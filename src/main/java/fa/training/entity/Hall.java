package fa.training.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table
@Data
public class Hall implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    @NotNull
    private String name;

    @OneToMany(mappedBy = "hall", cascade = CascadeType.ALL)
    @Transient
    @JsonIgnore
    private Set<MovieShowTime> movieShowTimes;



}
