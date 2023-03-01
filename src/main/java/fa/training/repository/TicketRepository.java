package fa.training.repository;

import fa.training.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query(value = "select t.* " +
            "from cinema.ticket t inner join cinema.people p " +
            "on t.people_id = p.id  inner join  cinema.users u " +
            "on p.user_id = u.id " +
            "where  u.email = :email ", nativeQuery = true)
    List<Ticket> findTicketByEmail(String email);


   // @Query(value = "select t.* from cinema.ticket t ", nativeQuery = true)
    List<Ticket> findAll();

}
