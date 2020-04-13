package com.sme.monitoring.jvm.core.process;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.jutils.jprocesses.info.ProcessesService;
import org.jutils.jprocesses.model.ProcessInfo;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sme.monitoring.jvm.core.model.JavaProcess;

/**
 * Unit tests of {@link JavaProcesses}
 */
public class JavaProcessesTest
{
    private IJavaProcesses javaProcesses;

    @Mock
    private ProcessesService processesService;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);

        javaProcesses = new JavaProcesses(processesService, asList("java.exe", "tomcat8_1.exe"));
        mockWhens();
    }

    private void mockWhens()
    {
        ProcessInfo javaProcessInfo = new ProcessInfo();
        javaProcessInfo.setPid("123");
        javaProcessInfo.setCommand("path/java.exe");
        javaProcessInfo.setName("java.exe");
        javaProcessInfo.setUser("tester");
        javaProcessInfo.setTime("time");

        ProcessInfo tomcatProcessInfo = new ProcessInfo();
        tomcatProcessInfo.setPid("234");
        tomcatProcessInfo.setCommand("path/tomcat8_1.exe");
        tomcatProcessInfo.setName("tomcat8_1.exe");
        tomcatProcessInfo.setUser("tester");
        tomcatProcessInfo.setTime("time");

        ProcessInfo winProcessInfo = new ProcessInfo();
        winProcessInfo.setPid("567");
        winProcessInfo.setCommand("path/cmd.exe");
        winProcessInfo.setName("cmd.exe");
        winProcessInfo.setUser("tester");
        winProcessInfo.setTime("time");

        List<ProcessInfo> processes = new ArrayList<>();
        processes.add(javaProcessInfo);
        processes.add(tomcatProcessInfo);
        processes.add(winProcessInfo);

        when(processesService.getList()).thenReturn(processes);
    }

    @Test
    public void test_get_processes() throws Exception
    {
        List<JavaProcess> processes = javaProcesses.getProcesses();
        assertTrue("The list constains java.exe process", processes.contains(new JavaProcess("123", "path/java.exe", "java.exe", "tester", "time")));
        assertTrue("The list constains tomcat8_1.exe process", processes.contains(new JavaProcess("234", "path/tomcat8_1.exe", "tomcat8_1.exe", "tester", "time")));

        verify(processesService).getList();
    }
}
