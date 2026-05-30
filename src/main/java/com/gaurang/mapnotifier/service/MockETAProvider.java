package com.gaurang.mapnotifier.service;

import com.gaurang.mapnotifier.bean.Route;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "eta.provider", havingValue = "mock", matchIfMissing = true)
public class MockETAProvider implements EtaProvider {

    @Override
    public double getETAMinutes(Route route) {
        return  route.getLastEtaMinutes() == null || route.getLastEtaMinutes() <= 0
                ? 90.0
                : Math.max(5.0, route.getLastEtaMinutes() - 10.0);
    }
}
