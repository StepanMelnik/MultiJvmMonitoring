package com.sme.monitoring.jvm.core.jmx;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sme.monitoring.jvm.core.model.GarbageCollectorProperty;
import com.sme.monitoring.jvm.core.model.JavaProcess;
import com.sme.monitoring.jvm.core.model.MemoryProperty;
import com.sme.monitoring.jvm.core.model.RuntimeProperty;
import com.sme.monitoring.jvm.core.model.ThreadProperty;

/**
 * Unit tests of {@link JmxConnector}
 */
public class JmxConnectorTest
{
    private JmxConnector jmxConnector;

    @Mock
    private IJmxFactory jmxFactory;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);

        JavaProcess javaProcess = new JavaProcess("pid", "command", "name", "user", "time");
        jmxConnector = new JmxConnector(javaProcess);
        jmxConnector.setJmxFactory(jmxFactory);

        mockWhens();
    }

    private void mockWhens() throws Exception
    {
        when(jmxFactory.getCpuLoad()).thenReturn(1.0d);
        when(jmxFactory.getGarbageCollectorProperty()).thenReturn(GarbageCollectorProperty.EMPTY);
        when(jmxFactory.getMemoryUsed()).thenReturn(MemoryProperty.EMPTY);
        when(jmxFactory.getRuntimeProperty()).thenReturn(RuntimeProperty.EMPTY);
        when(jmxFactory.getThreadUsed()).thenReturn(ThreadProperty.EMPTY);
    }

    @Test
    public void test_get_java_process() throws Exception
    {
        JavaProcess javaProcess = new JavaProcess("pid", "command", "name", "user", "time");

        jmxConnector = new JmxConnector(javaProcess);
        assertTrue("JavaProcess is attachable", jmxConnector.getJavaProcess().isPresent());

        javaProcess.setAttachable(false);
        assertFalse("JavaProcess is not attachable", jmxConnector.getJavaProcess().isPresent());
    }

    @Test
    public void test_get_process_cpu_load() throws Exception
    {
        assertEquals("Cpu load should be fetched properly", 1.0d, jmxConnector.getProcessCpuLoad(), 0);
        assertTrue("JavaProcess is attachable", jmxConnector.getJavaProcess().isPresent());

        verify(jmxFactory).getCpuLoad();

        when(jmxFactory.getCpuLoad()).thenThrow(new RuntimeException());
        jmxConnector.getProcessCpuLoad();
        assertFalse("JavaProcess is not attachable", jmxConnector.getJavaProcess().isPresent());
    }

    @Test
    public void test_get_memory_property() throws Exception
    {
        assertEquals("MemoryProperty should be fetched properly", MemoryProperty.EMPTY, jmxConnector.getMemoryProperty());
        assertTrue("JavaProcess is attachable", jmxConnector.getJavaProcess().isPresent());

        verify(jmxFactory).getMemoryUsed();

        when(jmxFactory.getMemoryUsed()).thenThrow(new RuntimeException());
        jmxConnector.getMemoryProperty();
        assertFalse("JavaProcess is not attachable", jmxConnector.getJavaProcess().isPresent());
    }

    @Test
    public void test_get_thread_property() throws Exception
    {
        assertEquals("ThreadProperty should be fetched properly", ThreadProperty.EMPTY, jmxConnector.getThreadProperty());
        assertTrue("JavaProcess is attachable", jmxConnector.getJavaProcess().isPresent());

        verify(jmxFactory).getThreadUsed();

        when(jmxFactory.getThreadUsed()).thenThrow(new RuntimeException());
        jmxConnector.getThreadProperty();
        assertFalse("JavaProcess is not attachable", jmxConnector.getJavaProcess().isPresent());
    }

    @Test
    public void test_get_runtime_property() throws Exception
    {
        assertEquals("RuntimeProperty should be fetched properly", RuntimeProperty.EMPTY, jmxConnector.getRuntimeProperty());
        assertTrue("JavaProcess is attachable", jmxConnector.getJavaProcess().isPresent());

        when(jmxFactory.getRuntimeProperty()).thenThrow(new RuntimeException());
        jmxConnector.getRuntimeProperty();
        assertFalse("JavaProcess is not attachable", jmxConnector.getJavaProcess().isPresent());
    }

    @Test
    public void test_get_garbage_collector_property() throws Exception
    {
        assertEquals("GarbageCollectorProperty should be fetched properly", GarbageCollectorProperty.EMPTY, jmxConnector.getGarbageCollectorProperty());
        assertTrue("JavaProcess is attachable", jmxConnector.getJavaProcess().isPresent());

        when(jmxFactory.getGarbageCollectorProperty()).thenThrow(new RuntimeException());
        jmxConnector.getGarbageCollectorProperty();
        assertFalse("JavaProcess is not attachable", jmxConnector.getJavaProcess().isPresent());
    }
}
