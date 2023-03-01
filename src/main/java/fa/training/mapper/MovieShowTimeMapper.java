package fa.training.mapper;

import fa.training.dto.MovieShowTimeDTO;
import fa.training.dto.SeatDTO;
import fa.training.entity.MovieShowTime;
import fa.training.repository.HallRepository;
import fa.training.repository.MovieRepository;
import fa.training.service.HallService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MovieShowTimeMapper {

    private final MovieRepository movieRepository;
    private final HallService hallService;
    private final HallRepository hallRepository;
    private final MovieMapper movieMapper;

    public MovieShowTimeMapper(MovieRepository movieRepository, HallService hallService, HallRepository hallRepository, MovieMapper movieMapper) {
        this.movieRepository = movieRepository;
        this.hallService = hallService;
        this.hallRepository = hallRepository;
        this.movieMapper = movieMapper;
    }

    public MovieShowTimeDTO castEntityToDTO(MovieShowTime movieShowTime) {

        Set<SeatDTO> seatDTOS = movieShowTime.getSeats().stream()
                .map(seat -> {
                    SeatDTO seatDTO = new SeatDTO();
                    seatDTO.setName(seat.getName());
                    return seatDTO;
                }).collect(Collectors.toSet());
        return  MovieShowTimeDTO.builder()
                .date(movieShowTime.getDate())
                .movieDTO(movieMapper.castEntityToDTO(movieRepository.findByName(movieShowTime.getMovie().getName()).orElseThrow()))
                .hallDTO(hallService.findByName(movieShowTime.getHall().getName()))
                .time(movieShowTime.getTime())
                .seatDTOS(seatDTOS).build();
    }

    public List<MovieShowTimeDTO> castListEntityToDTO(List<MovieShowTime> movieShowTimes) {
        List<MovieShowTimeDTO> movieShowTimeDTOS = new ArrayList<>();
        for(MovieShowTime movieShowTime : movieShowTimes) {
            MovieShowTimeDTO movieShowTimeDTO = castEntityToDTO(movieShowTime);
            movieShowTimeDTOS.add(movieShowTimeDTO);
        }
        return movieShowTimeDTOS;
    }

    public MovieShowTime castDTOToEntity(MovieShowTimeDTO movieShowTimeDTO) {
        MovieShowTime movieShowTime = new MovieShowTime();
        movieShowTime.setDate(movieShowTimeDTO.getDate());
        movieShowTime.setTime(movieShowTimeDTO.getTime());
        movieShowTime.setMovie(movieRepository.findByName(movieShowTimeDTO.getMovieDTO().getName()).orElseThrow());
        movieShowTime.setHall(hallRepository.findByName(movieShowTimeDTO.getHallDTO().getName()).orElseThrow());
        return movieShowTime;
    }
}
