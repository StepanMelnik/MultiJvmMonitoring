package com.sme.monitoring.jvm.core.model;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Method property.
 */
public class MethodProperty implements Comparable<MethodProperty>
{
    private final AtomicLong cpuTime = new AtomicLong(0);

    private final String method;
    private final String trace;

    public MethodProperty(String method, String trace)
    {
        this.method = method;
        this.trace = trace;
    }

    public String getMethod()
    {
        return method;
    }

    public String getTrace()
    {
        return trace;
    }

    public AtomicLong getCpuTime()
    {
        return cpuTime;
    }

    public String getShortLabel()
    {
        return String.format("MethodProperty [cpuTime=%s, method=%s, trace=%s]", cpuTime.get(), method, trace);
    }

    @Override
    public int compareTo(MethodProperty that)
    {
        return Long.valueOf(that.cpuTime.get()).compareTo(cpuTime.get());
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(method)
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

        MethodProperty that = (MethodProperty) obj;
        return new EqualsBuilder()
                .append(this.method, that.method)
                .isEquals();
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, SHORT_PREFIX_STYLE);
    }
}
