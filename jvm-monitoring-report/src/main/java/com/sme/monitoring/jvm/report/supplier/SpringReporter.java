package com.sme.monitoring.jvm.report.supplier;

import com.sme.monitoring.jvm.core.model.Report;
import com.sme.monitoring.jvm.core.reporter.AReporter;

/**
 * Spring trapper.
 */
class SpringReporter extends AReporter
{
    SpringReporter()
    {
    }

    @Override
    protected void printReport(Report report)
    {
        throw new UnsupportedOperationException("Spring trapper has not implemented yet");
    }
}
