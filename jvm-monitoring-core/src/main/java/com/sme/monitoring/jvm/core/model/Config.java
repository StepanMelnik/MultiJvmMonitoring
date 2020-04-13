package com.sme.monitoring.jvm.core.model;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Provides configuration properties.
 */
public class Config
{
    private ReportType reportType;
    private int refreshInterval;
    private double thresholdCpuToProfile;
    private List<String> processNames;
    private List<String> ignorePackages;

    public int getRefreshInterval()
    {
        return refreshInterval;
    }

    public void setRefreshInterval(int refreshInterval)
    {
        this.refreshInterval = refreshInterval;
    }

    public double getThresholdCpuToProfile()
    {
        return thresholdCpuToProfile;
    }

    public void setThresholdCpuToProfile(double thresholdCpuToProfile)
    {
        this.thresholdCpuToProfile = thresholdCpuToProfile;
    }

    public ReportType getReportType()
    {
        return reportType;
    }

    public void setReportType(ReportType reportType)
    {
        this.reportType = reportType;
    }

    public List<String> getProcessNames()
    {
        return processNames;
    }

    public void setProcessNames(List<String> processNames)
    {
        this.processNames = processNames;
    }

    public List<String> getIgnorePackages()
    {
        return ignorePackages;
    }

    public void setIgnorePackages(List<String> ignorePackages)
    {
        this.ignorePackages = ignorePackages;
    }

    @Override
    public int hashCode()
    {
        return HashCodeBuilder.reflectionHashCode(this);
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

        Config that = (Config) obj;
        return EqualsBuilder.reflectionEquals(that, this);
    }

    @Override
    public String toString()
    {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SIMPLE_STYLE);
    }
}
