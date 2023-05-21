package com.reward.service;

import com.reward.dto.RewardReportDto;
import com.reward.model.Purchase;

public interface RewardService {

    Purchase createPurchase(Purchase purchase);

    Purchase getPurchase(Long id);

    Purchase updatePurchase(Long id, Purchase updatedPurchase);

    void deletePurchase(Long id);

    RewardReportDto getRewardReport(Long customerId);

    RewardReportDto getRewardReport(Long customerId, Integer month);

}
