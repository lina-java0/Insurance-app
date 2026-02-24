package org.export.travel.insurance.core.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "country_default_day_rate", indexes = {
        @Index(name = "ix_country_default_day_rate_ic", columnList = "country_ic", unique = true)
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CountryDefaultDayRate {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "country_ic", nullable = false, length = 200)
    private String countryIc;

    @Column(name = "default_day_rate", precision = 10, scale = 2, nullable = false)
    private BigDecimal defaultDayRate;
}
