package fa.training.service.impl;


import fa.training.config.ModelMapperConfig;
import fa.training.dto.CategoryDTO;
import fa.training.entity.Category;
import fa.training.repository.CategoryRepository;
import fa.training.service.CategoryService;
import fa.training.mapper.CategoryMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final ModelMapperConfig modelMapperConfig;
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper, ModelMapperConfig modelMapperConfig) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.modelMapperConfig = modelMapperConfig;
    }

    @Override
    public String addCategory(CategoryDTO categoryDTO) {
        //Convert DTO to Entity
        Category category = categoryMapper.castDTOToEntity(categoryDTO);
        categoryRepository.save(category);
        return "Add Complete";
    }

    @Override
    public String addCategoryList(List<CategoryDTO> categoryDTOs) {
        for (CategoryDTO categoryDTO : categoryDTOs) {
            Category category = categoryMapper.castDTOToEntity(categoryDTO);
            categoryRepository.save(category);
        }
        return "Add List Complete";
    }

    @Override
    public List<CategoryDTO> findAll() {
        List<Category> categories= categoryRepository.findAll();
        return categoryMapper.castListEntityToDTO(categories);
    }

    @Override
    public List<CategoryDTO> findByNameContaining(String name) {
        return categoryMapper.castListEntityToDTO(categoryRepository.findByNameContaining(name));
    }

    @Override
    public Boolean deleteCategory(long categoryId) {
       try{
           categoryRepository.deleteById(categoryId);
           return true;
       }catch (Exception e) {
           return false;
       }
    }

    @Override
    public String editCategory(CategoryDTO categoryDTO) {
        Category category= categoryMapper.castDTOToEntity(categoryDTO);
        categoryRepository.saveAndFlush(category);
        return "Edit Complete";
    }

    @Override
    public CategoryDTO findByName(String name) {
        Category category = categoryRepository.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("NOT FOUND"));
        return categoryMapper.castEntityToDTO(category);
    }
    @Override
    public Page<CategoryDTO> findByNameContaining(String name, Pageable pageable) {
       return categoryRepository.findByNameContaining(name, pageable)
               .map(category -> modelMapperConfig.modelMapper().map(category, CategoryDTO.class));
    }

    @Override
    public CategoryDTO findById(long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("NOT FOUND"));
        return categoryMapper.castEntityToDTO(category);
    }

}
