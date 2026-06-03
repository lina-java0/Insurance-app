package org.export.travel.insurance.core.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.type.YesNoConverter;

@Entity
@Table(name = "agreement_proposals")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgreementProposalAckEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "agreement_uuid", nullable = false)
    private String agreementUuid;

    @Column(name = "already_generated", nullable = false)
    @Convert(converter = YesNoConverter.class)
    private Boolean alreadyGenerated;

    @Column(name = "proposal_file_path", nullable = false)
    private String proposalFilePath;
}
