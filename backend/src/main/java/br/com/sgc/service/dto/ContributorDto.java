package br.com.sgc.service.dto;

import br.com.sgc.util.MessageUtil;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class ContributorDto implements Serializable {

    private Long id;

    @NotNull(message = MessageUtil.VALIDATION_CONTRIBUTOR_FIRST_NAME_NOT_NULL)
    @NotEmpty(message = MessageUtil.VALIDATION_CONTRIBUTOR_FIRST_NAME_NOT_EMPTY)
    @Size(min = 3, message = MessageUtil.VALIDATION_CONTRIBUTOR_FIRST_NAME_SIZE_MIN)
    @Size(max = 50, message = MessageUtil.VALIDATION_CONTRIBUTOR_FIRST_NAME_SIZE_MAX)
    private String firstName;

    @NotNull(message = MessageUtil.VALIDATION_CONTRIBUTOR_LAST_NAME_NOT_NULL)
    @NotEmpty(message = MessageUtil.VALIDATION_CONTRIBUTOR_LAST_NAME_NOT_EMPTY)
    @Size(min = 3, message = MessageUtil.VALIDATION_CONTRIBUTOR_LAST_NAME_SIZE_MIN)
    @Size(max = 50, message = MessageUtil.VALIDATION_CONTRIBUTOR_LAST_NAME_SIZE_MAX)
    private String lastName;

    @NotNull(message = MessageUtil.VALIDATION_CONTRIBUTOR_CPF_NOT_NULL)
    @NotEmpty(message = MessageUtil.VALIDATION_CONTRIBUTOR_CPF_NOT_EMPTY)
    @CPF(message = MessageUtil.VALIDATION_CONTRIBUTOR_CPF_CPF)
    private String cpf;

    @NotNull(message = MessageUtil.VALIDATION_CONTRIBUTOR_EMAIL_NOT_NULL)
    @NotEmpty(message = MessageUtil.VALIDATION_CONTRIBUTOR_EMAIL_NOT_EMPTY)
    @Email(message = MessageUtil.VALIDATION_CONTRIBUTOR_EMAIL_EMAIL)
    private String email;

    private Byte[] photo;

    @NotNull(message = MessageUtil.VALIDATION_CONTRIBUTOR_BIRTH_DATE_NOT_NULL)
    @Past(message = MessageUtil.VALIDATION_CONTRIBUTOR_BIRTH_DATE_PAST)
    private LocalDate birthDate;

    @NotNull(message = MessageUtil.VALIDATION_CONTRIBUTOR_ADMISSION_DATE_NOT_NULL)
    @PastOrPresent(message = MessageUtil.VALIDATION_CONTRIBUTOR_ADMISSION_DATE_PAST_OR_PRESENT)
    private LocalDate admissionDate;

    @NotNull(message = MessageUtil.VALIDATION_CONTRIBUTOR_ID_SENIORITY_NOT_NULL)
    private Long idSeniority;

    private String nameSeniority;

}
