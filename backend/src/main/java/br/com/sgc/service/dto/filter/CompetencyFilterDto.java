package br.com.sgc.service.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompetencyFilterDto implements Serializable {

    private String name;

    private String description;

    private Long idCategory;

}
