package de.openknowledge.workshop.serverless.ocs.service;

import de.openknowledge.workshop.serverless.ocs.repository.OrderCountingRepository;

public class OrderCountingService {

    public static final Long init() {
        return init(0);
    }

    public static final Long init(int initValue) {
        return OrderCountingRepository.initCounter(0);
    }

    public static final Long next() {
        return increment(1);
    }

    public static final Long increment(int value) {
        return OrderCountingRepository.updateCounter(0,value);
    }

    public static final Long decrement(int value) {
        return OrderCountingRepository.updateCounter(0,-1*value);
    }

}
