package br.com.sgc.controller;

import br.com.sgc.service.SeniorityService;
import br.com.sgc.service.dto.SeniorityDto;
import br.com.sgc.util.MessageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/senioridade")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@Api(tags = "Senioridade")
public class SeniorityController {

    private final SeniorityService seniorityService;

    @GetMapping
    @ApiOperation(MessageUtil.OPERATION_SENIORITY_FIND_ALL)
    public ResponseEntity<List<SeniorityDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(seniorityService.findAll());
    }

    @GetMapping("/{idSeniority}")
    @ApiOperation(MessageUtil.OPERATION_SENIORITY_FIND_BY_ID)
    public ResponseEntity<SeniorityDto> findById(@PathVariable Long idSeniority) {
        return ResponseEntity.status(HttpStatus.OK).body(seniorityService.findById(idSeniority));
    }

}
