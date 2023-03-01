package fa.training.service;

import fa.training.dto.CategoryDTO;
import fa.training.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
  String addCategory(CategoryDTO categoryDTO);
  String addCategoryList(List<CategoryDTO> categoryDTO);

  Boolean deleteCategory(long categoryId);
  String editCategory(CategoryDTO categoryDTO);

  CategoryDTO findByName(String name);
  List<CategoryDTO> findAll();

  List<CategoryDTO> findByNameContaining(String name);
  CategoryDTO findById(long id);

  Page<CategoryDTO> findByNameContaining(String name, Pageable pageable);
}
