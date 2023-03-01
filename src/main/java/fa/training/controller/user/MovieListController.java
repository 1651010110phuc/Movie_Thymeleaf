package fa.training.controller.user;

import fa.training.dto.CategoryDTO;
import fa.training.dto.MovieDTO;
import fa.training.entity.Movie;
import fa.training.mapper.MovieMapper;
import fa.training.service.CategoryService;
import fa.training.service.MovieService;
import fa.training.service.StorageService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"movies"})
public class MovieListController {
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

    public MovieListController(MovieService movieService, CategoryService categoryService, StorageService storageService, MovieMapper movieMapper) {
        this.movieService = movieService;
        this.categoryService = categoryService;
        this.storageService = storageService;
        this.movieMapper = movieMapper;
    }

    @GetMapping({"view/{id}"})
    public ModelAndView detail(ModelMap model, @PathVariable("id") Long id) {
        Optional<Movie> opt = this.movieService.findById(id);
        if (opt.isPresent()) {
            MovieDTO dto = this.movieMapper.castEntityToDTO((Movie)opt.get());
            model.addAttribute("movie", dto);
            return new ModelAndView("/user/detail", model);
        } else {
            model.addAttribute("message", "Movie is not existed");
            return new ModelAndView("forward:/user/user-list/");
        }
    }

    @RequestMapping({""})
    public String list(ModelMap model) {
        model.addAttribute("movies", this.movieService.findAllFMovies());
        return "user/user-list";
    }

    @RequestMapping({"/cartoon"})
    public String listCartoon(ModelMap model) {
        List<MovieDTO> list = this.movieService.findAllFMovies();
        List<MovieDTO> listShow = new ArrayList();
        Iterator var4 = list.iterator();

        while(var4.hasNext()) {
            MovieDTO movie = (MovieDTO)var4.next();
            if (movie.getCategoryDTO().getName().equals("Cartoon")) {
                listShow.add(movie);
            }
        }

        model.addAttribute("listFound", listShow);
        return "user/user-list-category";
    }

    @RequestMapping({"/action"})
    public String listAction(ModelMap model) {
        List<MovieDTO> list = this.movieService.findAllFMovies();
        List<MovieDTO> listShow = new ArrayList();
        Iterator var4 = list.iterator();

        while(var4.hasNext()) {
            MovieDTO movie = (MovieDTO)var4.next();
            if (movie.getCategoryDTO().getName().equals("Action")) {
                listShow.add(movie);
            }
        }

        model.addAttribute("listFound", listShow);
        return "user/user-list-category";
    }

    @RequestMapping({"/romantic"})
    public String listRomantic(ModelMap model) {
        List<MovieDTO> list = this.movieService.findAllFMovies();
        List<MovieDTO> listShow = new ArrayList();
        Iterator var4 = list.iterator();

        while(var4.hasNext()) {
            MovieDTO movie = (MovieDTO)var4.next();
            if (movie.getCategoryDTO().getName().equals("Romantic")) {
                listShow.add(movie);
            }
        }

        model.addAttribute("listFound", listShow);
        return "user/user-list-category";
    }

    @RequestMapping({"/honor"})
    public String listHonor(ModelMap model) {
        List<MovieDTO> list = this.movieService.findAllFMovies();
        List<MovieDTO> listShow = new ArrayList();
        Iterator var4 = list.iterator();

        while(var4.hasNext()) {
            MovieDTO movie = (MovieDTO)var4.next();
            if (movie.getCategoryDTO().getName().equals("Honor")) {
                listShow.add(movie);
            }
        }

        model.addAttribute("listFound", listShow);
        return "user/user-list-category";
    }

    @GetMapping({"watch/{id}"})
    public ModelAndView watch(ModelMap model, @PathVariable("id") Long id) {
        Optional<Movie> opt = this.movieService.findById(id);
        if (opt.isPresent()) {
            MovieDTO dto = this.movieMapper.castEntityToDTO((Movie)opt.get());
            model.addAttribute("movie", dto);
            return new ModelAndView("/user/watchNow", model);
        } else {
            model.addAttribute("message", "Movie is not existed");
            return new ModelAndView("forward:/movies/");
        }
    }
}
