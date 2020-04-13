package com.sme.monitoring.jvm.core.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests of {@link ReportType}
 */
public class ReportTypeTest extends Assert
{
    @Test
    public void testReportType() throws Exception
    {
        assertEquals(ReportType.LOG4J, ReportType.fromName("Log4j"));
        assertEquals(ReportType.SPRING, ReportType.fromName("spring"));
        assertEquals(ReportType.ZABBIX, ReportType.fromName("zabbix"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWrongType() throws Exception
    {
        ReportType.fromName("wrong type");
    }
}
