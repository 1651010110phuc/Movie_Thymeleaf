package fa.training.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminLoginDTO {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    private Boolean rememberMe=false;
}
