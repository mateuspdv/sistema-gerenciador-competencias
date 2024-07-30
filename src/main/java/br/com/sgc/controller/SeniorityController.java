package br.com.sgc.controller;

import br.com.sgc.service.SeniorityService;
import br.com.sgc.service.dto.DropdownDto;
import br.com.sgc.service.dto.SeniorityDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/seniority")
@RequiredArgsConstructor
public class SeniorityController {

    private final SeniorityService seniorityService;

    @GetMapping
    public ResponseEntity<List<DropdownDto>> findAll() {
        return ResponseEntity.ok(seniorityService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeniorityDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(seniorityService.findById(id));
    }

}
