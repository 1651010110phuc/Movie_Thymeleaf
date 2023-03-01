package fa.training.repository;

import fa.training.entity.People;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<People, Long> {
    @Query(value = "select p.* " +
            "from cinema.people p INNER JOIN cinema.users s " +
            "on p.user_id = s.id " +
            "where s.email = :email ", nativeQuery = true)
    Optional<People> findByEmail(String email);
    @Query(value = "select p.* " +
            "from cinema.people p INNER JOIN cinema.users s " +
            "on p.user_id = s.id INNER JOIN cinema.user_roles ur " +
            "on s.id = ur.user_id INNER JOIN cinema.role r " +
            "on ur.role_id = r.id "+
            "where r.name= :roleName", nativeQuery = true)
    List<People> findByRole(String roleName);
    @Query(value = "select p.* " +
            "from cinema.people p INNER JOIN cinema.users s " +
            "on p.user_id = s.id INNER JOIN cinema.user_roles ur " +
            "on s.id = ur.user_id INNER JOIN cinema.role r " +
            "on ur.role_id = r.id "+
            "where r.name= :roleName and p.birthday = :birthday " , nativeQuery = true)
    List<People> findByRoleAndBirthday(String roleName, String birthday);



}
