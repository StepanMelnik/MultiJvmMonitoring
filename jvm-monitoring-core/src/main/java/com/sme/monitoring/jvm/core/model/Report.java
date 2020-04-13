package com.sme.monitoring.jvm.core.model;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Contains report info.
 */
public class Report implements Comparable<Report>
{
    private final JavaProcess javaProcess;
    private final double processCpuLoad;
    private final MemoryProperty memoryProperty;
    private final ThreadProperty threadProperty;
    private final List<MethodProperty> methodProperties;
    private final RuntimeProperty runtimeProperty;
    private final GarbageCollectorProperty garbageCollectorProperty;

    public Report(JavaProcess javaProcess, double processCpuLoad, MemoryProperty memoryProperty,
            ThreadProperty threadProperty, RuntimeProperty runtimeProperty,
            GarbageCollectorProperty garbageCollectorProperty, List<MethodProperty> methodProperties)
    {
        this.javaProcess = javaProcess;
        this.processCpuLoad = processCpuLoad;
        this.memoryProperty = memoryProperty;
        this.threadProperty = threadProperty;
        this.runtimeProperty = runtimeProperty;
        this.garbageCollectorProperty = garbageCollectorProperty;
        this.methodProperties = methodProperties;
    }

    public JavaProcess getJavaProcess()
    {
        return javaProcess;
    }

    public double getProcessCpuLoad()
    {
        return processCpuLoad;
    }

    public MemoryProperty getMemoryProperty()
    {
        return memoryProperty;
    }

    public ThreadProperty getThreadProperty()
    {
        return threadProperty;
    }

    public List<MethodProperty> getMethodProperties()
    {
        return methodProperties;
    }

    public boolean isProfiled()
    {
        return !methodProperties.isEmpty();
    }

    public RuntimeProperty getRuntimeProperty()
    {
        return runtimeProperty;
    }

    public GarbageCollectorProperty getGarbageCollectorProperty()
    {
        return garbageCollectorProperty;
    }

    /**
     * Generates a label.
     * 
     * @return Returns the short label of report.
     */
    public String getShortLabel()
    {
        return String.format("Report [javaProcess=%s@%s(%s), processCpuLoad=%s, %s, %s, %s, %s, command=%s]",
                javaProcess.getUser(), javaProcess.getName(), javaProcess.getPid(), processCpuLoad,
                runtimeProperty.getShortLabel(), memoryProperty.getShortLabel(),
                threadProperty.getShortLabel(), garbageCollectorProperty.getShortLabel(), javaProcess.getCommand());
    }

    @Override
    public int compareTo(Report that)
    {
        return Double.valueOf(that.processCpuLoad).compareTo(processCpuLoad);
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(javaProcess.getPid())
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

        Report that = (Report) obj;
        return new EqualsBuilder()
                .append(this.javaProcess.getPid(), that.javaProcess.getPid())
                .isEquals();
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, SHORT_PREFIX_STYLE);
    }
}
