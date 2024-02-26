package com.example.concesionario.controlador;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
 
import com.example.concesionario.repositorio.CocheRepositorio;
import org.springframework.http.MediaType;  

@WebMvcTest(CocheControlador.class)
public class CocheControladorTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CocheRepositorio cocheRepositorio;

    @InjectMocks
    private CocheControlador cocheControlador;

    @Test
    public void testObtenerTodosLosCoches() throws Exception {
        when(cocheRepositorio.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/coches")
                .contentType(MediaType.APPLICATION_JSON)) 
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}
