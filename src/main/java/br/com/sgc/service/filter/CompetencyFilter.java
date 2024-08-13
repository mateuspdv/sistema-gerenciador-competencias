package br.com.sgc.service.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompetencyFilter implements Serializable {

    private String name;

    private String description;

    private Long idCategory;

}
