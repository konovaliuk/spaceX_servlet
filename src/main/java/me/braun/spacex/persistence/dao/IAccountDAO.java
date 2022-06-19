package me.braun.spacex.persistence.dao;

import me.braun.spacex.persistence.entity.Account;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public interface IAccountDAO extends DAO<Account, Long>{
    Optional<Account> findByEmail(String email);
    @NotNull List<Account> findAllPagination(int offset, int limit);
    Optional<Account> findByPhone(String phone);
}
