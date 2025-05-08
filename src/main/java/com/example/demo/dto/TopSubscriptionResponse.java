package com.example.demo.dto;

public record TopSubscriptionResponse(
        String serviceName,
        Long subscribersCount
) {}