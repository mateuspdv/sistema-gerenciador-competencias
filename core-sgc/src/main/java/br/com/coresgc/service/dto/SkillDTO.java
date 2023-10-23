package br.com.coresgc.service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * A DTO for the {@link br.com.coresgc.domain.Skill} entity.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SkillDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    private String description;

}
