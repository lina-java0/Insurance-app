package org.javaguru.travel.insurance.core.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "classifier_values", indexes = {
        @Index(name = "ix_classifier_values_ic", columnList = "ic", unique = true)
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassifierValue {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "classifier_id", nullable = false)
    private Classifier classifier;

    @Column(name = "ic", nullable = false, length = 200)
    private String ic;

    @Column(name = "description", nullable = false, length = 500)
    private String description;
}
