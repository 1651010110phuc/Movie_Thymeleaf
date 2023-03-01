package fa.training.repository;

import fa.training.entity.Category;
import fa.training.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Optional<Movie> findByName(String name);
    List<Movie> findByCategory(Category category);
    @Query(value = "select m.* from cinema.movie m where m.rating >= 8.0", nativeQuery = true)
    List<Movie> findBestMovie();

    @Query(value = "SELECT * " +
            "FROM cinema.movie m " +
            "WHERE m.name LIKE %:name%", nativeQuery = true)
    List<Movie> findNeededMovies(String name);

}
