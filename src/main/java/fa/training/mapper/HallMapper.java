package fa.training.mapper;

import fa.training.dto.HallDTO;
import fa.training.entity.Hall;
import fa.training.repository.HallRepository;
import org.springframework.stereotype.Component;

@Component
public class HallMapper {
    private final HallRepository hallRepository;

    public HallMapper(HallRepository hallRepository) {
        this.hallRepository = hallRepository;
    }

    public HallDTO castEntityToDTO(Hall hall) {
        return HallDTO.builder().name(hall.getName()).build();
    }

    public Hall castDTOToEntity(HallDTO hallDTO) {
        Hall hall = new Hall();
        if(hallRepository.findByName(hall.getName()).isPresent()){
            hall = hallRepository.findByName(hall.getName()).orElseThrow();
        }
        hall.setName(hallDTO.getName());
        return hall;
    }
}
