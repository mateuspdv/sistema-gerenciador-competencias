package br.com.sgc.controller;

import br.com.sgc.service.TrainingClassStatusService;
import br.com.sgc.service.dto.DropdownDto;
import br.com.sgc.service.dto.TrainingClassStatusDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/training-class-status")
@RequiredArgsConstructor
public class TrainingClassStatusController {

    private final TrainingClassStatusService trainingClassStatusService;

    @GetMapping
    public ResponseEntity<List<DropdownDto>> findAll() {
        return ResponseEntity.ok(trainingClassStatusService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainingClassStatusDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(trainingClassStatusService.findById(id));
    }

}
