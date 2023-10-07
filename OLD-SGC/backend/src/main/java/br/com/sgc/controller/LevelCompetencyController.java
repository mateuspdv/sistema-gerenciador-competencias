package br.com.sgc.controller;

import br.com.sgc.service.LevelCompetencyService;
import br.com.sgc.service.dto.LevelCompetencyDto;
import br.com.sgc.util.MessageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nivel-competencia")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@Api(tags = "Nível da Competência")
public class LevelCompetencyController {

    private final LevelCompetencyService levelCompetencyService;

    @GetMapping
    @ApiOperation(MessageUtil.OPERATION_LEVEL_COMPETENCY_FIND_ALL)
    public ResponseEntity<List<LevelCompetencyDto>> findAll() {
        return ResponseEntity.ok(levelCompetencyService.findAll());
    }

    @GetMapping("/{idLevelCompetency}")
    @ApiOperation(MessageUtil.OPERATION_LEVEL_COMPETENCY_FIND_BY_ID)
    public ResponseEntity<LevelCompetencyDto> findById(@PathVariable Long idLevelCompetency) {
        return ResponseEntity.ok(levelCompetencyService.findById(idLevelCompetency));
    }

}
