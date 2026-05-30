package com.gaurang.mapnotifier.service;

import com.gaurang.mapnotifier.bean.CheckRouteResponse;
import com.gaurang.mapnotifier.bean.Location;
import com.gaurang.mapnotifier.bean.Route;
import com.gaurang.mapnotifier.bean.RouteDto;
import com.gaurang.mapnotifier.bean.Status;
import com.gaurang.mapnotifier.mapper.RouteMapper;
import com.gaurang.mapnotifier.repo.LocationRepository;
import com.gaurang.mapnotifier.repo.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RouteServiceImpl implements RouteService {

    @Autowired
    private RouteRepository routeRepository;
    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private RouteMapper routeMapper;

    @Autowired
    private EtaProvider etaProvider;

    @Override
    public RouteDto addRoute(RouteDto routeDto) {
        Location destination = locationRepository.save(routeDto.getDestination());
        Location origin = locationRepository.save(routeDto.getOrigin());
        Route route = routeRepository.save(
                routeMapper.toEntity(routeDto, origin.getId() ,destination.getId())
        );
        return routeMapper.toDto(route,destination,origin);
    }

    @Override
    public RouteDto
    geRouteById(Long id) {
        Optional<Route> routeOptional = Optional.ofNullable(routeRepository.findByIdAndIsDeleted(id, (byte) 0));
        if(routeOptional.isEmpty()){return null;}
        Map<Long, Location> locationMap = locationRepository.findAllByIdInAndIsDeleted(
                        List.of(
                                routeOptional.get().getOriginId(), routeOptional.get().getDestinationId()
                        ), (byte) 0
                ).stream()
                .collect(
                        Collectors.toMap(Location::getId, Function.identity()));
        return routeMapper.toDto(
                routeOptional.get(),
                locationMap.getOrDefault(routeOptional.get().getOriginId(),null),
                locationMap.getOrDefault(routeOptional.get().getDestinationId(),null)
        );

    }

    @Override
    public List<Route> getRoutes() {
        return routeRepository.findAllByIsDeleted((byte) 0);
    }

    @Override
    public List<Route> deleteRoutes(List<Long> ids) {
        List<Route> routes = routeRepository.findAllById(ids);
                routes.forEach(route -> {
            route.setIsDeleted((byte)1);
        });
        routeRepository.saveAll(routes);
        return routes;
    }

    @Override
    public CheckRouteResponse checkEta(Long id) {
        Route route = routeRepository.findByIdAndIsDeleted(id, (byte) 0);
        if (route == null) {
            throw new RuntimeException("Route not found with id: " + id);
        }

        double mockEta = etaProvider.getETAMinutes(route);

        route.setLastEtaMinutes(mockEta);

        long currentTime = System.currentTimeMillis();
        route.setLastCheckEpoch(currentTime);


        if (route.getTargetEtaMinutes() != null && mockEta <= route.getTargetEtaMinutes()) {
            route.setStatus(Status.TRIGGERED);
            route.setNextCheckScheduledEpoch(0);
        } else {
            long intervalMillis = (long) (route.getIntervalTime() * 60 * 1000);
            route.setNextCheckScheduledEpoch(currentTime + intervalMillis);
        }

        // Save updated route
        Route savedRoute = routeRepository.save(route);

        // Step 7: Fetch locations and return route DTO
        Map<Long, Location> locationMap = locationRepository.findAllByIdInAndIsDeleted(
                        List.of(savedRoute.getOriginId(), savedRoute.getDestinationId()),
                        (byte) 0
                ).stream()
                .collect(Collectors.toMap(Location::getId, Function.identity()));

        return CheckRouteResponse
                .builder()
                .routeId(route.getId())
                .routeName(route.getRouteName())
                .status(route.getStatus().name())
                .checkHappenedAt(route.getLastCheckEpoch())
                .nextCheckScheduledAt(route.getNextCheckScheduledEpoch())
                .checkEtaCalculated(mockEta)
                .targeteETA(route.getTargetEtaMinutes()).build();
    }
}
