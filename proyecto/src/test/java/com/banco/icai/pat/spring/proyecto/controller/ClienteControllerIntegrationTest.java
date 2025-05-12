package com.banco.icai.pat.spring.proyecto.controller;


import com.banco.icai.pat.spring.proyecto.model.ClientResponse;
import com.banco.icai.pat.spring.proyecto.model.RegisterRequest;
import com.banco.icai.pat.spring.proyecto.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(ClienteController.class)
public class ClienteControllerIntegrationTest {

    private final String NOMBRE  ="Anton";
    private final String EMAIL  ="pepelarana@yahho.es";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Test
    public void registroClienteOK() throws Exception{

    }
}
