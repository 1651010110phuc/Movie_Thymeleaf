package fa.training.dto;


import fa.training.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO implements Serializable {

    private Long productId;
    private String name;
    private int quantity;
    private double unitPrice;
    private String image;
    private MultipartFile imageFile;
    private String description;
    private double discount;
    private Date enteredDate;
    private short status;
    private Long categoryId;

}
