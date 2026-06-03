package org.blacklist.core.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "black_listed_people")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlackListedPersonEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "person_first_name", nullable = false, length = 200)
    private String firstName;

    @Column(name = "person_last_name", nullable = false, length = 200)
    private String lastName;

    @Column(name = "person_code", nullable = false, length = 100)
    private String personCode;
}
