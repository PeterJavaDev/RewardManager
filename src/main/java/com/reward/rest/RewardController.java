package com.reward.rest;

import com.reward.dto.RewardReportDto;
import com.reward.model.Purchase;
import com.reward.service.RewardService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reward")
public class RewardController {

    private RewardService rewardService;

    @Autowired
    public RewardController(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    @PostMapping("/purchase")
    @Operation(summary = "Create purchase",
            description = "Create a new purchase")
    public ResponseEntity<Purchase> createPurchase(@RequestBody Purchase purchase) {
        Purchase savedPurchase = rewardService.createPurchase(purchase);
        return new ResponseEntity<>(savedPurchase, HttpStatus.CREATED);
    }

    @GetMapping("/purchase/{id}")
    @Operation(
            summary = "Get purchase",
            description = "Get purchase by id")
    public ResponseEntity<Purchase> getPurchase(@PathVariable("id") Long id) {
        Purchase purchase = rewardService.getPurchase(id);
        return new ResponseEntity<>(purchase, HttpStatus.OK);
    }

    @PutMapping("/purchase/{id}")
    @Operation(
            summary = "Update purchase",
            description = "Update existing purchase")
    public ResponseEntity<Purchase> updatePurchase(@PathVariable("id") Long id, @RequestBody Purchase updatedPurchase) {
        Purchase purchase = rewardService.updatePurchase(id, updatedPurchase);
        return new ResponseEntity<>(purchase, HttpStatus.OK);
    }

    @DeleteMapping("/purchase/{id}")
    @Operation(
            summary = "Delete purchase",
            description = "Delete purchase by id")
    public ResponseEntity<Void> deletePurchase(@PathVariable("id") Long id) {
        rewardService.deletePurchase(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/reportTotal")
    @Operation(
            summary = "Get total points",
            description = "Get total points report")
    public ResponseEntity<RewardReportDto> getRewardReport(@RequestParam("customer_id") Long customerId) {
        return new ResponseEntity<>(rewardService.getRewardReport(customerId), HttpStatus.OK);
    }

    @GetMapping(value = "/reportMonth")
    @Operation(
            summary = "Get monthly points",
            description = "Get points report in a give month")
    public ResponseEntity<RewardReportDto> getRewardReport(@RequestParam("customer_id") Long customerId, @RequestParam("month") Integer month) {
        return new ResponseEntity<>(rewardService.getRewardReport(customerId, month), HttpStatus.OK);
    }

}
