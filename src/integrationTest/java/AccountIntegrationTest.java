import com.sample.AccountApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


/**
 * Account integration test
 *
 * We have develop three test to check our microservices
 * <ul>
 *     <li>findAll</li>
 *     <li>SaveAccount</li>
 *     <li>GetAccountById</li>
 *     <li>failWithWrongAccount</li>
 * </ul>
 *
 * @author Noel Rodriguez
 */


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class,AccountApplication.class})
@ActiveProfiles("test")
public class AccountIntegrationTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;


    @Before
    public void setUpMockMvc() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void findAll() throws Exception {

        mockMvc.perform(post("/accounts")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"alias\" : \"pepe\",\"accountId\" : \"1\"}"))
                .andExpect(status().is2xxSuccessful());


        MvcResult result = mockMvc.perform(get("/accounts")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString().contains("pepe"),is(true));
    }

    @Test
    public void saveAccountInTheDatabase() throws Exception {
        int rowNumber = countRowsInTable("account");

        mockMvc.perform(post("/accounts")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"alias\" : \"pepe\",\"accountId\" : \"1\"}"))
                .andExpect(status().is2xxSuccessful());

        assertThat(countRowsInTable("account"), is(rowNumber + 1));
    }



    @Test
    @ExceptionHandler(RuntimeException.class)
    public void failWithWrongAccount() throws Exception {
        int rowNumber = countRowsInTable("account");

        mockMvc.perform(post("/accounts")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());

        assertThat(countRowsInTable("account"), is(rowNumber));

    }


    @Test
    public void getAccountById() throws Exception {

        mockMvc.perform(post("/accounts")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"alias\" : \"pepe\",\"accountId\" : \"1\"}"))
                .andExpect(status().is2xxSuccessful());


        mockMvc.perform(get("/accounts/{accountId}", "1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.alias", is("pepe")));
    }


}
