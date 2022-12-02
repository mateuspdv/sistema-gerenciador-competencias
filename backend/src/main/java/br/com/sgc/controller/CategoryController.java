package br.com.sgc.controller;

import br.com.sgc.service.CategoryService;
import br.com.sgc.service.dto.CategoryDto;
import br.com.sgc.util.MessageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categoria")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
@Api(tags = "Categoria")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    @ApiOperation(MessageUtil.OPERATION_CATEGORY_FIND_ALL)
    public ResponseEntity<List<CategoryDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findAll());
    }

    @GetMapping("/{idCategory}")
    @ApiOperation(MessageUtil.OPERATION_CATEGORY_FIND_BY_ID)
    public ResponseEntity<CategoryDto> findById(@PathVariable Long idCategory) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findById(idCategory));
    }

}
