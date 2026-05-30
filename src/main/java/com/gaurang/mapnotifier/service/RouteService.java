package com.gaurang.mapnotifier.service;

import com.gaurang.mapnotifier.bean.Route;
import com.gaurang.mapnotifier.bean.RouteDto;

import java.util.List;

public interface RouteService {

    List<Route> addRoutes(List<RouteDto>routeDtos);
    Route geRouteById(String id);
    List<Route> getRoutes();
    List<RouteDto> deleteRoutes(List<String> ids);
}
