package com.Ironhack.BankApi.Repository;

import com.Ironhack.BankApi.Controllers.AccountController;
import com.Ironhack.BankApi.LocalDateDeserializer;
import com.Ironhack.BankApi.LocalDateSerializer;
import com.Ironhack.BankApi.Models.*;
import com.Ironhack.BankApi.MyTypeAdapter;
import com.Ironhack.BankApi.Repositories.AccountHolderRepository;
import com.Ironhack.BankApi.Repositories.AccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class AccountRepositoryTest {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AccountHolderRepository accountHolderRepository;
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    Account account;
    GsonBuilder gsonBuilder=new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
            .registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
    Gson gson = gsonBuilder.registerTypeAdapter(User.class, new MyTypeAdapter<User>()).registerTypeAdapter(Account.class, new MyTypeAdapter<Account>()).setPrettyPrinting().create();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objectMapper.findAndRegisterModules();


    }
    /*Los tres primeros tests petan por el bucle de llamadas a metodos del gson que provoca un StackOverflow, buscando por internet he visto que la solucion es aumentar el tamaño del stack
     *en segun que errores o revisar los oneToMany que no provoquen un bucle infinito, lo he estado mirando pero no consigo arreglarlo */
    /* EL problema veo que esta al convertir checking a una "String body" con el "gson.toJson", si hago el test con un ".content(String.valueof(checking) no entra en bucle pero
     *me lanza un error 415 unnsuported MediaType */
    /* EL problema esta todo el rato en la creacion de el objeto a un gson, he intentado probar otras versiones de la dependency de gson bajando hasta la 2.8.9 que he visto que es la
    * mas usada pero no he conseguido que no entre en bucle,en internet he encontrado la solucion de poner private trascient
    * a las autoreferencias en las clases para que gson las ignore pero no tengo ninguna por lo que he estado viendo */
    /* He visto que podria ser un problema del TypeAdapter y que se podria solventar haciendo una clase MyTypeAdapter y añadiendo
    * el .registerMyTypeAdapter(class.class,new MyTypeAdapter(class) en el registro del gsonBuilder, ahora falta que los tests
    * me den el created y no un bad request*/
    /*El bad request es porque las cuentas que estoy creando en cada test no se estan guardando en el AccountHolderRepository y no puedo llamarlas
    * con su id como request param de la http request al crear la cuenta, y si llamo al accountHolderRepository.findById().get() de el account holder que cree en la BankApiApplication  con el comandLineRuner
     * el test me da error porque no he iniciado sesion con un usuario, he estado investigando como falsear un inicio de sesion en los tests,
     * y he encontrado algo usuando Mockito pero no lo acavo de entender, tengo  dudas sobre como mockear un login de usuario y porque no me lo pide con la creacion de usuarios thirparty y account holders*/
    @Test
    void add_checking() throws Exception {
        AccountHolder accountHolder = new AccountHolder("Juan Lopez", passwordEncoder.encode("1234"), LocalDate.of(1995, 6, 23), new Address("Spain", "Barcelona", "Central Street"), null);
        Checking checking = new Checking(BigDecimal.valueOf(1500),accountHolderRepository.findById(Long.valueOf(2)).get(), null, "1234abc");
        String body = gson.toJson(checking);
        MvcResult mvcResult = mockMvc.perform(post("/Account/create-checking-account?accountHolderId=2").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Juan Lopez"));
    }

    @Test
    void add_savings() throws Exception {
        AccountHolder accountHolder2 = new AccountHolder("Jose Perez", passwordEncoder.encode("1234"), LocalDate.of(1980, 5, 22), new Address("Spain", "Barcelona", "Downhill Street"), null);
        Savings savings = new Savings(BigDecimal.valueOf(1500), accountHolder2, null, "1234abc", BigDecimal.valueOf(500), BigDecimal.valueOf(0.4));
        String body = gson.toJson(savings);
        MvcResult mvcResult = mockMvc.perform(post("/Account/create-saving-account?accountHolderId=1").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Jose Perez"));
    }

    @Test
    void add_credit_card() throws Exception {
        AccountHolder accountHolder3 = new AccountHolder("Antonio Mendoza", passwordEncoder.encode("1234"), LocalDate.of(1975, 10, 5), new Address("Spain", "Barcelona", "La Rambla"), null);
        CreditCard creditCard = new CreditCard(BigDecimal.valueOf(1500), accountHolder3, null, BigDecimal.valueOf(500), BigDecimal.valueOf(0.1));
        String body = gson.toJson(creditCard);
        MvcResult mvcResult = mockMvc.perform(post("/Account/create-credit-card?accountHolderId=1").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Jose Perez"));
    }
    //Este entiendo que hasta que no funcionen los de crear cuentas no podra eliminar ninguna porque la throw exception si la lanza
    @Test
    void delete_account_test() throws Exception {

        MvcResult mvcResult1 = mockMvc.perform(delete("/Account/delete-account/1"))
                .andExpect(status().isOk()).andReturn();
        MvcResult mvcResult2 = mockMvc.perform(delete("/Account/delete-account/1"))
                .andExpect(status().isNotFound()).andReturn();

        assertTrue(mvcResult2.getResolvedException().getMessage().contains("The Account don't exist"));
    }
//El resto de test que son algo mas complejos he de mirar como sacarlos una vez consiga arreglar lo que me hace petar los test
}
