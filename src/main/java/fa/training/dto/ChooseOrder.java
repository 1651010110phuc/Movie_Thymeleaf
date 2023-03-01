package fa.training.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChooseOrder  {
    @NotNull
    private TicketDTO ticketDTO;

    @NotNull
    private List<String> numberSeat;

//    @ConstructorProperties({"ticketDTO","numberSeat"})
//    ChooseOrder(TicketDTO ticketDTO, List<String> numberSeat){
//        this.ticketDTO = ticketDTO;
//        this.numberSeat = numberSeat;
//    }
//
//    public static ChooseOrder.ChooseOrderBuilder builder() {
//        return new ChooseOrder.ChooseOrderBuilder();
//    }
//
//    public static class ChooseOrderBuilder {
//        private TicketDTO ticketDTO;
//        private List<String> numberSeat;
//        ChooseOrderBuilder(){
//        }
//
//        public ChooseOrder.ChooseOrderBuilder ticketDTO(TicketDTO ticketDTO) {
//            this.ticketDTO = ticketDTO;
//            return this;
//        }
//        public ChooseOrder.ChooseOrderBuilder numberSeat(List<String> numberSeat) {
//            this.numberSeat = numberSeat;
//            return this;
//        }
//
//        public ChooseOrder build() {
//            return new ChooseOrder(this.ticketDTO, this.numberSeat);
//        }
//
//
//    }
//
//

}
