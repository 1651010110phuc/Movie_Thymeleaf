package fa.training.service;


import fa.training.dto.HallDTO;

public interface HallService {
    String editTheaterHall(HallDTO hallDTO);


    HallDTO findByName(String name);

}
