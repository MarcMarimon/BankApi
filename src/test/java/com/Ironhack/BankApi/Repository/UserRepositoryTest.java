package com.Ironhack.BankApi.Repository;

import com.Ironhack.BankApi.LocalDateDeserializer;
import com.Ironhack.BankApi.LocalDateSerializer;
import com.Ironhack.BankApi.Models.*;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    User user;
    GsonBuilder gsonBuilder=new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
            .registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
    Gson gson = gsonBuilder.setPrettyPrinting().create();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objectMapper.findAndRegisterModules();

    }
    //Estos dos tambien entran en el bucle de llamadas a metodos por el Gson a pesar de que el third Party no tiene LocalDate y no da error ahi pero entra en bucle con los mismos metodos
    @Test
    void add_third_party() throws Exception {
        ThirdParty thirdParty = new ThirdParty("Pepe Gomez",passwordEncoder.encode("1234"),"123abc" );
        String body = gson.toJson(thirdParty);
        MvcResult mvcResult = mockMvc.perform(post("/User/add-third-party").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Pepe Gomez"));
    }

    @Test
    void add_account_holder() throws Exception{
        AccountHolder accountHolder = new AccountHolder("Maria Antonia", passwordEncoder.encode("1234"), LocalDate.of(1997, 12, 3), new Address("Spain", "Barcelona", "Central Street"), null);
        String body = gson.toJson(accountHolder);
        MvcResult mvcResult = mockMvc.perform(post("/User/create-account-holder").content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Maria Antonia"));
    }
}
