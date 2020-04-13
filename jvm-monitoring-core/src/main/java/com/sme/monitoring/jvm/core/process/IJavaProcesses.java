package com.sme.monitoring.jvm.core.process;

import java.util.List;

import com.sme.monitoring.jvm.core.model.JavaProcess;

/**
 * Interface to work fetch java processes.
 */
public interface IJavaProcesses
{
    /**
     * Fetch all java processes.
     * 
     * @return Returns a list of java processes.
     */
    List<JavaProcess> getProcesses();
}
