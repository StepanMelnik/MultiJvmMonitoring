package com.sme.monitoring.jvm.core.jmx.profiler;

import static java.lang.management.ManagementFactory.THREAD_MXBEAN_NAME;

import java.io.IOException;
import java.lang.Thread.State;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.management.MBeanServerConnection;

import com.sme.monitoring.jvm.core.model.MethodProperty;

/**
 * Fetches a method in all threads with high cpu consuming.
 */
public class MethodCpuProfiler
{
    private static final int STACK_TRACE_SIZE = 5;
    private final Map<String, MethodProperty> methods = new ConcurrentHashMap<>();
    private final List<String> ignorePackages;

    public MethodCpuProfiler(List<String> ignorePackages)
    {
        this.ignorePackages = ignorePackages;
    }

    /**
     * Profiles current threads.
     * 
     * @param mbeanServerConnection MBeanServerConnection connection;
     * @return Returns a list of profiled methods;
     * @throws IOException The exception if the management interface cannot be created.
     */
    public List<MethodProperty> profile(MBeanServerConnection mbeanServerConnection) throws IOException
    {
        ThreadMXBean threadMXBean = ManagementFactory.newPlatformMXBeanProxy(mbeanServerConnection, THREAD_MXBEAN_NAME, ThreadMXBean.class);

        for (ThreadInfo threadInfo : threadMXBean.dumpAllThreads(false, false))
        {
            long threadCpuTime = threadMXBean.getThreadCpuTime(threadInfo.getThreadId());

            if (threadInfo.getStackTrace().length > 0 && threadInfo.getThreadState() == State.RUNNABLE)
            {
                for (StackTraceElement stackTraceElement : threadInfo.getStackTrace())
                {
                    Optional<String> found = ignorePackages
                            .stream()
                            .filter(ignoredPackage -> stackTraceElement.getClassName().startsWith(ignoredPackage))
                            .findAny();

                    if (!found.isPresent())
                    {
                        String method = stackTraceElement.getClassName() + "#" + stackTraceElement.getMethodName();
                        methods.putIfAbsent(method, new MethodProperty(method, threadInfo.toString()));
                        methods.get(method).getCpuTime().addAndGet(threadCpuTime);
                    }
                }
            }
        }

        return methods.values().stream().sorted().limit(STACK_TRACE_SIZE).collect(Collectors.toList());
    }
}
