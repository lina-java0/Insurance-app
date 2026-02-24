package org.export.travel.insurance.core.jobs;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.export.travel.insurance.core.api.command.TravelExportAgreementToXmlCoreCommand;
import org.export.travel.insurance.core.services.TravelExportAgreementToXmlServiceImpl;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class AgreementXmlExporter {

    private final TravelExportAgreementToXmlServiceImpl travelExportAgreementToXmlService;

    public void exportAgreement(String agreementUuid) {
        log.info("Exporting agreement started for UUID: {}", agreementUuid);
        travelExportAgreementToXmlService.export(new TravelExportAgreementToXmlCoreCommand(agreementUuid));
        log.info("Exporting agreement finished for UUID: {}", agreementUuid);
    }
}
