package fa.training.controller;

import fa.training.dto.AccountDto;
import fa.training.dto.CategoryDTO;
import fa.training.dto.MovieDTO;
import fa.training.entity.Account;
import fa.training.entity.Role;
import fa.training.entity.login.ERole;
import fa.training.mapper.MovieMapper;
import fa.training.repository.RoleRepository;
import fa.training.service.AccountService;
import fa.training.service.CategoryService;
import fa.training.service.MovieService;
import fa.training.service.StorageService;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping
public class IndexController {
    private final CategoryService categoryService;
    private final MovieService movieService;
    private final StorageService storageService;
    private final MovieMapper movieMapper;
    private final RoleRepository roleRepository;
    private final AccountService accountService;

    @ModelAttribute("categories")
    public List<CategoryDTO> getCategoryList() {
        return this.categoryService.findAll();
    }

    @ModelAttribute("movieList")
    public List<MovieDTO> getMovieList() {
        return this.movieService.findAllFMovies();
    }

    public IndexController(MovieService movieService, CategoryService categoryService, StorageService storageService, MovieMapper movieMapper, RoleRepository roleRepository, AccountService accountService) {
        this.movieService = movieService;
        this.categoryService = categoryService;
        this.storageService = storageService;
        this.movieMapper = movieMapper;
        this.roleRepository = roleRepository;
        this.accountService = accountService;
    }

    @RequestMapping({""})
    public String list(ModelMap model) {
        model.addAttribute("movies", this.movieService.findAllFMovies());
        return "user/index";
    }

    @RequestMapping({"/index"})
    public String index(ModelMap model) {
        model.addAttribute("movies", this.movieService.findAllFMovies());
        return "user/index";
    }
    @GetMapping("add")
    public String add(Model model) {
        model.addAttribute("account", new AccountDto());
        return "user/addOrEdit";
    }

    @PostMapping("saveOrUpdate")
    public ModelAndView saveOrUpdate(ModelMap model,
                                     @Valid @ModelAttribute("account") AccountDto dto,
                                     BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("user/addOrEdit");
        }
        Account entity = new Account();
        BeanUtils.copyProperties(dto, entity);
        Set<Role> roles = new HashSet<>();

        Role role= roleRepository.findByName(ERole.ROLE_USER).get();
            roles.add(role);
        entity.setCreateDate(LocalDate.now());
        entity.setIsActive(true);
        entity.setRoles(roles);
        accountService.save(entity);
        return new ModelAndView("forward:/index");
    }



}
