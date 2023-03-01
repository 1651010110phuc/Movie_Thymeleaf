package fa.training.entity;

import fa.training.entity.login.ERole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

import javax.persistence.Entity;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounts" ,uniqueConstraints = @UniqueConstraint(name = "CheckInit", columnNames = { "username","password","createDate","isActive"}))
public class Account implements Serializable {
    @Id
    @Column(length = 30,unique = true)
    private String username;
    @Column(length = 60, nullable = false)
    private String password;
    @Column()
    private LocalDate createDate;
    @Column()
    private Boolean isActive;
    @ManyToMany()
    @JoinTable(	name = "account_roles",
            joinColumns = @JoinColumn(name = "account"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
}
