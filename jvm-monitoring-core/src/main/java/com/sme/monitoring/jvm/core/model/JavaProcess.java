package com.sme.monitoring.jvm.core.model;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * The model contains an info about java process.
 */
public class JavaProcess
{
    private final String pid;
    private final String command;
    private final String name;
    private final String user;
    private final String time;

    private String jmxAddress;
    private boolean attachable = true;

    public JavaProcess(String pid, String command, String name, String user, String time)
    {
        this.pid = pid;
        this.command = command;
        this.name = name;
        this.user = user;
        this.time = time;
    }

    public String getPid()
    {
        return pid;
    }

    public String getCommand()
    {
        return command;
    }

    public String getName()
    {
        return name;
    }

    public String getUser()
    {
        return user;
    }

    public String getTime()
    {
        return time;
    }

    public void setJmxAddress(String jmxAddress)
    {
        this.jmxAddress = jmxAddress;
    }

    public String getJmxAddress()
    {
        return jmxAddress;
    }

    public boolean isAttachable()
    {
        return attachable;
    }

    public void setAttachable(boolean attachable)
    {
        this.attachable = attachable;
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(pid)
                .append(command)
                .append(user)
                .append(name)
                .append(time)
                .append(jmxAddress)
                .append(attachable)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }

        if (obj == null || getClass() != obj.getClass())
        {
            return false;
        }

        JavaProcess that = (JavaProcess) obj;
        return new EqualsBuilder()
                .append(this.pid, that.pid)
                .append(this.command, that.command)
                .append(user, that.user)
                .append(name, that.name)
                .append(time, that.time)
                .append(jmxAddress, that.jmxAddress)
                .append(attachable, that.attachable)
                .isEquals();
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, SHORT_PREFIX_STYLE);
    }
}
