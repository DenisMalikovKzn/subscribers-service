package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SubscriptionRequest(
        @NotBlank(message = "Service name is required")
        @Size(min = 2, max = 100, message = "Service name must be between 2-100 characters")
        String serviceName
) {}
