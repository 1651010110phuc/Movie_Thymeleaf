package fa.training.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO implements Serializable {
    private Long id;
    private String name;
    private double rating;
    private String image;
    private String description;
    private int lengthMinute;
    private short status;
    private String videoURL;
    private CategoryDTO categoryDTO;

}
