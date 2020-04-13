package com.sme.monitoring.jvm.core.model;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Thread properties of java process.
 */
public class ThreadProperty
{
    public static final ThreadProperty EMPTY = new ThreadProperty(-1, false);

    private final int threadCount;
    private final boolean deadlocksDetected;

    public ThreadProperty(int threadCount, boolean deadlocksDetected)
    {
        this.threadCount = threadCount;
        this.deadlocksDetected = deadlocksDetected;
    }

    public int getThreadCount()
    {
        return threadCount;
    }

    public boolean isDeadlocksDetected()
    {
        return deadlocksDetected;
    }

    public String getShortLabel()
    {
        return String.format("ThreadProperty [threadCount=%s, deadlocksDetected=%s]", threadCount, deadlocksDetected);
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, SHORT_PREFIX_STYLE);
    }
}
