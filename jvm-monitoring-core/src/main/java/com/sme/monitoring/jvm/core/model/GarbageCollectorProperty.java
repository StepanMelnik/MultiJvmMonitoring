package com.sme.monitoring.jvm.core.model;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * GarbageCollector properties of java process.
 */
public class GarbageCollectorProperty
{
    public static final GarbageCollectorProperty EMPTY = new GarbageCollectorProperty(-1, -1);

    private final long gcTotalTime;
    private final long gcTotalInvocations;

    public GarbageCollectorProperty(long gcTotalTime, long gcTotalInvocations)
    {
        this.gcTotalTime = gcTotalTime;
        this.gcTotalInvocations = gcTotalInvocations;
    }

    public long getGcTotalTime()
    {
        return gcTotalTime;
    }

    public long getGcTotalInvocations()
    {
        return gcTotalInvocations;
    }

    public String getShortLabel()
    {
        return String.format("GarbageCollectorProperty [gcTotalTime=%s, gcTotalInvocations=%s]", gcTotalTime, gcTotalInvocations);
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, SHORT_PREFIX_STYLE);
    }

    @Override
    public int hashCode()
    {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null || getClass() != obj.getClass())
        {
            return false;
        }

        GarbageCollectorProperty that = (GarbageCollectorProperty) obj;
        return EqualsBuilder.reflectionEquals(that, this);
    }
}
