package fa.training.controller.admin;

import fa.training.dto.CategoryDTO;
import fa.training.dto.MovieDTO;
import fa.training.entity.Movie;
import fa.training.mapper.MovieMapper;
import fa.training.service.CategoryService;
import fa.training.service.MovieService;
import fa.training.service.StorageService;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"admin/movies"})
public class MovieController {
    private final CategoryService categoryService;
    private final MovieService movieService;
    private final StorageService storageService;
    private final MovieMapper movieMapper;

    @ModelAttribute("categories")
    public List<CategoryDTO> getCategoryList() {
        return this.categoryService.findAll();
    }

    @ModelAttribute("movieList")
    public List<MovieDTO> getMovieList() {
        return this.movieService.findAllFMovies();
    }

    public MovieController(MovieService movieService, CategoryService categoryService, StorageService storageService, MovieMapper movieMapper) {
        this.movieService = movieService;
        this.categoryService = categoryService;
        this.storageService = storageService;
        this.movieMapper = movieMapper;
    }

    @GetMapping({"add"})
    public String hello(Model model) {
        model.addAttribute("movie", new MovieDTO());
        return "admin/movies/addOrEdit";
    }

    @GetMapping({"edit/{name}"})
    public ModelAndView edit(ModelMap model, @PathVariable("name") String name) {
        Optional<Movie> movieDTO = this.movieService.findByName(name);
        if (movieDTO.isPresent()) {
            model.addAttribute("movie", movieDTO);
            return new ModelAndView("edit", model);
        } else {
            model.addAttribute("message", "Movie is not existed");
            return new ModelAndView("forward:/admin/movies/");
        }
    }

    @GetMapping({"view/{id}"})
    public ModelAndView detail(ModelMap model, @PathVariable("id") Long id) {
        Optional<Movie> opt = this.movieService.findById(id);
        if (opt.isPresent()) {
            MovieDTO dto = this.movieMapper.castEntityToDTO((Movie)opt.get());
            model.addAttribute("movie", dto);
            return new ModelAndView("/admin/movies/detail", model);
        } else {
            model.addAttribute("message", "Movie is not existed");
            return new ModelAndView("forward:/admin/movies/");
        }
    }

    @GetMapping({"watch/{id}"})
    public ModelAndView watch(ModelMap model, @PathVariable("id") Long id) {
        Optional<Movie> opt = this.movieService.findById(id);
        if (opt.isPresent()) {
            MovieDTO dto = this.movieMapper.castEntityToDTO((Movie)opt.get());
            model.addAttribute("movie", dto);
            return new ModelAndView("/admin/movies/watchNow", model);
        } else {
            model.addAttribute("message", "Movie is not existed");
            return new ModelAndView("forward:/admin/movies/");
        }
    }

    @GetMapping({"delete/{name}"})
    public ModelAndView delete(ModelMap model, @PathVariable("name") String name) throws IOException {
        Optional<Movie> opt = this.movieService.findByName(name);
        if (opt.isPresent()) {
            if (!StringUtils.isEmpty(((Movie)opt.get()).getImage())) {
                this.storageService.delete(((Movie)opt.get()).getImage());
            }

            this.movieService.delete((Movie)opt.get());
            model.addAttribute("message", "Movie is not Delete");
        } else {
            model.addAttribute("message", "Movie not found");
        }

        return new ModelAndView("forward:/admin/movies", model);
    }

    @PostMapping({"saveOrUpdate"})
    public ModelAndView saveOrUpdate(ModelMap model, @RequestParam("file") MultipartFile file, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            return new ModelAndView("forward:/admin/movies");
        } else if (file.isEmpty()) {
            model.addAttribute("message", "Please select a file to upload.");
            return new ModelAndView("forward:/admin/movies", model);
        } else {
            this.movieService.addMovieFromExcel(file);
            model.addAttribute("message", "Movie is saved!");
            return new ModelAndView("forward:/admin/movies", model);
        }
    }

    @RequestMapping({""})
    public String list(ModelMap model) {
        model.addAttribute("movies", this.movieService.findAllFMovies());
        return "admin/movies/list";
    }

    @GetMapping({"search"})
    public String search(ModelMap model, @RequestParam(name = "name",required = false) String name) {
        if (StringUtils.hasText(name)) {
            model.addAttribute("categories", this.movieService.findNeededMovies(name));
        } else {
            model.addAttribute("categories", this.movieService.findAllFMovies());
        }

        return "admin/movies/search";
    }
}
