package br.com.sgc.controller;

import br.com.sgc.service.CompetencyService;
import br.com.sgc.service.dto.CompetencyDto;
import br.com.sgc.util.MessageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/competencia")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@Api(tags = "CompetĂȘncia")
public class CompetencyController {

    private final CompetencyService competencyService;

    @GetMapping
    @ApiOperation(MessageUtil.OPERATION_COMPETENCY_FIND_ALL)
    public ResponseEntity<Page<CompetencyDto>> findAll(@PageableDefault(sort = "id", direction = Sort.Direction.ASC, size = 5) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(competencyService.findAll(pageable));
    }

    @GetMapping("/filtro/{query}")
    @ApiOperation(MessageUtil.OPERATION_COMPETENCY_GLOBAL_SEARCH_FILTER)
    public ResponseEntity<Page<CompetencyDto>> globalSearchFilter(@PageableDefault(size = 5) Pageable pageable, @PathVariable String query) {
        return ResponseEntity.status(HttpStatus.OK).body(competencyService.globalSearchFilter(pageable, query));
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
