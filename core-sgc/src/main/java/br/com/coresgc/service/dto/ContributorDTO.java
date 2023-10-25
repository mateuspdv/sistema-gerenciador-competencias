package br.com.coresgc.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link br.com.coresgc.domain.Contributor} entity.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ContributorDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    private String name;

    @NotNull
    @Size(min = 3, max = 50)
    private String lastName;

    @NotNull
    @Size(min = 11, max = 11)
    private String cpf;

    @NotNull
    @Size(min = 3, max = 50)
    private String email;

    @Lob
    private byte[] photo;

    private String photoContentType;

    @NotNull
    private LocalDate birthDate;

    @NotNull
    private LocalDate admissionDate;

    @NotNull
    private ZonedDateTime creationDate;

    @NotNull
    private ZonedDateTime lastUpdateDate;

    private SeniorityDTO seniority;

    private Set<CompetencyDTO> competences = new HashSet<>();

}
