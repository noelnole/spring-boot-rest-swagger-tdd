package com.sample.service;

import com.sample.model.Account;

import java.util.List;
import java.util.Optional;

/**
 * Interface of AccountService, this interface define the necessary methods
 * to work with accounts
 *
 * @author Noel Rodriguez
 */
public interface AccountService {

    /**
     * Return all accounts in the system
     * @return Otional<List<Account>>
     *
     */
    public Optional<List<Account>> findAll();

    /**
     * Find an account by id
     *
     * @param id identificator of account in DB
     * @return Optional<Account>
     */
    Optional<Account> findById(Long id);

    /**
     * Find an account by Alias
     * @param alias alias account
     * @return Optional<Account>
     */
    Optional<Account> findByAlias(String alias);

    /**
     * Find an account by accountId
     * @param accountId accountId of the account
     * @return Optional<Account>
     */
    Optional<Account> findByAccountId(String accountId);

    /**
     * Save a new account
     * @param account the new account
     * @return Account
     */
    Account save(Account account);
}
