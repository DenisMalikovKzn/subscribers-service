package com.example.demo.controller;

import com.example.demo.dto.SubscriptionRequest;
import com.example.demo.dto.SubscriptionResponse;
import com.example.demo.dto.TopSubscriptionResponse;
import com.example.demo.entity.Subscription;
import com.example.demo.entity.User;
import com.example.demo.service.SubscriptionService;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<SubscriptionResponse> addSubscription(
            @PathVariable Long userId,
            @Valid @RequestBody SubscriptionRequest request) {
        User user = userService.getUserEntityById(userId);
        Subscription subscription = subscriptionService.convertToSubscriptionEntity(request, user);
        Subscription created = subscriptionService.createSubscription(userId, subscription);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(subscriptionService.convertToSubscriptionResponse(created));
    }

    @GetMapping
    public ResponseEntity<List<SubscriptionResponse>> getSubscriptions(
            @PathVariable Long userId) {
        List<Subscription> subscriptions = subscriptionService.getUserSubscriptions(userId);
        List<SubscriptionResponse> response = subscriptions.stream()
                .map(subscriptionService::convertToSubscriptionResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{subscriptionId}")
    public ResponseEntity<Void> deleteSubscription(
            @PathVariable Long subscriptionId) {
        subscriptionService.deleteSubscription(subscriptionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/top")
    public ResponseEntity<List<TopSubscriptionResponse>> getTopSubscriptions() {
        return ResponseEntity.ok(subscriptionService.getTop3PopularSubscriptions());
    }
}
