package org.doc.generator.core.api.dto;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@XmlRootElement(name = "person")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonDTO {

    @XmlElement
    private String personFirstName;

    @XmlElement
    private String personLastName;

    @XmlElement
    private String personCode;

    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate personBirthDate;

    @XmlElement
    private String medicalRiskLimitLevel;

    @XmlElementWrapper(name = "risks")
    @XmlElement(name = "risk")
    private List<RiskDTO> risks;
}
