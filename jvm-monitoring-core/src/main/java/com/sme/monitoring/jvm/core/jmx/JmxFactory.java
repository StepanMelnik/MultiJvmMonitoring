package com.sme.monitoring.jvm.core.jmx;

import static java.lang.management.ManagementFactory.GARBAGE_COLLECTOR_MXBEAN_DOMAIN_TYPE;
import static java.lang.management.ManagementFactory.MEMORY_MXBEAN_NAME;
import static java.lang.management.ManagementFactory.RUNTIME_MXBEAN_NAME;
import static java.lang.management.ManagementFactory.THREAD_MXBEAN_NAME;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sme.monitoring.jvm.core.model.GarbageCollectorProperty;
import com.sme.monitoring.jvm.core.model.MemoryProperty;
import com.sme.monitoring.jvm.core.model.RuntimeProperty;
import com.sme.monitoring.jvm.core.model.ThreadProperty;

/**
 * Jmx factory implementation.
 */
class JmxFactory implements IJmxFactory
{
    private static final Logger LOGGER = LoggerFactory.getLogger(JmxFactory.class);

    static final double UNDEFINED_DOUBLE = -1.0d;
    private static final double DOUBLE_PERSENT = 1000 / 10.0;

    private MemoryMXBean memoryMBean;
    private ThreadMXBean threadMXBean;
    private RuntimeMXBean runtimeMXBean;

    private final MBeanServerConnection mbeanServerConnection;

    JmxFactory(MBeanServerConnection mbeanServerConnection)
    {
        this.mbeanServerConnection = mbeanServerConnection;
    }

    @Override
    public double getCpuLoad() throws Exception
    {
        ObjectName operatingSystem = ObjectName.getInstance("java.lang:type=OperatingSystem");

        AttributeList list = mbeanServerConnection.getAttributes(operatingSystem, new String[] {"ProcessCpuLoad"});

        if (!list.isEmpty())
        {
            Attribute attribute = (Attribute) list.get(0);
            Double value = (Double) attribute.getValue();

            if (value != -1.0)
            {
                return value * DOUBLE_PERSENT;
            }
        }

        return UNDEFINED_DOUBLE;
    }

    @Override
    public MemoryProperty getMemoryUsed() throws Exception
    {
        if (memoryMBean == null)
        {
            memoryMBean = ManagementFactory.newPlatformMXBeanProxy(mbeanServerConnection, MEMORY_MXBEAN_NAME, MemoryMXBean.class);
        }

        MemoryUsage heapMemoryUsage = memoryMBean.getHeapMemoryUsage();
        MemoryUsage nonHeapMemoryUsage = memoryMBean.getNonHeapMemoryUsage();

        return new MemoryProperty(heapMemoryUsage.getUsed(), heapMemoryUsage.getMax(),
                nonHeapMemoryUsage.getUsed(), nonHeapMemoryUsage.getMax());
    }

    @Override
    public ThreadProperty getThreadUsed() throws Exception
    {
        if (threadMXBean == null)
        {
            threadMXBean = ManagementFactory.newPlatformMXBeanProxy(mbeanServerConnection, THREAD_MXBEAN_NAME, ThreadMXBean.class);
        }

        boolean deadlocksDetected = threadMXBean.findDeadlockedThreads() != null || threadMXBean.findMonitorDeadlockedThreads() != null;
        return new ThreadProperty(threadMXBean.getThreadCount(), deadlocksDetected);
    }

    @Override
    public RuntimeProperty getRuntimeProperty() throws Exception
    {
        if (runtimeMXBean == null)
        {
            runtimeMXBean = ManagementFactory.newPlatformMXBeanProxy(mbeanServerConnection, RUNTIME_MXBEAN_NAME, RuntimeMXBean.class);
        }

        return new RuntimeProperty(new Date(runtimeMXBean.getStartTime()), runtimeMXBean.getUptime());
    }

    @Override
    public GarbageCollectorProperty getGarbageCollectorProperty() throws Exception
    {
        List<GarbageCollectorMXBean> garbageCollectorMBeans = new ArrayList<>();

        ObjectName gcName = new ObjectName(GARBAGE_COLLECTOR_MXBEAN_DOMAIN_TYPE + ",*");
        Set<ObjectName> mbeans = mbeanServerConnection.queryNames(gcName, null);
        if (mbeans != null)
        {
            mbeans.forEach(objectName ->
            {
                String name = GARBAGE_COLLECTOR_MXBEAN_DOMAIN_TYPE + ",name=" + objectName.getKeyProperty("name");
                try
                {
                    GarbageCollectorMXBean mbean = ManagementFactory.newPlatformMXBeanProxy(mbeanServerConnection, name, GarbageCollectorMXBean.class);
                    garbageCollectorMBeans.add(mbean);
                }
                catch (Exception e)
                {
                    LOGGER.error("Cannot fetch GarbageColelctor Mbean", e);
                }
            });
        }

        long gcTotalTime = garbageCollectorMBeans.stream().mapToLong(mapper -> mapper.getCollectionTime()).sum();
        long gcTotalInvocations = garbageCollectorMBeans.stream().mapToLong(mapper -> mapper.getCollectionCount()).sum();
        return new GarbageCollectorProperty(gcTotalTime, gcTotalInvocations);
    }
}
