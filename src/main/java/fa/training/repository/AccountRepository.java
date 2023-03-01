package fa.training.repository;

import fa.training.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,String> {
    Optional<Account> findByUsername(String s);
    @Query(value = "SELECT a.*,r.name " +
            "FROM accounts a inner join account_roles b " +
            "on a.username = b.account inner join role r " +
            "on b.role_id = r.id " +
            "where r.name = :role ; ",nativeQuery = true)
    List<Account> findByRole(String role);
}
