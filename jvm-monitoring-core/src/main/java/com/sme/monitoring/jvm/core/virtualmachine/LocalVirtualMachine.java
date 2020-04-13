package com.sme.monitoring.jvm.core.virtualmachine;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sme.monitoring.jvm.core.model.JavaProcess;
//CSOFF
import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
//CSON

/**
 * Implementation to work with virtual machines.
 */
public class LocalVirtualMachine implements IVirtualMachine
{
    static final String LOCAL_CONNECTOR_ADDRESS_PROP = "com.sun.management.jmxremote.localConnectorAddress";

    private static final Logger LOGGER = LoggerFactory.getLogger(LocalVirtualMachine.class);
    private static final String JRE_AGENT_PATH = File.separator + "jre" + File.separator + "lib" + File.separator + "management-agent.jar";
    private static final String JAVA_AGENT_PATH = File.separator + "lib" + File.separator + "management-agent.jar";

    @Override
    public void attach(JavaProcess javaProcess)
    {
        try
        {
            LOGGER.debug("Connect to " + javaProcess);

            VirtualMachine virtualMachine = VirtualMachine.attach(javaProcess.getPid());
            Properties agentProperties = virtualMachine.getAgentProperties();
            String jmxAddress = (String) agentProperties.get(LOCAL_CONNECTOR_ADDRESS_PROP);

            if (jmxAddress == null)
            {
                jmxAddress = loadAgentAddress(javaProcess, virtualMachine);
            }

            javaProcess.setJmxAddress(jmxAddress);
            LOGGER.debug(String.format("Found %s agent for %s", jmxAddress, javaProcess));

            virtualMachine.detach();
        }
        catch (AgentLoadException e)
        {
            LOGGER.error("Agent cannot be loaded for " + javaProcess, e);
            javaProcess.setAttachable(false);
        }
        catch (AgentInitializationException e)
        {
            LOGGER.error("Agent cannot be initialized for " + javaProcess, e);
            javaProcess.setAttachable(false);
        }
        catch (AttachNotSupportedException e)
        {
            LOGGER.error("Cannot attach to " + javaProcess, e);
            javaProcess.setAttachable(false);
        }
        catch (IOException e)
        {
            LOGGER.error("Cannot connect to " + javaProcess, e);
            javaProcess.setAttachable(false);
        }
        catch (Exception e)
        {
            LOGGER.error("Unexpected exception oocurrs to connect to " + javaProcess, e);
            javaProcess.setAttachable(false);
        }
    }

    /**
     * Fetches jvm agent address.
     * 
     * @param javaProcess The {@link JavaProcess} instance;
     * @param virtualMachine Sun virtual machine;
     * @return Returns address to connect to virtual machine;
     * @throws IOException Throws exception if agent file does not exist;
     * @throws AgentInitializationException Throws exception if agent cannot be initialized;
     * @throws AgentLoadException Throws exception if agent cannot be loaded.
     */
    private String loadAgentAddress(JavaProcess javaProcess, VirtualMachine virtualMachine) throws IOException, AgentLoadException, AgentInitializationException
    {
        LOGGER.debug("Start management agent for " + javaProcess);

        String javaHome = virtualMachine.getSystemProperties().getProperty("java.home");
        LOGGER.debug("Use java home: " + javaHome);

        // Normally in ${java.home}/jre/lib/management-agent.jar but might be in ${java.home}/lib in build environments.
        String agent = javaHome + JRE_AGENT_PATH;

        File agentFile = new File(agent);
        if (!agentFile.exists())
        {
            agent = javaHome + JAVA_AGENT_PATH;
            agentFile = new File(agent);
            if (!agentFile.exists())
            {
                throw new IOException("Management agent is not found");
            }
        }

        agent = agentFile.getCanonicalPath();
        LOGGER.debug("Use agent: " + agent);
        virtualMachine.loadAgent(agent, "com.sun.management.jmxremote");

        Properties agentProps = virtualMachine.getAgentProperties();
        return (String) agentProps.get(LOCAL_CONNECTOR_ADDRESS_PROP);
    }
}
