package com.banco.icai.pat.spring.proyecto;

import com.banco.icai.pat.spring.proyecto.entity.Cuenta;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class ProyectoApplicationTests {

	private final String NOMBRE  ="Anton";
	private final String DNI  ="12345678A";
	private final String APELLIDO  ="Cobian";
	private final String EMAIL  ="pepelarana@yahoo.es";
	private final String PASSWORD  ="Aventura9";
	private final String TELEFONO  ="666666666";
	private final List<Cuenta> CUENTAS = new ArrayList<>();

	@Autowired
	private TestRestTemplate client;
	@BeforeEach
	public void antesTest(){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String registro = "{" +
				"\"dni\":\"" + DNI + "\"," +
				"\"nombre\":\"" + NOMBRE + "\"," +
				"\"apellido\":\"" + APELLIDO + "\"," +
				"\"email\":\"" + EMAIL + "\"," +
				"\"telefono\":\"" + TELEFONO + "\"," +
				"\"password\":\"" + PASSWORD + "\"}";
		ResponseEntity<String> response = client.exchange(
				"http://localhost:8080/api/royale",
				HttpMethod.POST, new HttpEntity<>(registro, headers), String.class);
	}
	@Test
	public void registroDuplicado() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String registro = "{" +
				"\"dni\":\"" + DNI + "\"," +
				"\"nombre\":\"" + NOMBRE + "\"," +
				"\"apellido\":\"" + APELLIDO + "\"," +
				"\"email\":\"" + EMAIL + "\"," +
				"\"telefono\":\"" + TELEFONO + "\"," +
				"\"password\":\"" + PASSWORD + "\"}";
		ResponseEntity<String> response = client.exchange(
				"http://localhost:8080/api/royale",
				HttpMethod.POST, new HttpEntity<>(registro, headers), String.class);
		assertEquals(response.getStatusCode(),HttpStatus.CONFLICT);
	}
	@Test
	public void	loginOK(){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		//Ahora hacer el login y comprobar que este bien
		String login = "{" +
				"\"email\":\"" + EMAIL + "\"," +
				"\"password\":\"" + PASSWORD + "\"}";

		ResponseEntity<String> loginResponse = client.exchange(
				"http://localhost:8080/api/royale/users",
				HttpMethod.POST, new HttpEntity<>(login, headers), String.class);

		Assertions.assertEquals(HttpStatus.CREATED, loginResponse.getStatusCode());


		// Verificar que devuelve la cookie
		String sessionCookie = loginResponse.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
		Assertions.assertNotNull(sessionCookie, "La cookie de sesión debe estar presente.");

		// Verificar si contiene el id
		Assertions.assertTrue(sessionCookie.contains("session="), "La cookie debe contener 'session='");
	}
}
