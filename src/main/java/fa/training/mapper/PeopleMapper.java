package fa.training.mapper;

import fa.training.dto.PeopleDTO;
import fa.training.dto.RoleDTO;
import fa.training.dto.UserDTO;
import fa.training.entity.People;
import fa.training.entity.login.User;
import fa.training.repository.PeopleRepository;
import fa.training.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PeopleMapper {

    private final UserRepository userRepository;
    private final PeopleRepository peopleRepository;

    public PeopleMapper(UserRepository userRepository, PeopleRepository peopleRepository) {
        this.userRepository = userRepository;
        this.peopleRepository = peopleRepository;
    }

    public PeopleDTO castEntityToDTO(People people) {
        PeopleDTO peopleDTO = new PeopleDTO();
        User newUser = userRepository.findByUsername(people.getUser().getUsername()).orElseThrow();
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
        return peopleDTO;
    }

    public List<PeopleDTO> castListEntityToDTO(List<People> people) {
        List<PeopleDTO> peopleDTOS= new ArrayList<>();
        for (People person : people) {
            PeopleDTO personDTO = castEntityToDTO(person);
            peopleDTOS.add(personDTO);
        }
        return peopleDTOS;
    }

    public People castDTOToEntity(PeopleDTO peopleDTO) {
        People person = new People();
        if(peopleRepository.findByEmail(peopleDTO.getUserDTO().getEmail()).isPresent()) {
            person = peopleRepository.findByEmail(peopleDTO.getUserDTO().getEmail()).orElseThrow();
        }
        person.setName(peopleDTO.getName());
        person.setPhone(peopleDTO.getPhone());
        person.setAddress(peopleDTO.getAddress());
        User user = userRepository.findByUsername(peopleDTO.getUserDTO().getUsername()).orElseThrow();
        person.setUser(user);
        return person;
    }
}
