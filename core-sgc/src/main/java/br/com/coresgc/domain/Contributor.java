package br.com.coresgc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A Contributor.
 */
@Entity
@Table(name = "contributor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Contributor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_contributor")
    @SequenceGenerator(name = "sequence_contributor")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    @NotNull
    @Size(min = 11, max = 11)
    @Column(name = "cpf", length = 11, nullable = false, unique = true)
    private String cpf;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "email", length = 50, nullable = false, unique = true)
    private String email;

    @Lob
    @Column(name = "photo", nullable = false)
    private byte[] photo;

    @NotNull
    @Column(name = "photo_content_type", nullable = false)
    private String photoContentType;

    @NotNull
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @NotNull
    @Column(name = "admission_date", nullable = false)
    private LocalDate admissionDate;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private ZonedDateTime creationDate;

    @NotNull
    @Column(name = "last_update_date", nullable = false)
    private ZonedDateTime lastUpdateDate;

    @ManyToOne(optional = false)
    @NotNull
    @JoinColumn(name = "id_seniority", referencedColumnName = "id")
    private Seniority seniority;

    @ManyToMany(fetch = FetchType.LAZY)
    @NotNull
    @JoinTable(
        name = "relationship_contributor_competency",
        joinColumns = @JoinColumn(name = "id_contributor"),
        inverseJoinColumns = @JoinColumn(name = "id_competency")
    )
    @JsonIgnoreProperties(value = {"category"}, allowSetters = true)
    private Set<Competency> competences = new HashSet<>();

}
