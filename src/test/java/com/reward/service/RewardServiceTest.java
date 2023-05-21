package com.reward.service;

import com.reward.dto.RewardReportDto;
import com.reward.exception.PurchasesNotFoundException;
import com.reward.model.Purchase;
//import com.reward.repository.PurchaseRepository;
import com.reward.repository.PurchaseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class RewardServiceTest {

    @Mock
    PurchaseRepository purchaseRepository;

    @Test
    void testGetRewards() throws PurchasesNotFoundException {
        Purchase p1 = new Purchase();
        p1.setPrice(BigDecimal.valueOf(60.5f));
        Purchase p2 = new Purchase();
        p2.setPrice(BigDecimal.valueOf(10.1f));
        List<Purchase> purchaseList = List.of(p1, p2);
        Long customerId = 2L;

        Mockito.when(purchaseRepository.findByCustomerId(customerId)).thenReturn(purchaseList);

        RewardService rewardService = new RewardServiceImpl(purchaseRepository);
        RewardReportDto rewardReportDto = rewardService.getRewardReport(customerId);

        assertEquals(10, rewardReportDto.getPoints());
    }
}