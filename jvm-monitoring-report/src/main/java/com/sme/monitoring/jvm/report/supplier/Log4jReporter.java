package com.sme.monitoring.jvm.report.supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sme.monitoring.jvm.core.model.Report;
import com.sme.monitoring.jvm.core.reporter.AReporter;

/**
 * Log4jReporter to print java statistic in logs.
 */
class Log4jReporter extends AReporter
{
    public static final String JAVA_PROCESS_NAMES = "process_names";
    public static final String REFRESH_INTERVAL = "refresh_interval";
    public static final String THRESHOLD_CPU_TO_PROFILE = "threshold_cpu_to_profile";
    public static final String HELP = "help";

    private static final Logger LOGGER = LoggerFactory.getLogger("Log4jReporter");

    Log4jReporter()
    {
    }

    @Override
    protected void printReport(Report report)
    {
        LOGGER.info(report.getShortLabel());

        if (report.isProfiled())
        {
            report.getMethodProperties().forEach(methodProperty -> LOGGER.info(methodProperty.getShortLabel()));
        }
    }
}
