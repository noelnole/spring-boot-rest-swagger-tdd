package com.sample.repository;

import com.sample.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This layer is in charge on connect with DB
 * @author Noel Rodriguez
 */

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * This method return an accounb by Alias
     * @param alias to search
     * @return Account
     */
    Account findByAlias(String alias);

    /**
     * This method return an account object
     * @param accountId to search
     * @return Account
     */
    Account findByAccountId(String accountId);
}
