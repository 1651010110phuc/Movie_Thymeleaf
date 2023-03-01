package fa.training.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@Entity
@Table
@Data
public class Ticket implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotNull
    private String paymentMethod;
    @Column
    @NotNull
    @Positive
    private double price;
    @Column
    @NotNull
    @Positive
    private int quantity;
    @Column
    @NotNull
    @Positive
    private double totalMoney;
    @ManyToOne
    @JoinColumn(name = "peopleId")
    private People people;
    @ManyToOne
    @JoinColumn(name = "movieShowTimeId")
    private MovieShowTime movieShowTime;

}
