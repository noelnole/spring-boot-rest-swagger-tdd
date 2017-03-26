package com.sample;

import com.sample.model.Account;
import com.sample.repository.AccountRepository;
import com.sample.service.impl.DefaultAccountService;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This class has been develop with tdd.
 *
 * The goal of this class is go from the test to the service, developing the necesary steps to reach all process of
 * our service
 *
 * @author Noel Rodriguez
 */
public class DefaultAccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private DefaultAccountService accountService;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Captor
    private ArgumentCaptor<Account> accountArgumentCaptor;


    @Test
    public void findAll() {

        Account account = new Account();
        account.setAlias("pepe");
        account.setAccountId("747940o");

        List accounts = new ArrayList();
        accounts.add(account);

        when(accountRepository.findAll()).thenReturn(accounts);

        List<Account> accountsResult = accountService.findAll().get();
        assertThat(accounts.size(), is(accountsResult.size()));
    }

    @Test
    public void findByAccountId(){
        Account account = when(mock(Account.class).getId())
                .thenReturn(2L)
                .getMock();
        when(account.getAlias())
                .thenReturn("TDD");
        when(accountRepository.findOne(2L))
                .thenReturn(account);

        Optional<Account> optionalAccount = accountService.findById(2L);

        Account accountDefault = optionalAccount.get();
        assertThat(account.getId(), is(accountDefault.getId()));
        assertThat(account.getAlias(), is(accountDefault.getAlias()));
    }


    @Test
    public void findByAccountAlias(){
        Account account = when(mock(Account.class).getAlias())
                .thenReturn("pepe")
                .getMock();
        when(account.getId())
                .thenReturn(1L);
        when(accountRepository.findByAlias("pepe"))
                .thenReturn(account);

        Optional<Account> optionalAccount = accountService.findByAlias("pepe");

        Account accountDefault = optionalAccount.get();
        assertThat(account.getId(), is(accountDefault.getId()));
        assertThat(account.getAlias(), is(accountDefault.getAlias()));

    }

    @Test
    public void saveNewAccount(){
        Account account = new Account();
        account.setAlias("New Alias");

        Account newAccount = when(mock(Account.class).getId())
                .thenReturn(1L)
                .getMock();
        when(newAccount.getAlias())
                .thenReturn("New Alias");
        when(accountRepository.save(accountArgumentCaptor.capture()))
                .thenReturn(newAccount);

        Account defaultAccount = accountService.save(account);

        assertThat(accountArgumentCaptor.getValue().getAlias(), is(account.getAlias()));
        assertThat(defaultAccount.getId(), is(newAccount.getId()));
        assertThat(defaultAccount.getAlias(), is(newAccount.getAlias()));
    }


    @Test
    public void returnNothingIsNotExist(){
        when(accountRepository.findOne(1L))
                .thenReturn(null);
        Optional<Account> account = accountService.findById(1L);

        assertThat(account.isPresent(),is(false));
    }
}
