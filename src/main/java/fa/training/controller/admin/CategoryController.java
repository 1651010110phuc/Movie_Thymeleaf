package fa.training.controller.admin;

import fa.training.config.ModelMapperConfig;
import fa.training.dto.CategoryDTO;
import fa.training.repository.CategoryRepository;
import fa.training.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.springframework.util.StringUtils.hasText;


@Controller
@RequestMapping("admin/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final ModelMapperConfig modelMapperConfig;

    private final CategoryRepository categoryRepository;
    public CategoryController(CategoryService categoryService, CategoryRepository categoryRepository, ModelMapperConfig modelMapperConfig) {
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
        this.modelMapperConfig = modelMapperConfig;
    }
    @GetMapping("add")
    public String add(Model model) {
        model.addAttribute("category",new CategoryDTO());
        return "admin/categories/addOrEdit";
    }

    @GetMapping("edit/{categoryId}")
    public ModelAndView edit(ModelMap model, @PathVariable("categoryId") long categoryId) {
        CategoryDTO categoryDTO = categoryService.findById(categoryId);
        if(!categoryDTO.equals(null)) {
            model.addAttribute("category", categoryDTO);
            return new ModelAndView("/admin/categories/addOrEdit", model);
        }
        model.addAttribute("message","Category is not existed");
        return new ModelAndView("forward:/admin/categories/");



    }

    @GetMapping("delete/{categoryId}")
    public ModelAndView delete(ModelMap model,@PathVariable("categoryId") long categoryId) {
        model.addAttribute("message", categoryService.deleteCategory(categoryId));
        return new ModelAndView("forward:/admin/categories",model);
    }
    @PostMapping("saveOrUpdate")
    public ModelAndView saveOrUpdate(ModelMap model,
                                     @Valid @ModelAttribute("category") CategoryDTO categoryDTO,
                                     BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("admin/categories/addOrEdit");
        }
        categoryService.addCategory(categoryDTO);
        model.addAttribute("message","Category is saved!");
        return new ModelAndView("forward:/admin/categories",model);
    }
    @RequestMapping("")
    public String list(ModelMap model) {
        model.addAttribute("categories",categoryService.findAll());
        return "admin/categories/list";
    }
    @GetMapping("search")
    public String search(ModelMap model, @RequestParam(name="name", required=false) String name) {
        if(hasText(name)) {
            model.addAttribute("categories", categoryService.findByNameContaining(name));
        }
        else {
            model.addAttribute("categories", categoryService.findAll());
        }
        return "admin/categories/search";
    }

    @GetMapping("search/paginated")
    public String search(ModelMap model,
                         @RequestParam(name="name", required=false) String name,
                         @RequestParam("page") Optional<Integer> page,
                         @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage, pageSize, Sort.by("name"));
        Page<CategoryDTO> resultPage = null;
        if(hasText(name)) {
            resultPage = categoryService.findByNameContaining(name,pageable);
            model.addAttribute("name",name);
        }
        else {
            resultPage = categoryRepository.findAll(pageable)
                    .map(category -> modelMapperConfig.modelMapper().map(category,CategoryDTO.class));
        }
        int totalPages = resultPage.getTotalPages();
        if(totalPages > 0) {
            int start = Math.max(1,currentPage -2);
            int end = Math.min(currentPage+2,totalPages);
            if(totalPages > 5) {
                if(end == totalPages) {
                    start = end -5;
                }
                else if( start==1) {
                    end = start + 5;
                }
            }
            List<Integer> pageNumbers = IntStream.rangeClosed(start,end)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("categoryPage",resultPage);

        return "admin/categories/search-paginated";
    }




//
//
//    @PostMapping("/add_category")
//    public ResponseEntity<String> addCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
//        return ResponseEntity.ok(categoryService.addCategory(categoryDTO));
//    }
//    @PostMapping("/add_category1")
//    public ResponseEntity<String> addCategoryList(@Valid @RequestBody List<CategoryDTO> categoryDTOs) {
//        return ResponseEntity.ok(categoryService.addCategoryList(categoryDTOs));
//    }
//    @DeleteMapping("/del_category")
//    public ResponseEntity<String> deleteCategory(@RequestParam(value = "id") long categoryId) {
//        if (Objects.equals(Boolean.TRUE, categoryService.deleteCategory(categoryId))) {
//            return ResponseEntity.ok("Delete complete");
//        } else {
//            return ResponseEntity.ok("Can not find Category which is deleted");
//        }
//    }
//    @PostMapping("/edit_category")
//    public ResponseEntity<String> editCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
//        return ResponseEntity.ok(categoryService.editCategory(categoryDTO));
//    }
//    //Code này ngu nhỉ :)))) Tự nhiên tìm tên có tên :)) Thôi sửa thành get có tồn tại k
//    @GetMapping("/get_category")
//    public ResponseEntity<CategoryDTO> getCategoryExist(@RequestParam("name") String name){
//        return ResponseEntity.ok(categoryService.findByName(name));
//    }
//
//    @GetMapping("/categories")
//    public ResponseEntity<List<CategoryDTO>> getAllCategory(){
//        return ResponseEntity.ok(categoryService.findAll());
//    }




}


