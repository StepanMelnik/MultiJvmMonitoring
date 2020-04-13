package com.sme.monitoring.jvm.report;

import static com.sme.monitoring.jvm.report.supplier.ReporterSupplier.createSupplier;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sme.monitoring.jvm.core.model.Config;

/**
 * The main entry point to start log4j reporter.
 */
public class Log4jReporterMain
{
    private static final Logger LOGGER = LoggerFactory.getLogger(Log4jReporterMain.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();

    Log4jReporterMain()
    {
    }

    private void run()
    {
        Config config = parse();
        LOGGER.debug("Fetched {} configuration", config);

        createSupplier(config.getReportType()).report(config);
    }

    /**
     * Parses config.json and prepares {@link Config} instance.
     * 
     * @return Returns {@link Config} instance.
     */
    Config parse()
    {
        InputStream stream = Log4jReporterMain.class.getClassLoader().getResourceAsStream("config.json");

        try
        {
            return MAPPER.readValue(stream, new TypeReference<Config>()
            {
            });
        }
        catch (IOException e)
        {
            throw new IllegalArgumentException("Cannot read and parse config.json file", e);
        }
    }

    /**
     * The main method to start log4j reporter.
     * 
     * @param args The list of arguments;
     * @throws InterruptedException The exception if a thread is interrupted.
     */
    public static void main(String[] args) throws InterruptedException
    {
        CountDownLatch latch = new CountDownLatch(1);
        new Log4jReporterMain().run();
        latch.await();
    }
}
