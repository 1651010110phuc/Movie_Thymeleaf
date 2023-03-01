package fa.training.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ShowTimeSeatRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void setSeatOrder(long movieShowTimeId, String seatName) {
        entityManager.createNativeQuery("insert into cinema.show_time_seat(movie_show_time_id, seat_id) " +
                        "select mst.id,s.id " +
                        "from cinema.movie_show_time mst,cinema.seat s " +
                        "where mst.id = ? and s.name = ? ")
                .setParameter(1, movieShowTimeId)
                .setParameter(2, seatName)
                .executeUpdate();
    }
}
