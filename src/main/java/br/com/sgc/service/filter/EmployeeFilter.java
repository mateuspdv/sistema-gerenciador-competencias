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
public class EmployeeFilter implements Serializable {

    private String name;

    private String lastName;

    private String email;

    private Long idSeniority;

}
