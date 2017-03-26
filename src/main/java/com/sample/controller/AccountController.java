package com.sample.controller;

import com.sample.exception.AccountNotFoundException;
import com.sample.model.Account;
import com.sample.service.AccountService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Controller tier, this class is in charge on manage the request and respond of the
 * account microservice
 *
 * @author Noel Rodriguez
 */

@RestController
@RequestMapping(value = "/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;


    /**
     * This method return all accounts in the system
     *
     * @return ResponseEntity<List<Account>>
     */
    @ApiOperation(value = "Find an account by AccountId", nickname = "FindAccount")
    @GetMapping()
    public ResponseEntity<List<Account>> getAccounts(){
        return ResponseEntity.ok(accountService.findAll().get());
    }


    /**
     * This method create a new account
     *
     * @param account new account
     * @return ResponseEntity<Account>
     */
    @ApiOperation(value = "Add New Account in the System", nickname = "addANewAccount")
    @PostMapping()
    public ResponseEntity<Account> newAccount(@RequestBody @Valid @ApiParam(value = "NewAccount") Account account){
         Account newAccount = accountService.save(account);

         return ResponseEntity.ok(newAccount);
    }

    /**
     * This method return an account by Id
     * @param accountId
     * @return ResponseEntity<Account>
     * @throws NotFoundException if the accountId doesn't exist
     */
    @ApiOperation(value = "Find an account by AccountId", nickname = "FindAccount")
    @GetMapping(value="/{accountId}")
    public ResponseEntity<Account> findAccountById(@PathVariable @ApiParam(value = "Account Id to search") String accountId) throws NotFoundException {
        Optional<Account> account = accountService.findByAccountId(accountId);
        if (account!= null && account.isPresent()){
            return  ResponseEntity.ok(account.get());
        }
        throw new AccountNotFoundException(accountId);

    }

}
