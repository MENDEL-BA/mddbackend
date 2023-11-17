package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.Subscription;

public interface SubscriptionService {
    void subscribe(Subscription subscription);
    void unsubscribe(Subscription subscription);
}
