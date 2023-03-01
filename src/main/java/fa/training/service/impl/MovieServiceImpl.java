package fa.training.service.impl;

import fa.training.dto.MovieDTO;
import fa.training.entity.Category;
import fa.training.entity.Movie;
import fa.training.mapper.MovieMapper;
import fa.training.repository.CategoryRepository;
import fa.training.repository.MovieRepository;
import fa.training.service.MovieService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final CategoryRepository categoryRepository;
    private final MovieMapper movieMapper;
    public MovieServiceImpl(MovieRepository movieRepository, CategoryRepository categoryRepository, MovieMapper movieMapper) {
        this.movieRepository = movieRepository;
        this.categoryRepository = categoryRepository;
        this.movieMapper = movieMapper;
    }
    @Override
    public String addMovie(MovieDTO movieDTO) {
        Movie movie = movieMapper.castDTOToEntity(movieDTO);
            movieRepository.save(movie);
            return "Add Complete";
    }
    @Override
    public String addMovieFromExcel(MultipartFile file) throws Exception {
        List<Movie> movies = movieMapper.castListMultipartFileToListEntity(file);
        movieRepository.saveAll(movies);
        return "Add List Complete";
    }
    @Override
    public Boolean deleteMovieByName(String name) {
        Optional<Movie> opt = movieRepository.findByName(name);
        if(opt.isPresent()) {
            Movie movie = opt.get();
            movieRepository.deleteById(movie.getId());
            return true;
        } else{
            return false;
        }
    }
    @Override
    public String editMovie(MovieDTO movieDTO) {
        Movie movie = movieMapper.castDTOToEntity(movieDTO);
            movieRepository.saveAndFlush(movie);
            return "Edit Complete";
    }
    @Override
    public List<MovieDTO> findAllFMovies() {
        List<Movie> movies = movieRepository.findAll();
        return movieMapper.castListEntityToDTO(movies);
    }
    @Override
    public List<MovieDTO> findNeededMovies(String name) {
        List<Movie> movies = movieRepository.findNeededMovies(name);
        return movieMapper.castListEntityToDTO(movies);
    }
    @Override
    public List<MovieDTO> findBestMovie() {
        List<Movie> movies = movieRepository.findBestMovie();
        return movieMapper.castListEntityToDTO(movies);
    }
    @Override
    public List<MovieDTO> findByCategory(String categoryName) {
        Category category = categoryRepository.findByName(categoryName).orElseThrow();
        List<Movie> movies = movieRepository.findByCategory(category);
        return movieMapper.castListEntityToDTO(movies);
    }

    @Override
    public Optional<Movie> findByName(String name) {
        return movieRepository.findByName(name);
    }

    @Override
    public List<Movie> findByCategory(Category category) {
        return movieRepository.findByCategory(category);
    }

    @Override
    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    @Override
    public List<Movie> findAll(Sort sort) {
        return movieRepository.findAll(sort);
    }

    @Override
    public List<Movie> findAllById(Iterable<Long> longs) {
        return movieRepository.findAllById(longs);
    }

    @Override
    public <S extends Movie> List<S> saveAll(Iterable<S> entities) {
        return movieRepository.saveAll(entities);
    }

    @Override
    public void flush() {
        movieRepository.flush();
    }

    @Override
    public <S extends Movie> S saveAndFlush(S entity) {
        return movieRepository.saveAndFlush(entity);
    }

    @Override
    public <S extends Movie> List<S> saveAllAndFlush(Iterable<S> entities) {
        return movieRepository.saveAllAndFlush(entities);
    }

    @Override
    @Deprecated
    public void deleteInBatch(Iterable<Movie> entities) {
        movieRepository.deleteInBatch(entities);
    }

    @Override
    public void deleteAllInBatch(Iterable<Movie> entities) {
        movieRepository.deleteAllInBatch(entities);
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {
        movieRepository.deleteAllByIdInBatch(longs);
    }

    @Override
    public void deleteAllInBatch() {
        movieRepository.deleteAllInBatch();
    }

    @Override
    @Deprecated
    public Movie getOne(Long aLong) {
        return movieRepository.getOne(aLong);
    }

    @Override
    @Deprecated
    public Movie getById(Long aLong) {
        return movieRepository.getById(aLong);
    }

    @Override
    public Movie getReferenceById(Long aLong) {
        return movieRepository.getReferenceById(aLong);
    }

    @Override
    public <S extends Movie> List<S> findAll(Example<S> example) {
        return movieRepository.findAll(example);
    }

    @Override
    public <S extends Movie> List<S> findAll(Example<S> example, Sort sort) {
        return movieRepository.findAll(example, sort);
    }

    @Override
    public Page<Movie> findAll(Pageable pageable) {
        return movieRepository.findAll(pageable);
    }

    @Override
    public <S extends Movie> S save(S entity) {
        return movieRepository.save(entity);
    }

    @Override
    public Optional<Movie> findById(Long aLong) {
        return movieRepository.findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return movieRepository.existsById(aLong);
    }

    @Override
    public long count() {
        return movieRepository.count();
    }

    @Override
    public void deleteById(Long aLong) {
        movieRepository.deleteById(aLong);
    }

    @Override
    public void delete(Movie entity) {
        movieRepository.delete(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {
        movieRepository.deleteAllById(longs);
    }

    @Override
    public void deleteAll(Iterable<? extends Movie> entities) {
        movieRepository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        movieRepository.deleteAll();
    }

    @Override
    public <S extends Movie> Optional<S> findOne(Example<S> example) {
        return movieRepository.findOne(example);
    }

    @Override
    public <S extends Movie> Page<S> findAll(Example<S> example, Pageable pageable) {
        return movieRepository.findAll(example, pageable);
    }

    @Override
    public <S extends Movie> long count(Example<S> example) {
        return movieRepository.count(example);
    }

    @Override
    public <S extends Movie> boolean exists(Example<S> example) {
        return movieRepository.exists(example);
    }

    @Override
    public <S extends Movie, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return movieRepository.findBy(example, queryFunction);
    }
}
