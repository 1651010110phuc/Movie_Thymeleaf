package fa.training.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Builder
@Getter
public class TimeDTO {
    @NotNull
    @Size(max = 5, min = 5, message = "Time Type: hh:mm")
    private String time;
}
