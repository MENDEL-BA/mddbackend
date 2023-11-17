package com.openclassrooms.mddapi.services.impl;

import com.openclassrooms.mddapi.models.Subscription;
import com.openclassrooms.mddapi.repositories.SubscriptionRepository;
import com.openclassrooms.mddapi.services.SubscriptionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    @Transactional
    public void subscribe(Subscription subscription) {
        if (!subscribeExistence(subscription)) {
            this.subscriptionRepository.save(subscription);
        }
    }

    @Override
    @Transactional
    public void unsubscribe(Subscription subscription) {
        if (subscribeExistence(subscription)) {
            this.subscriptionRepository.deleteByUserIdAndSubjectId(subscription.getUserId(), subscription.getSubjectId());
        } else throw new RestClientException("la sousscription n' existe pas");
    }

    private boolean subscribeExistence(Subscription subscription) {
        return this.subscriptionRepository.existsByUserIdAndSubjectId(subscription.getUserId(), subscription.getSubjectId());
    }
}
