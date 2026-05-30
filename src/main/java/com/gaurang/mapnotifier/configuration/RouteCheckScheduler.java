package com.gaurang.mapnotifier.configuration;

import com.gaurang.mapnotifier.bean.Route;
import com.gaurang.mapnotifier.bean.Status;
import com.gaurang.mapnotifier.repo.RouteRepository;
import com.gaurang.mapnotifier.service.RouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Slf4j
public class RouteCheckScheduler {
    private final RouteRepository routeRepository;
    private final RouteService routeService;

    public RouteCheckScheduler(RouteRepository routeRepository, RouteService routeService) {
        this.routeRepository = routeRepository;
        this.routeService = routeService;
    }

    @Scheduled(fixedDelay = 1000000000)
    public void checkDueRoutes() {
        log.info("Checking Due Routes");
        long now = System.currentTimeMillis();

        List<Route> dueRoutes =
                routeRepository.findAllByStatusAndIsDeletedAndNextCheckScheduledEpochLessThanEqual(
                        Status.ACTIVE,
                        (byte) 0,
                        now
                );

        for (Route route : dueRoutes) {
            routeService.checkEta(route.getId());
        }
    }
}
