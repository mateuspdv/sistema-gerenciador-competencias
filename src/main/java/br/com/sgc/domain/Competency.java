package br.com.sgc.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "competency")
@Getter
@Setter
public class Competency implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_competency")
    @SequenceGenerator(name = "sequence_competency", sequenceName = "sequence_competency", allocationSize = 1)
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "description", length = 100, nullable = false)
    private String description;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "id_category", nullable = false)
    private Category category;

}
