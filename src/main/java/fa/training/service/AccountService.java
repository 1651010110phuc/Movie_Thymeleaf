package fa.training.service;

import fa.training.entity.Account;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface AccountService {
    Account login(String username, String password);

    List<Account> findAllUsers();

    List<Account> findAllAdmins();

    List<Account> findAll();
    List<Account> findAll(Sort sort);

    List<Account> findAllById(Iterable<String> strings);

    <S extends Account> List<S> saveAll(Iterable<S> entities);

    <S extends Account> List<S> saveAllAndFlush(Iterable<S> entities);

    @Deprecated
    void deleteInBatch(Iterable<Account> entities);

    void deleteAllInBatch(Iterable<Account> entities);

    void deleteAllByIdInBatch(Iterable<String> strings);

    void deleteAllInBatch();

    @Deprecated
    Account getOne(String s);

    @Deprecated
    Account getById(String s);

    Account getReferenceById(String s);

    <S extends Account> List<S> findAll(Example<S> example);

    <S extends Account> List<S> findAll(Example<S> example, Sort sort);

    Page<Account> findAll(Pageable pageable);

    <S extends Account> S save(S entity);

    Optional<Account> findById(String s);

    boolean existsById(String s);

    long count();

    void deleteById(String s);

    void delete(Account entity);

    void deleteAllById(Iterable<? extends String> strings);

    void deleteAll(Iterable<? extends Account> entities);

    void deleteAll();

    <S extends Account> Optional<S> findOne(Example<S> example);

    <S extends Account> Page<S> findAll(Example<S> example, Pageable pageable);

    <S extends Account> long count(Example<S> example);

    <S extends Account> boolean exists(Example<S> example);

    <S extends Account, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction);

    Optional<Account> findByUsername(String username);


    void deleteByUsername(Account account);
}
