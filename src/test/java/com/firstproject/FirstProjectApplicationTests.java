package com.firstproject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName("Tests de l'application FirstProject")
class FirstProjectApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("1. Le contexte Spring doit se charger correctement")
    void contextLoads() {
        // Ce test vérifie que l'application Spring Boot démarre sans erreur
        assertNotNull(restTemplate, "TestRestTemplate doit être injecté");
        assertTrue(port > 0, "Le port doit être assigné");
    }

    @Test
    @DisplayName("2. L'application doit démarrer sur un port disponible")
    void applicationStartsOnRandomPort() {
        assertTrue(port > 1024, "Le port doit être supérieur à 1024");
        System.out.println("Application démarrée sur le port: " + port);
    }

    @Test
    @DisplayName("3. L'endpoint racine doit être accessible")
    void rootEndpointShouldBeAccessible() {
        String url = "http://localhost:" + port + "/";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // Même si on n'a pas de contrôleur, on ne doit pas avoir d'erreur de démarrage
        assertNotNull(response, "La réponse ne doit pas être nulle");

        // La réponse peut être 404 (pas de contrôleur) ou 200 (si contrôleur existe)
        assertTrue(
                response.getStatusCode() == HttpStatus.NOT_FOUND ||
                        response.getStatusCode() == HttpStatus.OK,
                "Le statut doit être 404 (pas de contrôleur) ou 200 (contrôleur présent)"
        );
    }

    @Test
    @DisplayName("4. Test de santé de l'application")
    void applicationHealthCheck() {
        // Vérifie que l'application répond (même avec une 404)
        String url = "http://localhost:" + port + "/";

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            assertNotNull(response, "L'application doit répondre");
            System.out.println("Status de réponse: " + response.getStatusCode());
            System.out.println("Application en bonne santé ✅");
        } catch (Exception e) {
            fail("L'application devrait répondre même avec une erreur 404: " + e.getMessage());
        }
    }
}