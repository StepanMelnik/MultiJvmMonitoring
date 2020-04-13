package com.sme.monitoring.jvm.core.jmx;

import com.sme.monitoring.jvm.core.model.GarbageCollectorProperty;
import com.sme.monitoring.jvm.core.model.MemoryProperty;
import com.sme.monitoring.jvm.core.model.RuntimeProperty;
import com.sme.monitoring.jvm.core.model.ThreadProperty;

/**
 * JMX factory.
 */
public interface IJmxFactory
{
    /**
     * Fetch CPU loading.
     * 
     * @return Returns CPU loading;
     * @throws Exception if the property cannot be read.
     */
    double getCpuLoad() throws Exception;

    /**
     * Creates {@link MemoryProperty} instance with memory properties.
     * 
     * @return Returns {@link MemoryProperty} instance;
     * @throws Exception if the property cannot be read.
     */
    MemoryProperty getMemoryUsed() throws Exception;

    /**
     * Creates {@link ThreadProperty} instance with thread properties.
     * 
     * @return Returns {@link ThreadProperty} instance;
     * @throws Exception if the property cannot be read.
     */
    ThreadProperty getThreadUsed() throws Exception;

    /**
     * Creates {@link RuntimeProperty} instance with runtime properties.
     * 
     * @return Returns {@link RuntimeProperty} instance;
     * @throws Exception if the property cannot be read.
     */
    RuntimeProperty getRuntimeProperty() throws Exception;

    /**
     * Creates {@link GarbageCollectorProperty} instance with GC properties.
     * 
     * @return Returns {@link GarbageCollectorProperty} instance;
     * @throws Exception if the property cannot be read.
     */
    GarbageCollectorProperty getGarbageCollectorProperty() throws Exception;
}
