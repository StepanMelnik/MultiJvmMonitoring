package com.sme.monitoring.jvm.report.supplier;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.sme.monitoring.jvm.core.model.GarbageCollectorProperty;
import com.sme.monitoring.jvm.core.model.JavaProcess;
import com.sme.monitoring.jvm.core.model.MemoryProperty;
import com.sme.monitoring.jvm.core.model.MethodProperty;
import com.sme.monitoring.jvm.core.model.Report;
import com.sme.monitoring.jvm.core.model.RuntimeProperty;
import com.sme.monitoring.jvm.core.model.ThreadProperty;

/**
 * Unit tests of {@link Log4jReporter}.
 */
public class Log4jReporterTest extends Assert
{
    @Mock
    private JavaProcess javaProcess;
    @Mock
    private MemoryProperty memoryProperty;
    @Mock
    private ThreadProperty threadProperty;
    @Mock
    private RuntimeProperty runtimeProperty;
    @Mock
    private GarbageCollectorProperty garbageCollectorProperty;
    @Mock
    private List<MethodProperty> methodProperties;

    private Report spyReport;

    @Before
    public void setUp() throws Exception
    {
        initMocks(this);

        spyReport = spy(new Report(javaProcess, 1d, memoryProperty, threadProperty, runtimeProperty, garbageCollectorProperty, methodProperties));
        mockWhens();
    }

    private void mockWhens()
    {
        when(spyReport.getShortLabel()).thenReturn("Short label");
        when(spyReport.isProfiled()).thenReturn(true);
    }

    @Test
    public void testPrintReport() throws Exception
    {
        Log4jReporter log4jReporter = new Log4jReporter();
        log4jReporter.printReport(spyReport);

        verify(spyReport, times(2)).getShortLabel();
        verify(spyReport, times(2)).isProfiled();
    }
}
