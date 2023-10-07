package br.com.sgc.controller;

import br.com.sgc.service.CompetencyService;
import br.com.sgc.service.dto.CompetencyDto;
import br.com.sgc.service.dto.DropdownCategoryDto;
import br.com.sgc.service.dto.filter.CompetencyFilterDto;
import br.com.sgc.util.MessageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Page<CompetencyDto>> findAll(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(competencyService.findAll(pageable));
    }

    @GetMapping("/competencias-dropdown")
    @ApiOperation(MessageUtil.OPERATION_COMPETENCY_FIND_ALL_DROPDOWN)
    public ResponseEntity<List<DropdownCategoryDto>> findAllDropdown() {
        return ResponseEntity.status(HttpStatus.OK).body(competencyService.findAllDropdown());
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

    @PostMapping("/filter")
    public ResponseEntity<Page<CompetencyDto>> columnsFilter(Pageable pageable, @RequestBody CompetencyFilterDto filter) {
        return ResponseEntity.ok(competencyService.columnsFilter(pageable, filter));
    }

}
