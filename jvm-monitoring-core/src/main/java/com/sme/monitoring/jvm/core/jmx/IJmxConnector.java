package com.sme.monitoring.jvm.core.jmx;

import java.util.List;
import java.util.Optional;

import com.sme.monitoring.jvm.core.model.GarbageCollectorProperty;
import com.sme.monitoring.jvm.core.model.JavaProcess;
import com.sme.monitoring.jvm.core.model.MemoryProperty;
import com.sme.monitoring.jvm.core.model.MethodProperty;
import com.sme.monitoring.jvm.core.model.RuntimeProperty;
import com.sme.monitoring.jvm.core.model.ThreadProperty;

/**
 * Jmx connector.
 */
public interface IJmxConnector
{
    /**
     * Connect to jmx agent of virtual machine.
     */
    void connect();

    /**
     * Fetch {@link JavaProcess} instance.
     * 
     * @return Returns java process instance.
     */
    Optional<JavaProcess> getJavaProcess();

    /**
     * Fetch a process CPU loading.
     * 
     * @return Returns the process CPU loading.
     */
    double getProcessCpuLoad();

    /**
     * Fetch {@link MemoryProperty}.
     * 
     * @return Returns {@link MemoryProperty} instance.
     */
    MemoryProperty getMemoryProperty();

    /**
     * Fetch {@link ThreadProperty}.
     * 
     * @return Returns {@link ThreadProperty} instance.
     */
    ThreadProperty getThreadProperty();

    /**
     * Fetch {@link RuntimeProperty}.
     * 
     * @return Returns {@link RuntimeProperty} instance.
     */
    RuntimeProperty getRuntimeProperty();

    /**
     * Fetch {@link GarbageCollectorProperty}.
     * 
     * @return Returns {@link GarbageCollectorProperty} instance.
     */
    GarbageCollectorProperty getGarbageCollectorProperty();

    /**
     * Get the profiled info about methods that consumes high CPU.
     * 
     * @param ignorePackages The list of packages to be ignored by profiler;
     * @return Returns the list of profiled methods.
     */
    List<MethodProperty> profile(List<String> ignorePackages);
}
