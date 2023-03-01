package fa.training.service;


import fa.training.dto.ChooseOrder;
import fa.training.dto.TicketDTO;

import java.util.List;

public interface TicketService {

    String addTicket(ChooseOrder order);


    List<TicketDTO> findAll();

    List<TicketDTO> findByEmail(String email);


}
