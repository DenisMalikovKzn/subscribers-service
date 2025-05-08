package com.example.demo.service;

import com.example.demo.dto.SubscriptionRequest;
import com.example.demo.dto.SubscriptionResponse;
import com.example.demo.dto.TopSubscriptionResponse;
import com.example.demo.entity.Subscription;
import com.example.demo.entity.User;
import com.example.demo.repository.SubscriptionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserService userService;

    @Override
    @Transactional
    public Subscription createSubscription(Long userId, Subscription subscription) {
        User user = userService.getUserEntityById(userId);
        subscription.setUser(user);
        return subscriptionRepository.save(subscription);
    }

    @Override
    public List<Subscription> getUserSubscriptions(Long userId) {
        return subscriptionRepository.findByUserId(userId);
    }

    @Override
    @Transactional
    public void deleteSubscription(Long subscriptionId) {
        subscriptionRepository.deleteById(subscriptionId);
    }

    /*@Override
    public List<Map<String, Object>> getTop3PopularSubscriptions() {
        return subscriptionRepository.findTop3PopularSubscriptions().stream()
                .map(result -> Map.of(
                        "serviceName", result[0],
                        "count", result[1]
                ))
                .collect(Collectors.toList());
    }*/

    @Override
    public List<TopSubscriptionResponse> getTop3PopularSubscriptions() {
        return subscriptionRepository.findTop3PopularSubscriptions().stream()
                .map(result -> new TopSubscriptionResponse(
                        (String) result[0],
                        (Long) result[1]
                ))
                .toList();
    }

    @Override
    public SubscriptionResponse convertToSubscriptionResponse(Subscription subscription) {
        return new SubscriptionResponse(
                subscription.getId(),
                subscription.getServiceName(),
                subscription.getStartDate(),
                subscription.getUser().getId()
        );
    }

    @Override
    public Subscription convertToSubscriptionEntity(SubscriptionRequest request, User user) {
        Subscription subscription = new Subscription();
        subscription.setServiceName(request.serviceName());
        subscription.setUser(user);
        return subscription;
    }
}
