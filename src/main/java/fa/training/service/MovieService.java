package fa.training.service;


import fa.training.dto.MovieDTO;
import fa.training.entity.Category;
import fa.training.entity.Movie;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface MovieService {

    String addMovie(MovieDTO movieDTO);

    String addMovieFromExcel(MultipartFile file) throws Exception;

    Boolean deleteMovieByName(String name);

    String editMovie(MovieDTO movieDTO);

    List<MovieDTO> findNeededMovies(String name); // Native Query (Select * From... WHERE like)

    List<MovieDTO> findAllFMovies();

    List<MovieDTO> findBestMovie();//Rating > 8

    List<MovieDTO> findByCategory(String categoryName);


    Optional<Movie> findByName(String name);

    List<Movie> findByCategory(Category category);

    List<Movie> findAll();

    List<Movie> findAll(Sort sort);

    List<Movie> findAllById(Iterable<Long> longs);

    <S extends Movie> List<S> saveAll(Iterable<S> entities);

    void flush();

    <S extends Movie> S saveAndFlush(S entity);

    <S extends Movie> List<S> saveAllAndFlush(Iterable<S> entities);

    @Deprecated
    void deleteInBatch(Iterable<Movie> entities);

    void deleteAllInBatch(Iterable<Movie> entities);

    void deleteAllByIdInBatch(Iterable<Long> longs);

    void deleteAllInBatch();

    @Deprecated
    Movie getOne(Long aLong);

    @Deprecated
    Movie getById(Long aLong);

    Movie getReferenceById(Long aLong);

    <S extends Movie> List<S> findAll(Example<S> example);

    <S extends Movie> List<S> findAll(Example<S> example, Sort sort);

    Page<Movie> findAll(Pageable pageable);

    <S extends Movie> S save(S entity);

    Optional<Movie> findById(Long aLong);

    boolean existsById(Long aLong);

    long count();

    void deleteById(Long aLong);

    void delete(Movie entity);

    void deleteAllById(Iterable<? extends Long> longs);

    void deleteAll(Iterable<? extends Movie> entities);

    void deleteAll();

    <S extends Movie> Optional<S> findOne(Example<S> example);

    <S extends Movie> Page<S> findAll(Example<S> example, Pageable pageable);

    <S extends Movie> long count(Example<S> example);

    <S extends Movie> boolean exists(Example<S> example);

    <S extends Movie, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction);
}
