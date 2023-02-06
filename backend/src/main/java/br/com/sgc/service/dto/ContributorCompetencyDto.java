package br.com.sgc.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ContributorCompetencyDto implements Serializable {

    private Long idCompetency;

    private String nameCompetency;

    private Long idLevelCompetency;

    private String nameLevelCompetency;

}
