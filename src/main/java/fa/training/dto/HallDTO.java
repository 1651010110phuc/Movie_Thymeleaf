package fa.training.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HallDTO {

    @NotNull
    private String name;
//    @ConstructorProperties({"name"})
//    HallDTO(String name){
//        this.name = name;
//    }
//    public static HallDTO.HallDTOBuilder builder() {
//        return new HallDTO.HallDTOBuilder();
//    }
//
//    public static class HallDTOBuilder {
//        private String name;
//
//        HallDTOBuilder() {
//        }
//
//        public HallDTO.HallDTOBuilder name(String name) {
//            this.name = name;
//            return this;
//        }
//
//        public HallDTO build() {
//            return new HallDTO(this.name);
//        }
//    }
}
