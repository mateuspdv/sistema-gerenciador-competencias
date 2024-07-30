package br.com.sgc.controller;

import br.com.sgc.service.CompetencyService;
import br.com.sgc.service.dto.CompetencyDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/competency")
@RequiredArgsConstructor
public class CompetencyController {

    private final CompetencyService competencyService;

    @GetMapping
    public ResponseEntity<Page<CompetencyDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(competencyService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompetencyDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(competencyService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CompetencyDto> create(@Valid @RequestBody CompetencyDto competencyDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(competencyService.create(competencyDto));
    }

    @PutMapping
    public ResponseEntity<CompetencyDto> update(@Valid @RequestBody CompetencyDto competencyDto) {
        return ResponseEntity.ok(competencyService.update(competencyDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> disableById(@PathVariable Long id) {
        competencyService.disableById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
