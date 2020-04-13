package com.sme.monitoring.jvm.core.jmx;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sme.monitoring.jvm.core.jmx.profiler.MethodCpuProfiler;
import com.sme.monitoring.jvm.core.model.GarbageCollectorProperty;
import com.sme.monitoring.jvm.core.model.JavaProcess;
import com.sme.monitoring.jvm.core.model.MemoryProperty;
import com.sme.monitoring.jvm.core.model.MethodProperty;
import com.sme.monitoring.jvm.core.model.RuntimeProperty;
import com.sme.monitoring.jvm.core.model.ThreadProperty;

/**
 * Implementation of {@link IJmxConnector}.
 */
public class JmxConnector implements IJmxConnector
{
    private static final Logger LOGGER = LoggerFactory.getLogger(JmxConnector.class);

    private final JavaProcess javaProcess;
    private MBeanServerConnection mbeanServerConnection;
    private IJmxFactory jmxFactory;

    public JmxConnector(JavaProcess javaProcess)
    {
        this.javaProcess = javaProcess;
    }

    /**
     * Sets own {@link IJmxFactory} implementation in test.
     * 
     * @VisibleForTesting
     * @param jmxFactory The instance of {@link IJmxFactory}.
     */
    void setJmxFactory(IJmxFactory jmxFactory)
    {
        this.jmxFactory = jmxFactory;
    }

    @Override
    public void connect()
    {
        if (!javaProcess.isAttachable())
        {
            return;
        }

        try
        {
            JMXServiceURL jmxUrl = new JMXServiceURL(javaProcess.getJmxAddress());
            JMXConnector jmxConnector = JMXConnectorFactory.connect(jmxUrl);
            mbeanServerConnection = jmxConnector.getMBeanServerConnection();
            jmxFactory = new JmxFactory(mbeanServerConnection);
        }
        catch (MalformedURLException e)
        {
            LOGGER.error(String.format("Cannot connect to %s jmx server of %s jvm", javaProcess.getJmxAddress(), javaProcess), e);
            javaProcess.setAttachable(false);
        }
        catch (IOException e)
        {
            LOGGER.error(String.format("Cannot create %s jmx connector of %s jvm", javaProcess.getJmxAddress(), javaProcess), e);
            javaProcess.setAttachable(false);
        }
        catch (Exception e)
        {
            LOGGER.error(String.format("Unexpected exception oocurrs to connect to create %s jmx connector of %s jvm", javaProcess.getJmxAddress(), javaProcess), e);
            javaProcess.setAttachable(false);
        }
    }

    @Override
    public double getProcessCpuLoad()
    {
        Objects.requireNonNull(jmxFactory, "JmxFactory should be available.");

        try
        {
            return jmxFactory.getCpuLoad();
        }
        catch (Exception e)
        {
            LOGGER.error("Cannot fetch cpu loading for " + javaProcess, e);
            javaProcess.setAttachable(false);
        }

        return JmxFactory.UNDEFINED_DOUBLE;
    }

    @Override
    public Optional<JavaProcess> getJavaProcess()
    {
        return javaProcess.isAttachable() ? of(javaProcess) : empty();
    }

    @Override
    public MemoryProperty getMemoryProperty()
    {
        Objects.requireNonNull(jmxFactory, "JmxFactory should be available.");

        try
        {
            return jmxFactory.getMemoryUsed();
        }
        catch (Exception e)
        {
            LOGGER.error("Cannot fetch memory properties for " + javaProcess, e);
            javaProcess.setAttachable(false);
        }

        return MemoryProperty.EMPTY;
    }

    @Override
    public ThreadProperty getThreadProperty()
    {
        Objects.requireNonNull(jmxFactory, "JmxFactory should be available.");

        try
        {
            return jmxFactory.getThreadUsed();
        }
        catch (Exception e)
        {
            LOGGER.error("Cannot fetch thread properties for " + javaProcess, e);
            javaProcess.setAttachable(false);
        }

        return ThreadProperty.EMPTY;
    }

    @Override
    public RuntimeProperty getRuntimeProperty()
    {
        Objects.requireNonNull(jmxFactory, "JmxFactory should be available.");

        try
        {
            return jmxFactory.getRuntimeProperty();
        }
        catch (Exception e)
        {
            LOGGER.error("Cannot fetch thread properties for " + javaProcess, e);
            javaProcess.setAttachable(false);
        }

        return RuntimeProperty.EMPTY;
    }

    @Override
    public GarbageCollectorProperty getGarbageCollectorProperty()
    {
        Objects.requireNonNull(jmxFactory, "JmxFactory should be available.");

        try
        {
            return jmxFactory.getGarbageCollectorProperty();
        }
        catch (Exception e)
        {
            LOGGER.error("Cannot fetch thread properties for " + javaProcess, e);
            javaProcess.setAttachable(false);
        }

        return GarbageCollectorProperty.EMPTY;
    }

    @Override
    public List<MethodProperty> profile(List<String> ignorePackages)
    {
        Objects.requireNonNull(mbeanServerConnection, "MBeanServerConnection connection should be available.");

        try
        {
            return new MethodCpuProfiler(ignorePackages).profile(mbeanServerConnection);
        }
        catch (Exception e)
        {
            LOGGER.error("Cannot profile " + javaProcess, e);
            javaProcess.setAttachable(false);
        }

        return Collections.emptyList();
    }
}
