package com.reward;

import com.reward.dto.RewardReportDto;
import com.reward.model.Purchase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RewardManagerIntegrationTest {

    private String uri;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void init() {
        uri = "http://localhost:" + port + "/reward/";
    }

    @Test
    public void testTotalRewardReportSuccess() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<RewardReportDto> responseEntity = restTemplate.getForEntity(
                uri + "reportTotal?customer_id=2",
                RewardReportDto.class
        );
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(22, responseEntity.getBody().getPoints());
    }

    @Test
    public void testTotalRewardReportNoParams() {
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<RewardReportDto> responseEntity = restTemplate.getForEntity(
                    uri + "reportTotal",
                    RewardReportDto.class
            );
        } catch (HttpClientErrorException.BadRequest ex) {
            assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        }

    }

    @Test
    public void testTotalRewardReportNoPurchases() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<RewardReportDto> responseEntity = restTemplate.getForEntity(
                uri + "reportTotal?customer_id=100",
                RewardReportDto.class
        );
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    public void testMonthRewardReportSuccess() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<RewardReportDto> responseEntity = restTemplate.getForEntity(
                uri + "reportTotal?customer_id=2&month=7",
                RewardReportDto.class
        );
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(22, responseEntity.getBody().getPoints());
    }

    @Test
    public void testMonthRewardReportNoParams() {
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<RewardReportDto> responseEntity = restTemplate.getForEntity(
                    uri + "reportTotal?month=7",
                    RewardReportDto.class
            );
        } catch (HttpClientErrorException.BadRequest ex) {
            assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        }

    }

    @Test
    public void testCreatePurchase() {
        Purchase purchase = new Purchase();
        purchase.setId(101L);
        purchase.setCustomerId(101L);
        purchase.setCreateDate(LocalDate.of(2023, 11, 1));
        purchase.setPrice(BigDecimal.valueOf(65.3f));

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Purchase> responseEntity = restTemplate.postForEntity(
                uri + "purchase",
                purchase,
                Purchase.class
        );
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void testGetPurchase() {

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Purchase> responseEntity = restTemplate.getForEntity(
                uri + "purchase/1",
                Purchase.class
        );
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, responseEntity.getBody().getId());
    }

    @Test
    public void testUpdatePurchase() {
        Purchase purchase = new Purchase();
        purchase.setCustomerId(102L);
        purchase.setCreateDate(LocalDate.of(2023, 11, 2));
        purchase.setPrice(BigDecimal.valueOf(66.3f));

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(
                uri + "purchase/2",
                purchase
        );
    }

    @Test
    public void testDeletePurchase() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(
                uri + "purchase/2"
        );
    }

}
