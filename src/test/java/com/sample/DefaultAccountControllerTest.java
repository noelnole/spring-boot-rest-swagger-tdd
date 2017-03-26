package com.sample;

import com.sample.controller.AccountController;
import com.sample.model.Account;
import com.sample.service.AccountService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


/**
 * This class has been develop with tdd.
 *
 * The goal of this class is go from the test to the controller, developing the necesary steps to reach all process of
 * our controller
 *
 * @author Noel Rodriguez
 */


public class DefaultAccountControllerTest {

    @InjectMocks
    private AccountController accountController;

    @Mock
    private AccountService accountService;


    private MockMvc mockMvc;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();


    @Before
    public void setUpMockMvc() {
        mockMvc = standaloneSetup(accountController).build();
    }



    @Test
    public void findAllAccounts() throws Exception {

        Account account = new Account();
        account.setAlias("pepe");
        account.setAccountId("747940o");

        List accounts = new ArrayList();
        accounts.add(account);

        Optional<List<Account>> accountsOptional = Optional.of(accounts);
        when(accountService.findAll()).thenReturn(accountsOptional);

       mockMvc.perform(get("/accounts")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void creatingErrorAccount() throws Exception {
        Account account = when(mock(Account.class).getId())
                .thenReturn(1L)
                .getMock();

        when(accountService.save(isA(Account.class)))
                .thenReturn(account);

        mockMvc.perform(post("/accounts")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void creatingAccount() throws Exception {
        mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"alias\" : \"pepe\",\"accountId\" : \"1\"}"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void findAccountId() throws Exception {

        Account account = new Account();
        when(accountService.findByAccountId("1"))
                .thenReturn(Optional.of(account));

        mockMvc.perform(get("/accounts/{accountId}", "1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


    }


    @Test
    public void notFoundAccountId() throws Exception {
        mockMvc.perform(get("/accounts/-1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


}
