package org.javaguru.travel.insurance.core.jobs;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javaguru.travel.insurance.core.api.command.TravelGetNotExportedAgreementUuidsCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelGetNotExportedAgreementUuidsCoreResult;
import org.javaguru.travel.insurance.core.services.TravelGetNotExportedAgreementUuidsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

@Component
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class AgreementXmlExporterJob {

    @Value("${agreement.xml.exporter.job.enabled:false}")
    private boolean jobEnabled;

    @Value("${agreement.xml.exporter.job.path}")
    private String agreementExportPath;

    @Value("${agreement.xml.exporter.job.thread.count:1}")
    private int threadPoolCount;

    private final TravelGetNotExportedAgreementUuidsService agreementUuidsService;
    private final AgreementXmlExporter agreementXmlExporter;

    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.SECONDS)
    public void doJob() {
        if (jobEnabled) {
            executeJob();
        }
    }

    private void executeJob() {
        log.info("AgreementXmlExporterJob started");

        List<String> allAgreementUuids = getNotExportedYetAgreementUuids();
        if (allAgreementUuids.isEmpty()) {
            log.info("No agreements found for export.");
            return;
        }

        exportAgreements(allAgreementUuids);
    }

    private List<String> getNotExportedYetAgreementUuids() {
        TravelGetNotExportedAgreementUuidsCoreResult result = agreementUuidsService.getAgreementUuids(new TravelGetNotExportedAgreementUuidsCoreCommand());
        return result.getAgreementUuids();
    }

    private void exportAgreements(List<String> agreementUuids) {
        ExecutorService executor = Executors.newFixedThreadPool(threadPoolCount);
        Collection<Future<?>> futures = new CopyOnWriteArrayList<>();
        agreementUuids.forEach(uuid -> futures.add(executor.submit(() -> agreementXmlExporter.exportAgreement(uuid))));
        waitUntilAllTasksWillBeExecuted(futures);
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    private static void waitUntilAllTasksWillBeExecuted(Collection<Future<?>> futures) {
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException e) {
                log.error("Export interrupted", e);
            } catch (ExecutionException e) {
                log.error("Error during export", e.getCause());
            }
        }
    }
}
