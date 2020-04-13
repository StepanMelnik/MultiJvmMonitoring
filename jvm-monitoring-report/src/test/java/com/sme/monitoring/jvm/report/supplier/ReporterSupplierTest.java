package com.sme.monitoring.jvm.report.supplier;

import org.junit.Assert;
import org.junit.Test;

import com.sme.monitoring.jvm.core.model.ReportType;

/**
 * Unit tests of {@link ReporterSupplier}.
 */
public class ReporterSupplierTest extends Assert
{
    @Test
    public void testName() throws Exception
    {
        assertTrue("Expects Log4jReporter", ReporterSupplier.createSupplier(ReportType.LOG4J) instanceof Log4jReporter);
        assertTrue("Expects SpringReporter", ReporterSupplier.createSupplier(ReportType.SPRING) instanceof SpringReporter);
        assertTrue("Expects ZabbixReporter", ReporterSupplier.createSupplier(ReportType.ZABBIX) instanceof ZabbixReporter);
    }
}
