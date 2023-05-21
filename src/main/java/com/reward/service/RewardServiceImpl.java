package com.reward.service;

import com.reward.dto.RewardReportDto;
import com.reward.exception.PurchasesNotFoundException;
import com.reward.exception.ResourceNotFoundException;
import com.reward.model.Purchase;
import com.reward.repository.PurchaseRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class RewardServiceImpl implements RewardService {

    private static final Logger log = LogManager.getLogger(RewardServiceImpl.class);

    private final PurchaseRepository purchaseRepository;

    @Autowired
    public RewardServiceImpl(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    public Purchase createPurchase(Purchase purchase) {
        log.info("Creating purchase for customer " + purchase.getCustomerId());

        return purchaseRepository.save(purchase);
    }

    public Purchase getPurchase(Long id) {
        log.info("Getting purchase by ID " + id);

        return purchaseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Purchase not found with ID: " + id));
    }

    public Purchase updatePurchase(Long id, Purchase updatedPurchase) {
        log.info("Updating purchase with ID " + id);

        return purchaseRepository.findById(id).map(purchase -> {
            purchase.setCustomerId(updatedPurchase.getCustomerId());
            purchase.setCreateDate(updatedPurchase.getCreateDate());
            purchase.setPrice(updatedPurchase.getPrice());
            return purchaseRepository.save(purchase);
        }).orElseThrow(() -> new ResourceNotFoundException("Purchase not found with ID: " + id));
    }

    public void deletePurchase(Long id) {
        log.info("Deleting purchase by ID " + id);
        purchaseRepository.deleteById(id);
    }

    public RewardReportDto getRewardReport(Long customerId) {
        log.info("Getting reward points report for customer ID " + customerId);
        List<Purchase> purchaseList = purchaseRepository.findByCustomerId(customerId);
        return calculateRewardReport(purchaseList);
    }

    public RewardReportDto getRewardReport(Long customerId, Integer month) {
        log.info("Getting reward points report for customer ID " + customerId + " in given month");
        List<Purchase> purchaseList = purchaseRepository.findByCustomerIdAndMonth(customerId, month);
        return calculateRewardReport(purchaseList);
    }

    private RewardReportDto calculateRewardReport(List<Purchase> purchaseList) {
        if (purchaseList == null || purchaseList.isEmpty()) {
            log.warn("No purchases found for customer");
            throw new PurchasesNotFoundException("Customer have no purchases");
        }

        int totalPoints = purchaseList.stream()
                .mapToInt(purchase -> calculateRewardPoints(purchase.getPrice()))
                .sum();

        RewardReportDto rewardReportDto = new RewardReportDto();
        rewardReportDto.setPoints(totalPoints);
        return rewardReportDto;
    }

    private int calculateRewardPoints(BigDecimal price) {
        log.debug("Calculating reward points for price " + price);

        int points = 0;

        BigDecimal hundred = BigDecimal.valueOf(100);
        BigDecimal fifty = BigDecimal.valueOf(50);

        if (price.compareTo(hundred) > 0) {
            BigDecimal excess = price.subtract(hundred);
            points += excess.multiply(BigDecimal.valueOf(2)).intValue();
            price = hundred;
        }

        if (price.compareTo(fifty) > 0) {
            BigDecimal excess = price.subtract(fifty);
            points += excess.intValue();
        }

        return points;
    }

}
