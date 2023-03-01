package fa.training.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fa.training.entity.login.User;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;


@Entity
@Table
@Data
public class People implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column()
    @NotNull(message = "Empty Name")
    private String name;
    @Column()
    @NotNull(message = "Empty BirthDay")
    private Date birthday;
    @Column()
    @NotNull(message = "Empty Address")
    @Size(min = 6, message = "Address have more 6 characters")
    private String address;
    @Column(unique = true)
    @NotNull(message = "Empty Telephone")
    @NotBlank(message = "Telephone number must not have blank")
    @Size(min = 10, max = 10, message = "Vietnamese Telephone number must have 10 numbers")
    private String phone;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "people", cascade = CascadeType.ALL)
    @Transient
    @JsonIgnore
    private Set<Ticket> ticketSet;

}
