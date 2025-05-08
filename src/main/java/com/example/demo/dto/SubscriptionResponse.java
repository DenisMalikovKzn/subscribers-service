package com.example.demo.dto;

import java.time.LocalDate;

public record SubscriptionResponse(
        Long id,
        String serviceName,
        LocalDate startDate,
        Long userId
) {}