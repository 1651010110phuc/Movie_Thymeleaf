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
public class Category implements Serializable  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long categoryId;

    @Column(name = "name", columnDefinition = "nvarchar(255)",unique = true)
    @NotNull(message = "Empty Category Name")
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @Transient
    @JsonIgnore
    private Set<Movie> movies;


}
