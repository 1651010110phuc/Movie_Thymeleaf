package fa.training.mapper;

import fa.training.dto.CategoryDTO;
import fa.training.entity.Category;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryMapper {


    public CategoryDTO castEntityToDTO(Category category) {
        return  CategoryDTO.builder()
                .categoryId(category.getCategoryId())
                .name(category.getName())
                .build();
    }

    public List<CategoryDTO> castListEntityToDTO(List<Category> categories) {
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        for(Category category : categories){
            CategoryDTO categoryDTO = castEntityToDTO(category);
            categoryDTOS.add(categoryDTO);
        }
        return categoryDTOS;
    }

    public Category castDTOToEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        return category;
    }





}
