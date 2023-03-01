package fa.training.dto;

import fa.training.entity.login.ERole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    @NotEmpty
    @Length(min = 6)
    private String username;
    @Length(min = 6)
    @NotEmpty
    private String password;
    private LocalDate createDate;

    private Boolean isEdit=false;
    private Boolean isActive;
    private String role;
}
