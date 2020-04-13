package com.sme.monitoring.jvm.core.process;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.jutils.jprocesses.info.ProcessesFactory;
import org.jutils.jprocesses.info.ProcessesService;
import org.jutils.jprocesses.model.ProcessInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sme.monitoring.jvm.core.model.JavaProcess;

/**
 * Fetch a list of java processes by specific filters.
 */
public class JavaProcesses implements IJavaProcesses
{
    private static final Logger LOGGER = LoggerFactory.getLogger(JavaProcesses.class);

    private static final String MONITOR_PROGRAM = "monitor";

    private final List<String> processNames;
    private final ProcessesService processesService;

    public JavaProcesses(List<String> processNames)
    {
        this(ProcessesFactory.getService(), processNames);
    }

    //@VisibleForTesting
    JavaProcesses(ProcessesService processesService, List<String> processNames)
    {
        this.processesService = processesService;
        this.processNames = processNames;
    }

    @Override
    public List<JavaProcess> getProcesses()
    {
        List<JavaProcess> javaProcesses = new ArrayList<>();

        LOGGER.debug("Start to fetch processes");
        List<ProcessInfo> processesList = processesService.getList();
        LOGGER.debug("Fetched all processes: " + processesList.size());

        processesList.stream()
                .map(processInfo ->
                {
                    LOGGER.debug("Check {} process with {} command", processInfo.getName(), processInfo.getCommand());
                    return processInfo;
                })
                .filter(processInfo -> processNames.contains(processInfo.getName().trim()) && !processInfo.getCommand().contains(MONITOR_PROGRAM))
                .collect(Collectors.toList())
                .forEach(process ->
                {
                    LOGGER.debug("Process: {} pid, {} name, {} user, {} time, command", process.getPid(), process.getName(), process.getUser(), process.getTime(), process.getCommand());
                    javaProcesses.add(new JavaProcess(process.getPid(), process.getCommand(), process.getName(), process.getUser(), process.getTime()));
                });

        return javaProcesses;
    }
}
