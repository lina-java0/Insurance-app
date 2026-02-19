package org.javaguru.travel.insurance.core.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "agreements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgreementEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", nullable = false)
    private String uuid;

    @Column(name = "date_from", nullable = false)
    private LocalDate agreementDateFrom;

    @Column(name = "date_to", nullable = false)
    private LocalDate agreementDateTo;

    @Column(name = "country", nullable = false, length = 100)
    private String country;

    @Column(name = "premium", precision = 10, scale = 2, nullable = false)
    private BigDecimal agreementPremium;
}
