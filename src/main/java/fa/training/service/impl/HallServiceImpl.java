package fa.training.service.impl;

import fa.training.dto.HallDTO;
import fa.training.entity.Hall;
import fa.training.mapper.HallMapper;
import fa.training.repository.HallRepository;
import fa.training.service.HallService;
import org.springframework.stereotype.Service;

@Service
public class HallServiceImpl implements HallService {
    private final HallRepository hallRepository;

    private final HallMapper hallMapper;

    public HallServiceImpl(HallRepository hallRepository, HallMapper hallMapper) {
        this.hallRepository = hallRepository;
        this.hallMapper = hallMapper;
    }

    @Override
    public String editTheaterHall(HallDTO hallDTO) {
        Hall hall = hallMapper.castDTOToEntity(hallDTO);
        hallRepository.saveAndFlush(hall);
        return "Edit Hall complete";
    }
    @Override
    public HallDTO findByName(String name) {
        Hall hall = hallRepository.findByName(name).orElseThrow();
        return hallMapper.castEntityToDTO(hall);
    }


}
