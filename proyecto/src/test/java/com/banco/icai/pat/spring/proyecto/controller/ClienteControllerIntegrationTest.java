package com.banco.icai.pat.spring.proyecto.controller;


import com.banco.icai.pat.spring.proyecto.entity.Cuenta;
import com.banco.icai.pat.spring.proyecto.model.ClientResponse;
import com.banco.icai.pat.spring.proyecto.model.RegisterRequest;
import com.banco.icai.pat.spring.proyecto.service.ClienteService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(ClienteController.class)
public class ClienteControllerIntegrationTest {

    private final String NOMBRE  ="Anton";
    private final String DNI  ="12345678A";
    private final String APELLIDO  ="Cobian";
    private final String EMAIL  ="pepelarana@yahho.es";
    private final String PASSWORD  ="Aventura9";
    private final String TELEFONO  ="666666666";
    private final List<Cuenta> CUENTAS = new ArrayList<>();


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Test
    void registerOk() throws Exception {
        // Given ...
        Mockito.when(clienteService.profile(Mockito.any(RegisterRequest.class)))
                .thenReturn(new ClientResponse(EMAIL, NOMBRE, CUENTAS));
        String request = "{" +
                "\"dni\":\"" + DNI + "\"," +
                "\"nombre\":\"" + NOMBRE + "\"," +
                "\"apellido\":\"" + APELLIDO + "\"," +
                "\"email\":\"" + EMAIL + "\"," +
                "\"telefono\":\"" + TELEFONO + "\"," +
                "\"password\":\"" + PASSWORD + "\"}";
        // When ...
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/royale")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                // Then ...
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("{" +
                        "\"email\":\"" + EMAIL + "\"," +
                        "\"nombre\":\"" + NOMBRE + "\"," +
                        "\"cuentas\":" + CUENTAS + "}"));
    }

}
