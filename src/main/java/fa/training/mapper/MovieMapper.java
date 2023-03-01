package fa.training.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import fa.training.dto.CategoryDTO;
import fa.training.dto.MovieDTO;
import fa.training.entity.Category;
import fa.training.entity.Movie;
import fa.training.repository.CategoryRepository;
import fa.training.service.UploadService;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Component
public class MovieMapper {

    private final UploadService uploadService;
    private final CategoryRepository categoryRepository;

    public MovieMapper(CategoryRepository categoryRepository, UploadService uploadService) {
        this.categoryRepository = categoryRepository;
        this.uploadService = uploadService;
    }

    public  MovieDTO castEntityToDTO(Movie movie) {
        CategoryDTO categoryDTO = CategoryDTO.builder()
                .name(movie.getCategory().getName())
                .build();
        return MovieDTO.builder()
                .id(movie.getId())
                .name(movie.getName())
                .description(movie.getDescription())
                .rating(movie.getRating())
                .image(movie.getImage())
                .status(movie.getStatus())
                .videoURL(movie.getVideoURL())
                .lengthMinute(movie.getLengthMinute())
                .categoryDTO(categoryDTO).build();
    }

    public List<MovieDTO> castListEntityToDTO(List<Movie> movies) {
        List<MovieDTO> movieDTOS = new ArrayList<>();
        for (Movie movie : movies) {
            MovieDTO movieDTO = castEntityToDTO(movie);
            movieDTOS.add(movieDTO);
        }
        return movieDTOS;
    }

    public Movie castDTOToEntity(MovieDTO movieDTO) {
        Movie movie = new Movie();
        movie.setName(movieDTO.getName());
        movie.setDescription(movieDTO.getDescription());
        movie.setRating(movieDTO.getRating());
        movie.setLengthMinute(movieDTO.getLengthMinute());
        Category category = categoryRepository.findByName(movieDTO.getCategoryDTO().getName())
                .orElseThrow(() -> new NoSuchElementException("Not found Category."));
        movie.setCategory(category);
        return movie;
    }

    public List<Movie> castListMultipartFileToListEntity(MultipartFile file) throws Exception{
        List<Map<String, String>> temp = uploadService.upload(file);
        temp.remove(0);
        List<Movie> movies = new ArrayList<>();
        for (Map<String, String> mapMovie : temp) {
            ObjectMapper mapper = new ObjectMapper();
            String jsonData = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(mapMovie);
            MovieTransfer movieTransfer = mapper.readValue(jsonData, MovieTransfer.class);
            Movie movie = new Movie();
            movie.setRating(Double.parseDouble(movieTransfer.getRating()));
            movie.setDescription(movieTransfer.getDescription());
            movie.setName(movieTransfer.getName());
            movie.setLengthMinute((int)Double.parseDouble(movieTransfer.getLengthMinute()));
            movie.setCategory(categoryRepository.findByName(movieTransfer.getCategoryDTO()).orElseThrow());
            movies.add(movie);
        }
        return movies;
    }
}
