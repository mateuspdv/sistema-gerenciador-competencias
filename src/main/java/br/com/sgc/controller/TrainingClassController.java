package br.com.sgc.controller;

import br.com.sgc.service.TrainingClassService;
import br.com.sgc.service.dto.TrainingClassDto;
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
@RequestMapping("/api/training-classes")
@RequiredArgsConstructor
public class TrainingClassController {

    private final TrainingClassService trainingClassService;

    @GetMapping
    public ResponseEntity<Page<TrainingClassDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(trainingClassService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainingClassDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(trainingClassService.findById(id));
    }

    @PostMapping
    public ResponseEntity<TrainingClassDto> create(@Valid @RequestBody TrainingClassDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(trainingClassService.create(dto));
    }

    @PutMapping
    public ResponseEntity<TrainingClassDto> update(@Valid @RequestBody TrainingClassDto dto) {
        return ResponseEntity.ok(trainingClassService.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> disableById(@PathVariable Long id) {
        trainingClassService.disableById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
