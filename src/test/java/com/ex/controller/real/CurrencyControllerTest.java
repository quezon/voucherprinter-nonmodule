package com.ex.controller.real;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Currency;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

/*
 * This test executes this SpringBootApplication with a given random port
 * 
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CurrencyControllerTest {
	
	@LocalServerPort
    private int port;
	
	@Autowired
    private TestRestTemplate restTemplate;

	@Test
	void currencyControllerReturnsSet() {
        Set<Currency> task = (Set<Currency>) restTemplate.getForObject("http://localhost:" + port + "/curr/all", Set.class);
        assertEquals(228,task.size());
	}

}
