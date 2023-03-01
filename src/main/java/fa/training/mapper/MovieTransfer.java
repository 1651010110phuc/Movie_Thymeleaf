package fa.training.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MovieTransfer implements Serializable {
    private String name;
    private String description;
    private String lengthMinute;
    private String rating;
    private String categoryDTO;
}
