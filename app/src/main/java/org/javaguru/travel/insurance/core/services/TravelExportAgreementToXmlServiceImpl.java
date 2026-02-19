package org.javaguru.travel.insurance.core.services;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javaguru.travel.insurance.core.api.command.TravelExportAgreementToXmlCoreCommand;
import org.javaguru.travel.insurance.core.api.command.TravelExportAgreementToXmlCoreResult;
import org.javaguru.travel.insurance.core.api.command.TravelGetAgreementCoreCommand;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.domain.entities.AgreementXmlExportEntity;
import org.javaguru.travel.insurance.core.repositories.entities.AgreementXmlExportEntityRepository;
import org.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@Slf4j
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class TravelExportAgreementToXmlServiceImpl implements TravelExportAgreementToXmlService {

    @Value("${agreement.xml.exporter.job.path}")
    private String agreementExportPath;

    private final TravelGetAgreementService agreementService;
    private final AgreementXmlExportEntityRepository agreementXmlExportEntityRepository;
    private final ValidationErrorFactory validationErrorFactory;

    @Override
    public TravelExportAgreementToXmlCoreResult export(TravelExportAgreementToXmlCoreCommand command) {
        try {
            AgreementDTO agreementDTO = getAgreementData(command.getAgreementUuid());
            String agreementXml = convertAgreementToXml(agreementDTO);
            storeXmlToFile(command.getAgreementUuid(), agreementXml);
            log.info("Successfully exported agreement UUID: {}", command.getAgreementUuid());
        } catch (Exception e) {
            log.error("Failed to export agreement UUID: {}", command.getAgreementUuid(), e);
            return new TravelExportAgreementToXmlCoreResult(List.of(
                    validationErrorFactory.buildError("ERROR_CODE_19")
            ));
        }

        saveToDatabaseInfoAboutExportedAgreement(command);

        return new TravelExportAgreementToXmlCoreResult();
    }

    private void saveToDatabaseInfoAboutExportedAgreement(TravelExportAgreementToXmlCoreCommand command) {
        AgreementXmlExportEntity agreementXmlExportEntity = new AgreementXmlExportEntity();
        agreementXmlExportEntity.setAgreementUuid(command.getAgreementUuid());
        agreementXmlExportEntity.setAlreadyExported(Boolean.TRUE);
        agreementXmlExportEntityRepository.save(agreementXmlExportEntity);
    }

    private AgreementDTO getAgreementData(String agreementUuid) {
        TravelGetAgreementCoreCommand command = new TravelGetAgreementCoreCommand(agreementUuid);
        return agreementService.getAgreement(command).getAgreement();
    }

    private String convertAgreementToXml(AgreementDTO agreementDTO) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(AgreementDTO.class);
        Marshaller marshaller = jaxbContext.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        StringWriter sw = new StringWriter();
        marshaller.marshal(agreementDTO, sw);
        return sw.toString();
    }

    private void storeXmlToFile(String agreementUuid,
                                String agreementXml) throws IOException {
        File directory = new File(agreementExportPath);
        if (!directory.exists() && directory.mkdirs()) {
            log.info("Created export directory: {}", directory.getAbsolutePath());
        }

        File file = new File(directory, "agreement-" + agreementUuid + ".xml");

        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            bw.write(agreementXml);
        }

        log.info("Saved file: {}", file.getAbsolutePath());
    }
}
