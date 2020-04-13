package com.sme.monitoring.jvm.report.supplier;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.sme.monitoring.jvm.core.model.ReportType;
import com.sme.monitoring.jvm.core.reporter.AReporter;

/**
 * Factory to create {@link AReporter} implementation by {@link ReportType}.
 */
public final class ReporterSupplier
{
    private static Map<ReportType, Supplier<AReporter>> SUPPLIERS = Stream
            .of(new ImmutablePair<ReportType, Supplier<AReporter>>(ReportType.LOG4J, Log4jReporter::new),
                    new ImmutablePair<ReportType, Supplier<AReporter>>(ReportType.SPRING, SpringReporter::new),
                    new ImmutablePair<ReportType, Supplier<AReporter>>(ReportType.ZABBIX, ZabbixReporter::new))
            .collect(Collectors.toMap(ImmutablePair::getLeft, ImmutablePair::getRight));

    // Private constructor
    private ReporterSupplier()
    {
    }

    /**
     * Creates {@link AReporter} instance according to {@link ReportType}.
     * 
     * @param reportType The {@link ReportType} type;
     * @return Returns {@link AReporter} instance.
     */
    public static AReporter createSupplier(ReportType reportType)
    {
        return Optional.ofNullable(SUPPLIERS.get(reportType).get()).orElseThrow(() -> new IllegalArgumentException(reportType + " has not supported yet"));
    }
}
