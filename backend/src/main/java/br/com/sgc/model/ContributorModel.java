package br.com.sgc.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "contributor")
@Getter
@Setter
public class ContributorModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_contributor")
    @SequenceGenerator(name = "sequence_contributor", sequenceName = "sequence_contributor", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "cpf", nullable = false)
    private String cpf;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "photo")
    private Byte[] photo;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "admission_date", nullable = false)
    private LocalDate admissionDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_seniority", referencedColumnName = "id")
    private SeniorityModel seniorityModel;

}
