package com.example.demo.service;

import com.example.demo.dto.SubscriptionRequest;
import com.example.demo.dto.SubscriptionResponse;
import com.example.demo.dto.TopSubscriptionResponse;
import com.example.demo.entity.Subscription;
import com.example.demo.entity.User;

import java.util.List;

public interface SubscriptionService {
    Subscription createSubscription(Long userId, Subscription subscription);
    List<Subscription> getUserSubscriptions(Long userId);
    void deleteSubscription(Long subscriptionId);
    List<TopSubscriptionResponse> getTop3PopularSubscriptions();
    SubscriptionResponse convertToSubscriptionResponse(Subscription subscription);
    Subscription convertToSubscriptionEntity(SubscriptionRequest request, User user);
}
