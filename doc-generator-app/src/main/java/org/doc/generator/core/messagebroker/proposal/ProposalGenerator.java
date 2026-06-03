package org.doc.generator.core.messagebroker.proposal;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.doc.generator.core.api.dto.AgreementDTO;
import org.doc.generator.core.messagebroker.proposalack.ProposalGenerationAckQueueSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.io.FileOutputStream;
import java.nio.file.Path;

@Component
@RequiredArgsConstructor
public class ProposalGenerator {

    @Value( "${proposals.directory.path}" )
    private String proposalsDirectoryPath;

    private final ProposalGenerationAckQueueSender ackQueueSender;

    public String generateProposalAndStoreToFile (AgreementDTO agreementDTO) throws Exception {
        String filePath = proposalsDirectoryPath + "/" + buildFileName(agreementDTO);
        Path path = Path.of(filePath);

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(path.toFile()));

        document.open();

        document.add(new Paragraph("Insurance Proposal"));
        document.add(new Paragraph(" "));
        document.add(new Paragraph("Agreement UUID: " + agreementDTO.getUuid()));
        document.add(new Paragraph("Agreement Details: "));
        document.add(new Paragraph(agreementDTO.toString()));

        document.close();

        ackQueueSender.send(agreementDTO, filePath);

        return filePath;
    }

    private String buildFileName(AgreementDTO agreementDTO) {
        return "agreement-proposal-" + agreementDTO.getUuid() + ".pdf";
    }
}
