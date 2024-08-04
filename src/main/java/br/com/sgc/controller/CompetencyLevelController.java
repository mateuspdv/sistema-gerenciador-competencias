package br.com.sgc.controller;

import br.com.sgc.service.CompetencyLevelService;
import br.com.sgc.service.dto.CompetencyLevelDto;
import br.com.sgc.service.dto.DropdownDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/competency-level")
@RequiredArgsConstructor
public class CompetencyLevelController {

    private final CompetencyLevelService competencyLevelService;

    @GetMapping
    public ResponseEntity<List<DropdownDto>> findAll() {
        return ResponseEntity.ok(competencyLevelService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompetencyLevelDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(competencyLevelService.findById(id));
    }

}
