package com.gaurang.mapnotifier.mapper;

import com.gaurang.mapnotifier.bean.Location;
import com.gaurang.mapnotifier.bean.Route;
import com.gaurang.mapnotifier.bean.RouteDto;
import com.gaurang.mapnotifier.bean.Status;
import com.gaurang.mapnotifier.repo.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RouteMapper {

    @Autowired
    private LocationRepository locationRepository;

    /**
     * Converts RouteDto to Route entity
     * Persists Location objects first and maps only their IDs to Route
     * @param dto the RouteDto to convert
     * @return Route entity with Location references
     */
    public Route toEntity(RouteDto dto,Long originId,Long destinationId) {
        if (dto == null) {
            return null;
        }

        Route route = new Route();
        route.setRouteName(dto.getName());
        route.setOriginId(dto.getOrigin().getId());
        route.setDestinationId(dto.getDestination().getId());
        route.setStatus(Status.ACTIVE);
        route.setCreatedAtEpoch(System.currentTimeMillis());
        route.setIsDeleted((byte) 0);

        return route;
    }

    /**
     * Converts Route entity to RouteDto
     * @param route the Route entity to convert
     * @return RouteDto
     */
    public RouteDto toDto(Route route,Location destination,Location origin) {
        if (route == null) {
            return null;
        }

        return RouteDto.builder()
                .id(route.getId())
                .name(route.getRouteName())
                .origin(origin)      // Returns full Location object
                .destination(destination) // Returns full Location object
                .build();
    }
}
