package com.gaurang.mapnotifier.service;

import com.gaurang.mapnotifier.bean.Location;
import com.gaurang.mapnotifier.bean.Route;
import com.gaurang.mapnotifier.bean.RouteDto;
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
}
