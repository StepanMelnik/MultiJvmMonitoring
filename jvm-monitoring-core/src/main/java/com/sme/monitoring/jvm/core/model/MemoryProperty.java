package com.sme.monitoring.jvm.core.model;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Memory properties of java process.
 */
public class MemoryProperty
{
    public static final MemoryProperty EMPTY = new MemoryProperty(-1, -1, -1, -1);

    private final long heapUsed;
    private final long heapMaxUsed;
    private final long nonHeapUsed;
    private final long nonHeapMaxUsed;

    public MemoryProperty(long heapUsed, long heapMaxUsed, long nonHeapUsed, long nonHeapMaxUsed)
    {
        this.heapUsed = heapUsed;
        this.heapMaxUsed = heapMaxUsed;
        this.nonHeapUsed = nonHeapUsed;
        this.nonHeapMaxUsed = nonHeapMaxUsed;
    }

    public long getHeapUsed()
    {
        return heapUsed;
    }

    public long getHeapMaxUsed()
    {
        return heapMaxUsed;
    }

    public long getNonHeapUsed()
    {
        return nonHeapUsed;
    }

    public long getNonHeapMaxUsed()
    {
        return nonHeapMaxUsed;
    }

    public String getShortLabel()
    {
        return String.format("MemoryProperty [heapUsed=%s, heapMaxUsed=%s, nonHeapUsed=%s, nonHeapMaxUsed=%s]", heapUsed, heapMaxUsed, nonHeapUsed, nonHeapMaxUsed);
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, SHORT_PREFIX_STYLE);
    }
}
