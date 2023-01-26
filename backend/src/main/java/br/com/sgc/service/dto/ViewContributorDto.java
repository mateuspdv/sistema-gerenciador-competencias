package br.com.sgc.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ViewContributorDto implements Serializable {

    private Long id;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private String nameSeniority;

}
