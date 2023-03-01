package fa.training.mapper;

import fa.training.dto.*;
import fa.training.entity.*;
import fa.training.entity.login.User;
import fa.training.enumerates.Time;
import fa.training.repository.HallRepository;
import fa.training.repository.MovieRepository;
import fa.training.repository.MovieShowTimeRepository;
import fa.training.repository.PeopleRepository;
import fa.training.utils.DateTimeUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;
@Component
public class TicketMapper {

    private final PeopleRepository peopleRepository;
    private final MovieShowTimeRepository movieShowTimeRepository;
    private final MovieRepository movieRepository;
    private final HallRepository hallRepository;

    public TicketMapper(PeopleRepository peopleRepository, MovieShowTimeRepository movieShowTimeRepository, MovieRepository movieRepository, HallRepository hallRepository) {
        this.peopleRepository = peopleRepository;
        this.movieShowTimeRepository = movieShowTimeRepository;
        this.movieRepository = movieRepository;
        this.hallRepository = hallRepository;
    }


    public TicketDTO castEntityToDTO(Ticket ticket) {

        //Set PeopleDTO
        People people = peopleRepository.findByEmail(ticket.getPeople().getUser().getEmail()).orElseThrow();
        PeopleDTO peopleDTO = new PeopleDTO();
        User newUser =  people.getUser();
        UserDTO userDTO = new UserDTO();
        Set<RoleDTO> roleDTOS = newUser.getRoles().stream()
                .map(role -> {
                    RoleDTO roleDto = new RoleDTO();
                    roleDto.setName(role.getName());
                    return roleDto;
                }).collect(Collectors.toSet());
        userDTO.setRoleDTOs(roleDTOS);
        userDTO.setUsername(newUser.getUsername());
        userDTO.setEmail(newUser.getEmail());
        peopleDTO.setUserDTO(userDTO);
        peopleDTO.setName(people.getName());
        peopleDTO.setPhone(people.getPhone());
        peopleDTO.setAddress(people.getAddress());
        peopleDTO.setBirthday(people.getBirthday());
        //Set MovieShowTime
        MovieShowTime movieShowTime = ticket.getMovieShowTime();

        Movie movie = movieShowTime.getMovie();

        CategoryDTO categoryDTO = CategoryDTO.builder()
                .name(movie.getCategory().getName()).build();

        MovieDTO movieDTO = MovieDTO.builder()
                .name(movie.getName())
                .description(movie.getDescription())
                .rating(movie.getRating())
                .lengthMinute(movie.getLengthMinute())
                .categoryDTO(categoryDTO).build();

        Hall hall = movieShowTime.getHall();
        HallDTO hallDTO = HallDTO.builder()
                .name(hall.getName()).build();

        Set<SeatDTO>  seatDTOS = movieShowTime.getSeats().stream()
                .map(seat -> {
                    SeatDTO seatDTO = new SeatDTO();
                    seatDTO.setName(seat.getName());
                    return seatDTO;
                }).collect(Collectors.toSet());

        MovieShowTimeDTO movieShowTimeDTO = MovieShowTimeDTO.builder()
                .date(movieShowTime.getDate())
                .movieDTO(movieDTO)
                .hallDTO(hallDTO)
                .time(movieShowTime.getTime())
                .seatDTOS(seatDTOS).build();


        return TicketDTO.builder()
                .paymentMethod(ticket.getPaymentMethod())
                .id(ticket.getId())
                .price(ticket.getPrice())
                .totalMoney(ticket.getTotalMoney())
                .quantity(ticket.getQuantity())
                .movieShowTimeDTO(movieShowTimeDTO).build();
    }

    public List<TicketDTO> castListEntityToDTO(List<Ticket> tickets) {
        List<TicketDTO> ticketDTOS = new ArrayList<>();
        for(Ticket ticket : tickets) {
            TicketDTO ticketDTO = castEntityToDTO(ticket);
            ticketDTOS.add(ticketDTO);
        }
        return ticketDTOS;
    }

    public Ticket castDTOToEntity(TicketDTO ticketDTO) {
        Ticket ticket = new Ticket();
        String date = DateTimeUtils.fromDateToString(ticketDTO.getMovieShowTimeDTO().getDate());
        String movieName = ticketDTO.getMovieShowTimeDTO().getMovieDTO().getName();
        String hallName =  ticketDTO.getMovieShowTimeDTO().getHallDTO().getName();
        Time time = ticketDTO.getMovieShowTimeDTO().getTime();
        MovieShowTime movieShowTime = movieShowTimeRepository
                .findForTicket(date,
                        movieName,
                        hallName,
                        time).orElseThrow(() -> new NoSuchElementException("NOT FOUND"));
        movieShowTime.setTime(time);
        movieShowTime.setDate(ticketDTO.getMovieShowTimeDTO().getDate());
        Movie movie = movieRepository.findByName(movieName).orElseThrow();
        movieShowTime.setMovie(movie);
        Hall hall = hallRepository.findByName(hallName).orElseThrow();
        movieShowTime.setHall(hall);
        ticket.setMovieShowTime(movieShowTime);
        ticket.setPaymentMethod(ticketDTO.getPaymentMethod());
        ticket.setPrice(ticketDTO.getPrice());
        ticket.setQuantity(ticketDTO.getQuantity());
        ticket.setTotalMoney(ticketDTO.getPrice()*ticketDTO.getQuantity());
        PeopleDTO peopleDTO = ticketDTO.getPeopleDTO();
        People people = peopleRepository.findByEmail(peopleDTO.getUserDTO().getEmail()).orElseThrow();
        people.setBirthday(peopleDTO.getBirthday());
        people.setName(peopleDTO.getName());
        people.setAddress(peopleDTO.getAddress());
        people.setName(peopleDTO.getName());
        people.setTicketSet(null);
        people.setPhone(peopleDTO.getPhone());
        ticket.setPeople(people);
        return ticket;
    }
}
