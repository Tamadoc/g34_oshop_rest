package se.lexicon.oshop_rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.lexicon.oshop_rest.dto.CategoryDto;
import se.lexicon.oshop_rest.exception.RecordDuplicateException;
import se.lexicon.oshop_rest.exception.RecordNotFoundException;
import se.lexicon.oshop_rest.service.CategoryService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAll() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> findById(@PathVariable("id") Integer id) throws RecordNotFoundException {
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryDto> deleteById(@PathVariable("id") Integer id) throws RecordNotFoundException {
        categoryService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/")
    public ResponseEntity<CategoryDto> save(@RequestBody @Valid CategoryDto categoryDto) throws RecordDuplicateException {
        CategoryDto result = categoryService.save(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody @Valid CategoryDto categoryDto) throws RecordNotFoundException {
        categoryService.update(categoryDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
