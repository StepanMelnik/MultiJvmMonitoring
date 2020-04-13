package com.sme.monitoring.jvm.core.model;

import org.apache.commons.lang3.StringUtils;

/**
 * Represent a type strategy to analyze pdf file and generate a specific result.
 */
public enum ReportType
{
    /**
     * The type to generate report by log4j framework.
     */
    LOG4J,

    /**
     * Send a report to zabbix.
     */
    ZABBIX,

    /**
     * Send a report to Spring Monitoring and Management.
     */
    SPRING;

    /**
     * Gets an {@link ReportType} by its name.
     *
     * @param name The name to get a type for.
     * @return Returns the type specified by name.
     */
    public static ReportType fromName(String name)
    {
        for (ReportType type : ReportType.values())
        {
            if (StringUtils.equalsIgnoreCase(type.name(), name))
            {
                return type;
            }
        }
        throw new IllegalArgumentException("'" + name + "' is not a valid report type");
    }
}
