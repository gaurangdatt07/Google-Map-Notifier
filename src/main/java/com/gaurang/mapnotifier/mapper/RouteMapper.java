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

    public Route toEntity(RouteDto dto,Long originId,Long destinationId) {
        if (dto == null) {
            return null;
        }
        return Route.builder()
                .routeName(dto.getName())
                .originId(originId)
                .destinationId(destinationId)
                .status(Status.ACTIVE)
                .targetEtaMinutes(dto.getTargetEtaMinutes())
                .intervalTime(dto.getIntervalTime())
                .createdAtEpoch(System.currentTimeMillis())
                .isDeleted((byte) 0).build();
    }


    public RouteDto toDto(Route route,Location destination,Location origin) {
        if (route == null) {
            return null;
        }

        return RouteDto.builder()
                .id(route.getId())
                .name(route.getRouteName())
                .origin(origin)
                .destination(destination)
                .build();
    }
}
