package br.com.coresgc.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViewCompetencyDTO implements Serializable {

    Long id;

    String name;

    String description;

    String categoryName;

}
