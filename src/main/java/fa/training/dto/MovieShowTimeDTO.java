package fa.training.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieShowTimeDTO implements Serializable {

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date date;
    private fa.training.enumerates.Time time;
    @NotNull
    private MovieDTO movieDTO;
    @NotNull
    private HallDTO hallDTO;
    private Set<SeatDTO> seatDTOS;

}
