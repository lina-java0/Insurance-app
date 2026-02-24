package org.export.travel.insurance.core.api.dto;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@XmlRootElement(name = "agreementDTO")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgreementDTO {

    @XmlElement
    private String uuid;

    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate agreementDateFrom;

    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate agreementDateTo;

    @XmlElement
    private String country;

    @XmlElementWrapper(name = "selectedRisks")
    @XmlElement(name = "risk")
    private List<String> selectedRisks;

    @XmlElementWrapper(name = "people")
    @XmlElement(name = "person")
    private List<PersonDTO> people;

    @XmlElement
    private BigDecimal agreementPremium;
}
