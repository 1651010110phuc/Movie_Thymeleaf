package fa.training.service;

import fa.training.dto.MovieShowTimeDTO;

import java.text.ParseException;
import java.util.List;

public interface MovieShowTimeService {

    String addMovieShowTime(MovieShowTimeDTO movieShowTimeDTO) throws ParseException;

    List<MovieShowTimeDTO> findAllAndFreeSeats();
    List<MovieShowTimeDTO> findByDateAndFreeSeats(String date);

}
