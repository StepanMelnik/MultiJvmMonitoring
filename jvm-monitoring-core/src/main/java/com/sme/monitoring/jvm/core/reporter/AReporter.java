package com.sme.monitoring.jvm.core.reporter;

import static com.sme.monitoring.jvm.core.util.ThreadUtil.sleep;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sme.monitoring.jvm.core.jmx.IJmxConnector;
import com.sme.monitoring.jvm.core.jmx.JmxConnector;
import com.sme.monitoring.jvm.core.model.Config;
import com.sme.monitoring.jvm.core.model.MethodProperty;
import com.sme.monitoring.jvm.core.model.Report;
import com.sme.monitoring.jvm.core.process.JavaProcesses;
import com.sme.monitoring.jvm.core.virtualmachine.LocalVirtualMachine;

/**
 * Abstraction to prepare a report.
 */
public abstract class AReporter
{
    private static final Logger LOGGER = LoggerFactory.getLogger(AReporter.class);
    private static final int MILLIS_PER_SEC = 1000;
    private static final long CREATE_PROCESSES_PERIOD = 600 * MILLIS_PER_SEC;  // Init connectors every 10 minutes.
    private static final long PROCESSES_CONNECTORS_DELAY = 60 * MILLIS_PER_SEC;

    private final Map<String, IJmxConnector> connectors = new ConcurrentHashMap<>();
    private Config config;

    /**
     * Initialize and process all found connectors.
     * 
     * @param config The entry point configuration.
     */
    public final void report(Config config)
    {
        this.config = config;
        new Timer().schedule(new InitConnectors(), 0, CREATE_PROCESSES_PERIOD);
        new Timer().schedule(new ProcessConnectors(), PROCESSES_CONNECTORS_DELAY, config.getRefreshInterval());
    }

    /**
     * Timer task implementation to initialize connectors.
     */
    class InitConnectors extends TimerTask
    {
        @Override
        public void run()
        {
            LOGGER.debug("Init connectors");

            connectors.entrySet().removeIf(filter -> !filter.getValue().getJavaProcess().isPresent());

            new JavaProcesses(config.getProcessNames()).getProcesses()
                    .forEach(
                            process ->
                            {
                                if (!connectors.containsKey(process.getPid()))
                                {
                                    LOGGER.debug("Connect to process: " + process);

                                    new LocalVirtualMachine().attach(process);

                                    IJmxConnector connector = new JmxConnector(process);
                                    connector.connect();

                                    if (connector.getJavaProcess().isPresent())
                                    {
                                        connectors.put(process.getPid(), connector);
                                    }
                                }
                            });
        }
    }

    /**
     * Timer task implementation to process connectors.
     */
    class ProcessConnectors extends TimerTask
    {
        @Override
        public void run()
        {
            LOGGER.debug("Process connectors");
            sleep(config.getRefreshInterval());

            connectors.values()
                    .stream()
                    .filter(element -> element.getJavaProcess().isPresent())
                    .forEach(connector ->
                    {
                        if (connector.getJavaProcess().isPresent())
                        {
                            Report report = new Report(connector.getJavaProcess().get(), connector.getProcessCpuLoad(),
                                    connector.getMemoryProperty(), connector.getThreadProperty(),
                                    connector.getRuntimeProperty(), connector.getGarbageCollectorProperty(),
                                    profileIfNeeded(connector));

                            printReport(report);
                        }
                    });
        }

        private List<MethodProperty> profileIfNeeded(IJmxConnector connector)
        {
            if (connector.getProcessCpuLoad() >= config.getThresholdCpuToProfile())
            {
                return connector.profile(config.getIgnorePackages());
            }

            return Collections.emptyList();
        }
    }

    /**
     * Print report.
     *
     * @param report The report.
     */
    protected abstract void printReport(Report report);
}
