package fa.training.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
public class SeatDTO {
    @NotNull
    private String name;
}
