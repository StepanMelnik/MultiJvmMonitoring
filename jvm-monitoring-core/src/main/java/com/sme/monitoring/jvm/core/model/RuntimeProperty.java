package com.sme.monitoring.jvm.core.model;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Runtime properties of java process.
 */
public class RuntimeProperty
{
    public static final RuntimeProperty EMPTY = new RuntimeProperty(new Date(-1), -1);

    private final Date startDate;
    private final long uptime;

    public RuntimeProperty(Date startDate, long uptime)
    {
        this.startDate = startDate;
        this.uptime = uptime;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public long getUptime()
    {
        return uptime;
    }

    public String getShortLabel()
    {
        return String.format("RuntimeProperty [startDate=%s, uptime=%s]", startDate, uptime);
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, SHORT_PREFIX_STYLE);
    }
}
