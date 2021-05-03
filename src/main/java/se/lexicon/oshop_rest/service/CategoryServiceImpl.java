package se.lexicon.oshop_rest.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.oshop_rest.dto.CategoryDto;
import se.lexicon.oshop_rest.entity.Category;
import se.lexicon.oshop_rest.exception.ArgumentException;
import se.lexicon.oshop_rest.exception.RecordDuplicateException;
import se.lexicon.oshop_rest.exception.RecordNotFoundException;
import se.lexicon.oshop_rest.repository.CategoryRepository;
import se.lexicon.oshop_rest.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    CategoryRepository categoryRepository;
    ModelMapper modelMapper;

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto save(CategoryDto dto) throws RecordDuplicateException {
        // 1. check dto is not null
        if (dto == null) throw new ArgumentException("CategoryDto is null");
        // 2. id should be null or = 0
        if (dto.getId() != 0) throw new ArgumentException("CategoryDto Id is not valid");
        // 3. convert dto to entity
        Category category = modelMapper.map(dto, Category.class);
        // 4. check name exist on db or no
        boolean isExist = categoryRepository.findByNameIgnoreCase(dto.getName()).isPresent();
        if (isExist)
            throw new RecordDuplicateException("Category Name should not be duplicate");

        // 5. save entity to database
        Category savedCategory = categoryRepository.save(category);
        // 6. convert entity result to dto
        CategoryDto convertedToDto = modelMapper.map(savedCategory, CategoryDto.class);
        // 7. return it
        return convertedToDto;
    }

    @Override
    public CategoryDto update(CategoryDto dto) throws RecordNotFoundException {
        if (dto == null) throw new ArgumentException("CategoryDto is null");
        if (dto.getId() != 0) throw new ArgumentException("CategoryDto Id is not valid");
        Category category = modelMapper.map(dto, Category.class);
        categoryRepository.findById(category.getId()).orElseThrow(() -> new RecordNotFoundException("Category Id is not valid"));
        Category updatedCategory = categoryRepository.save(category);
        CategoryDto convertToDto = modelMapper.map(updatedCategory, CategoryDto.class);
        return convertToDto;
    }

    @Override
    public List<CategoryDto> getAll() {
        List<Category> categoryList = new ArrayList<>();
        categoryRepository.findAll().iterator().forEachRemaining(categoryList::add);
        List<CategoryDto> categoryDtoList = categoryList.stream().map(category -> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
        return categoryDtoList;
    }

    @Override
    public CategoryDto findById(int id) {
        if (id <= 0) throw new ArgumentException("Id is not valid");
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent())
            return modelMapper.map(category, CategoryDto.class);

        return null;
    }

    @Override
    public void deleteById(int id) throws RecordNotFoundException {
        if (id <= 0) throw new ArgumentException("Is is not valid");
        categoryRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Category Id is not valid"));
        categoryRepository.deleteById(id);
    }
}
