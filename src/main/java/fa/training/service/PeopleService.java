package fa.training.service;


import fa.training.dto.PeopleDTO;

import java.util.List;

public interface PeopleService {
    String addPeople(PeopleDTO peopleDTO);
    Boolean deletePeople(String peopleEmail);
    String editPeople(PeopleDTO peopleDTO);
    PeopleDTO findByEmail(String peopleEmail);
    List<PeopleDTO> getAdmins();
    List<PeopleDTO> getAllCustomer();
    List<PeopleDTO> getCustomerBirthDay(String birthDay);

}
