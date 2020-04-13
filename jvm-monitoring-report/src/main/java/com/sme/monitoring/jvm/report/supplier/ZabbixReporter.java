package com.sme.monitoring.jvm.report.supplier;

import com.sme.monitoring.jvm.core.model.Report;
import com.sme.monitoring.jvm.core.reporter.AReporter;

/**
 * Zabbix trapper.
 */
class ZabbixReporter extends AReporter
{
    ZabbixReporter()
    {
    }

    @Override
    protected void printReport(Report report)
    {
        throw new UnsupportedOperationException("Zabbix reporter has not implemented yet");
    }
}
