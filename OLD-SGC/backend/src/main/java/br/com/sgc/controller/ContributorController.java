package br.com.sgc.controller;

import br.com.sgc.service.ContributorService;
import br.com.sgc.service.dto.ContributorDto;
import br.com.sgc.service.dto.ViewContributorDto;
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
@RequestMapping("/api/colaborador")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@Api(tags = "Colaborador")
public class ContributorController {

    private final ContributorService contributorService;

    @GetMapping
    @ApiOperation(MessageUtil.OPERATION_CONTRIBUTOR_FIND_ALL)
    public ResponseEntity<Page<ViewContributorDto>> findAll(@PageableDefault(sort = "id", direction = Sort.Direction.ASC, size = 5) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(contributorService.findAll(pageable));
    }

    @GetMapping("/{idContributor}")
    @ApiOperation(MessageUtil.OPERATION_CONTRIBUTOR_FIND_BY_ID)
    public ResponseEntity<ContributorDto> findById(@PathVariable Long idContributor) {
        return ResponseEntity.status(HttpStatus.OK).body(contributorService.findById(idContributor));
    }

    @PostMapping
    @ApiOperation(MessageUtil.OPERATION_CONTRIBUTOR_CREATE)
    public ResponseEntity<ContributorDto> create(@Valid @RequestBody ContributorDto contributorDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contributorService.create(contributorDto));
    }

    @PutMapping
    @ApiOperation(MessageUtil.OPERATION_CONTRIBUTOR_UPDATE)
    public ResponseEntity<ContributorDto> update(@Valid @RequestBody ContributorDto contributorDto) {
        return ResponseEntity.status(HttpStatus.OK).body(contributorService.update(contributorDto));
    }

    @DeleteMapping("/{idContributor}")
    @ApiOperation(MessageUtil.OPERATION_CONTRIBUTOR_DELETE_BY_ID)
    public ResponseEntity<Void> deleteById(@PathVariable Long idContributor) {
        contributorService.deleteById(idContributor);
        return ResponseEntity.noContent().build();
    }

}
