package com.sample.service.impl;

import com.sample.model.Account;
import com.sample.repository.AccountRepository;
import com.sample.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * This class implement  accountServic
 *
 * @author Noel Rodriguez
 */

@Service
public class DefaultAccountService implements AccountService {

    private static AccountRepository accountRepository;

    @Autowired
    public DefaultAccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    /* (non-Javadoc)
     *
     * FindAll.
     *
     * @see com.sample.service#findAll
   */
    @Override
    public Optional<List<Account>> findAll(){

        List<Account> accounts = accountRepository.findAll();
        if (accounts != null)
            return Optional.of(accounts);
        else
            return Optional.empty();
    }

    /* (non-Javadoc)
      *
      * FindById.
      *
      * @see com.sample.service#findById
    */
    @Override
    public Optional<Account> findById(Long id) {

        Account account = accountRepository.findOne(id);
        if (account == null) {
            return Optional.empty();

        }
        return Optional.of(account);
    }

    /* (non-Javadoc)
      *
      * FindByAlias
      *
      * @see com.sample.service#findByAlias(java.lang.String)
      */
    @Override
    public Optional<Account> findByAlias(String alias) {
        Account account = accountRepository.findByAlias(alias);
        if (account != null) {
            return Optional.of(account);
        } else
            return Optional.empty();
    }


    /* (non-Javadoc)
      *
      * FindByAccountId
      *
      * @see com.sample.service#findByAccountId(java.lang.String)
      */
    @Override
    public Optional<Account> findByAccountId(String accountId) {
        Account account = accountRepository.findByAccountId(accountId);
        if (account != null) {
            return Optional.of(account);
        } else
            return Optional.empty();
    }

    /* (non-Javadoc)
      *
      * Standard class loader method to load a class and resolve it.
      *
      * @see com.sample.service#save(com.sample.Model.Account)
      */
    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }
}
