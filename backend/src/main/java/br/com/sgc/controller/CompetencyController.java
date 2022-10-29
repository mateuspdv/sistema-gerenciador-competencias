package br.com.sgc.controller;

import br.com.sgc.service.CompetencyService;
import br.com.sgc.service.dto.CompetencyDto;
import br.com.sgc.util.MessageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/competencia")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@Api(tags = "Competência")
public class CompetencyController {

    private final CompetencyService competencyService;

    @GetMapping
    @ApiOperation(MessageUtil.OPERATION_COMPETENCY_FIND_ALL)
    public ResponseEntity<List<CompetencyDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(competencyService.findAll());
    }

    @GetMapping("/{idCompetency}")
    @ApiOperation(MessageUtil.OPERATION_COMPETENCY_FIND_BY_ID)
    public ResponseEntity<CompetencyDto> findById(@PathVariable Long idCompetency) {
        return ResponseEntity.status(HttpStatus.OK).body(competencyService.findById(idCompetency));
    }

    @PostMapping
    @ApiOperation(MessageUtil.OPERATION_COMPETENCY_SAVE)
    public ResponseEntity<CompetencyDto> create(@Valid @RequestBody CompetencyDto competencyDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(competencyService.create(competencyDto));
    }

    @PutMapping
    @ApiOperation(MessageUtil.OPERATION_COMPETENCY_UPDATE)
    public ResponseEntity<CompetencyDto> update(@Valid @RequestBody CompetencyDto competencyDto) {
        return ResponseEntity.status(HttpStatus.OK).body(competencyService.update(competencyDto));
    }

    @DeleteMapping("/{idCompetency}")
    @ApiOperation(MessageUtil.OPERATION_COMPETENCY_DELETE_BY_ID)
    public ResponseEntity<Void> deleteById(@PathVariable Long idCompetency) {
        competencyService.deleteById(idCompetency);
        return ResponseEntity.noContent().build();
    }

}
