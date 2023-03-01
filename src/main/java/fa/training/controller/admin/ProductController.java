package fa.training.controller.admin;

import fa.training.dto.CategoryDTO;
import fa.training.dto.ProductDTO;
import fa.training.entity.Category;
import fa.training.entity.Product;
import fa.training.service.CategoryService;
import fa.training.service.ProductService;
import fa.training.service.StorageService;
import org.springframework.beans.BeanUtils;
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
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.springframework.util.StringUtils.hasText;


@Controller
@RequestMapping("admin/products")
public class ProductController {
    private final CategoryService categoryService;
    private final ProductService productService;
    private final StorageService storageService;

    public ProductController(CategoryService categoryService, ProductService productService, StorageService storageService) {
        this.categoryService = categoryService;

        this.productService = productService;
        this.storageService = storageService;
    }
    @ModelAttribute("categories")
    public List<CategoryDTO> getCategoryList()  {
        return categoryService.findAll();
    }

    @GetMapping("add")
    public String add(Model model) {
        model.addAttribute("product",new ProductDTO());
        return "admin/products/addOrEdit";
    }

    @GetMapping("edit/{productId}")
    public ModelAndView edit(ModelMap model, @PathVariable("productId") Long productId) {
        CategoryDTO categoryDTO = categoryService.findById(productId);
        if(!categoryDTO.equals(null)) {
            model.addAttribute("category", categoryDTO);
            return new ModelAndView("/admin/products/addOrEdit", model);
        }
        model.addAttribute("message","Product is not existed");
        return new ModelAndView("forward:/admin/products/");
    }

    @GetMapping("delete/{productId}")
    public ModelAndView delete(ModelMap model,
                               @PathVariable("productId") long productId) {
        model.addAttribute("message", categoryService.deleteCategory(productId));
        return new ModelAndView("forward:/admin/products",model);
    }
    @PostMapping("saveOrUpdate")
    public ModelAndView saveOrUpdate(ModelMap model,
                                     @ModelAttribute("product") ProductDTO dto,
                                     BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("admin/products/addOrEdit");
        }

        if (dto != null) {
            Product entity = new Product();
            BeanUtils.copyProperties(dto,entity);
            Category category = new Category();
            category.setCategoryId(dto.getCategoryId());
            entity.setCategory(category);
            if(!dto.getImageFile().isEmpty()) {
                UUID uuid = UUID.randomUUID();
                String uuString = uuid.toString();
                entity.setImage(storageService.getStoredFileName(dto.getImageFile(),uuString));
                storageService.store(dto.getImageFile(), dto.getImage());
            }

            productService.save(entity);
            model.addAttribute("message","Movie is saved!");
            return new ModelAndView("forward:/admin/products",model);
        }
        else {
            return new ModelAndView("admin/products/addOrEdit");
        }

    }
    @RequestMapping("")
    public String list(ModelMap model) {
        model.addAttribute("products",categoryService.findAll());
        return "admin/products/list";
    }
    @GetMapping("search")
    public String search(ModelMap model, @RequestParam(name="name", required=false) String name) {
        if(hasText(name)) {
            model.addAttribute("products", categoryService.findByNameContaining(name));
        }
        else {
            model.addAttribute("products", categoryService.findAll());
        }
        return "admin/products/search";
    }

//    @GetMapping("search/paginated")
//    public String search(ModelMap model,
//                         @RequestParam(name="name", required=false) String name,
//                         @RequestParam("page") Optional<Integer> page,
//                         @RequestParam("size") Optional<Integer> size) {
//        int currentPage = page.orElse(1);
//        int pageSize = size.orElse(5);
//        Pageable pageable = PageRequest.of(currentPage, pageSize, Sort.by("name"));
//        Page<CategoryDTO> resultPage = null;
//        if(hasText(name)) {
//            resultPage = categoryService.findByNameContaining(name,pageable);
//            model.addAttribute("name",name);
//        }
//        else {
////            resultPage = categoryRepository.findAll(pageable)
////                    .map(category -> modelMapperConfig.modelMapper().map(category,CategoryDTO.class));
//        }
//        int totalPages = resultPage.getTotalPages();
//        if(totalPages > 0) {
//            int start = Math.max(1,currentPage -2);
//            int end = Math.min(currentPage+2,totalPages);
//            if(totalPages > 5) {
//                if(end == totalPages) {
//                    start = end -5;
//                }
//                else if( start==1) {
//                    end = start + 5;
//                }
//            }
//            List<Integer> pageNumbers = IntStream.rangeClosed(start,end)
//                    .boxed()
//                    .collect(Collectors.toList());
//            model.addAttribute("pageNumbers", pageNumbers);
//        }
//        model.addAttribute("categoryPage",resultPage);
//
//        return "admin/products/search-paginated";
//    }




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
//    public ResponseEntity<String> deleteCategory(@RequestParam(value = "id") long productId) {
//        if (Objects.equals(Boolean.TRUE, categoryService.deleteCategory(productId))) {
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
//    @GetMapping("/products")
//    public ResponseEntity<List<CategoryDTO>> getAllCategory(){
//        return ResponseEntity.ok(categoryService.findAll());
//    }




}


