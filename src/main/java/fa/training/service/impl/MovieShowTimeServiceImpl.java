package fa.training.service.impl;

import fa.training.dto.MovieShowTimeDTO;
import fa.training.entity.MovieShowTime;
import fa.training.mapper.MovieShowTimeMapper;
import fa.training.repository.MovieShowTimeRepository;
import fa.training.service.MovieShowTimeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieShowTimeServiceImpl  implements MovieShowTimeService {
    private final MovieShowTimeRepository movieShowTimeRepository;
    private final MovieShowTimeMapper movieShowTimeMapper;

    public MovieShowTimeServiceImpl(MovieShowTimeRepository movieShowTimeRepository, MovieShowTimeMapper movieShowTimeMapper) {
        this.movieShowTimeRepository = movieShowTimeRepository;
        this.movieShowTimeMapper = movieShowTimeMapper;
    }

    @Override
    public String addMovieShowTime(MovieShowTimeDTO movieShowTimeDTO) {
        MovieShowTime movieShowTime = movieShowTimeMapper.castDTOToEntity(movieShowTimeDTO);
            movieShowTimeRepository.save(movieShowTime);
            return "Add Complete";

    }


    //kiểm tra số ghế còn lại của lịch chiếu.
    @Override
    public List<MovieShowTimeDTO> findAllAndFreeSeats() {
        List<MovieShowTime> movieShowTimes = movieShowTimeRepository.findAll();
        return movieShowTimeMapper.castListEntityToDTO(movieShowTimes);
    }

    @Override
    public List<MovieShowTimeDTO> findByDateAndFreeSeats(String date) {
        List<MovieShowTime> movieShowTimes = movieShowTimeRepository.findByDate(date);
        return movieShowTimeMapper.castListEntityToDTO(movieShowTimes);
    }


}
