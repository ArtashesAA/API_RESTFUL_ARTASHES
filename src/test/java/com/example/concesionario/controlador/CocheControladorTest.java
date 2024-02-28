package com.example.concesionario.controlador;

import com.example.concesionario.ConcesionarioApplication;
import com.example.concesionario.entidad.Coche;
import com.example.concesionario.repositorio.CocheRepositorio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = ConcesionarioApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CocheControladorTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CocheRepositorio cocheRepositorio;

    @Test
    public void pruebaObtenerTodosLosCoches() {
        ResponseEntity<Coche[]> respuesta = restTemplate.getForEntity("http://localhost:" + port + "/coches", Coche[].class);
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
    }

    @Test
    public void pruebaObtenerCochePorId() {
        // Supongamos que hay un coche con ID 1 en la base de datos
        Long idCocheExistente = 1L;
        ResponseEntity<Coche> respuesta = restTemplate.getForEntity("http://localhost:" + port + "/coches/" + idCocheExistente, Coche.class);
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertEquals(idCocheExistente, respuesta.getBody().getId());
    }

    @Test
    public void pruebaCrearCoche() {
    	 Coche nuevoCoche = new Coche(1L, "NuevoCoche", "Modelo", 2022, 150, 50000, 1500, "Gasolina", "Blanco", 25000, "Descripcion");
    	    ResponseEntity<Coche> respuesta = restTemplate.postForEntity("http://localhost:" + port + "/coches", nuevoCoche, Coche.class);
    	    assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());  // Cambiado a CREATED
    	    assertNotNull(respuesta.getBody());
    	    assertEquals(nuevoCoche.getModelo(), respuesta.getBody().getModelo());

    	    // Limpieza: Elimina el coche creado durante la prueba
    	    cocheRepositorio.deleteById(respuesta.getBody().getId());
    }

    @Test
    public void pruebaActualizarCoche() {
        // Supongamos que hay un coche con ID 1 en la base de datos
        Long idCocheExistente = 1L;
        ResponseEntity<Coche> respuestaExistente = restTemplate.getForEntity("http://localhost:" + port + "/coches/" + idCocheExistente, Coche.class);
        assertEquals(HttpStatus.OK, respuestaExistente.getStatusCode());
        assertNotNull(respuestaExistente.getBody());

        // Modifica el coche existente
        Coche cocheModificado = respuestaExistente.getBody();
        cocheModificado.setModelo("CocheModificado");
        HttpEntity<Coche> entidadModificada = new HttpEntity<>(cocheModificado);
        ResponseEntity<Coche> respuestaModificada = restTemplate.exchange(
                "http://localhost:" + port + "/coches/" + idCocheExistente,
                HttpMethod.PUT,
                entidadModificada,
                Coche.class);

        assertEquals(HttpStatus.OK, respuestaModificada.getStatusCode());
        assertNotNull(respuestaModificada.getBody());
        assertEquals(cocheModificado.getModelo(), respuestaModificada.getBody().getModelo());
    }

    @Test
    public void pruebaEliminarCoche() {
        // Supongamos que hay un coche con ID 1 en la base de datos
        Long idCocheExistente = 1L;

        // Intenta eliminar el coche
        ResponseEntity<Void> respuesta = restTemplate.exchange(
                "http://localhost:" + port + "/coches/" + idCocheExistente,
                HttpMethod.DELETE,
                null,
                Void.class);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
    }
}