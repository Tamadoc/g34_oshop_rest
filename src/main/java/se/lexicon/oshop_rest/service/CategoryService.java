package se.lexicon.oshop_rest.service;

import se.lexicon.oshop_rest.dto.CategoryDto;
import se.lexicon.oshop_rest.exception.RecordDuplicateException;
import se.lexicon.oshop_rest.exception.RecordNotFoundException;

import java.util.List;

public interface CategoryService {

    CategoryDto save(CategoryDto dto) throws RecordDuplicateException;

    CategoryDto update(CategoryDto dto) throws RecordNotFoundException;

    List<CategoryDto> getAll();

    CategoryDto findById(int id);

    void deleteById(int id) throws RecordNotFoundException;
}
