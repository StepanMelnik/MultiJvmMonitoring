package com.sme.monitoring.jvm.core.virtualmachine;

import com.sme.monitoring.jvm.core.model.JavaProcess;

/**
 * Provides functionality to work with java java virtual machines.
 */
public interface IVirtualMachine
{
    /**
     * Attach to java virtual machines and update JMX address of {@link JavaProcess} instance. If java process is not attachable, inform
     * {@link JavaProcess} instance about it.
     * 
     * @param javaProcess The instance of {@link JavaProcess}.
     */
    void attach(JavaProcess javaProcess);
}
