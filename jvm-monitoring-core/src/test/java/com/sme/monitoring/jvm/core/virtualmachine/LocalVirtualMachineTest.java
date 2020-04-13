package com.sme.monitoring.jvm.core.virtualmachine;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.sme.monitoring.jvm.core.model.JavaProcess;
//CSOFF
import com.sun.tools.attach.VirtualMachine;
//CSON

/**
 * Unit tests of {@link LocalVirtualMachine}
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(VirtualMachine.class)
public class LocalVirtualMachineTest
{
    private IVirtualMachine virtualMachine;

    @Before
    public void setUp() throws Exception
    {
        virtualMachine = new LocalVirtualMachine();
    }

    @Test
    public void test_attach() throws Exception
    {
        PowerMockito.mockStatic(VirtualMachine.class);
        PowerMockito.when(VirtualMachine.attach("123")).thenReturn(new VirtualMachineMock(""));

        JavaProcess javaProcess = new JavaProcess("123", "path/java.exe", "java.exe", "tester", "time");

        virtualMachine.attach(javaProcess);
        assertEquals("JMX address should be fetched", VirtualMachineMock.LOCAL_CONNECTOR_ADDRESS, javaProcess.getJmxAddress());
    }
}
