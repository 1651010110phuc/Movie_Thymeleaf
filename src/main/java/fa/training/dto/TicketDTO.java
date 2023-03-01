package fa.training.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {

    private long id;
    @NotNull
    private String paymentMethod;
    @NotNull
    @Positive
    private double price;
    @NotNull
    @Positive
    private int quantity;
    @Positive
    private double totalMoney;
    @NotNull
    private PeopleDTO peopleDTO;
    @NotNull
    private MovieShowTimeDTO movieShowTimeDTO;
}
