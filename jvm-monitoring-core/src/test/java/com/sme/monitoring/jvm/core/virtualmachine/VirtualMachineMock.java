package com.sme.monitoring.jvm.core.virtualmachine;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

//CSOFF
import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import com.sun.tools.attach.spi.AttachProvider;
//CSON

/**
 * Mock of {@link VirtualMachine}.
 */
class VirtualMachineMock extends VirtualMachine
{
    static final String LOCAL_CONNECTOR_ADDRESS = "1111111";

    protected VirtualMachineMock(String arg)
    {
        super(new AttachProvider()
        {
            @Override
            public VirtualMachine attachVirtualMachine(String arg0) throws AttachNotSupportedException, IOException
            {
                return null;
            }

            @Override
            public List<VirtualMachineDescriptor> listVirtualMachines()
            {
                return null;
            }

            @Override
            public String name()
            {
                return null;
            }

            @Override
            public String type()
            {
                return null;
            }
        }, arg);
    }

    @Override
    public void detach() throws IOException
    {
    }

    @Override
    public Properties getAgentProperties() throws IOException
    {
        Properties properties = new Properties();
        properties.put(LocalVirtualMachine.LOCAL_CONNECTOR_ADDRESS_PROP, LOCAL_CONNECTOR_ADDRESS);
        return properties;
    }

    @Override
    public Properties getSystemProperties() throws IOException
    {
        return null;
    }

    @Override
    public void loadAgent(String arg0, String arg1) throws AgentLoadException, AgentInitializationException, IOException
    {
    }

    @Override
    public void loadAgentLibrary(String arg0, String arg1) throws AgentLoadException, AgentInitializationException, IOException
    {
    }

    @Override
    public void loadAgentPath(String arg0, String arg1) throws AgentLoadException, AgentInitializationException, IOException
    {
    }

    @Override
    public String startLocalManagementAgent() throws IOException
    {
        return null;
    }

    @Override
    public void startManagementAgent(Properties arg0) throws IOException
    {
    }
}
