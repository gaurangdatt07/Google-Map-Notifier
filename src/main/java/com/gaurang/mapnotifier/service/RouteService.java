package com.gaurang.mapnotifier.service;

import com.gaurang.mapnotifier.bean.CheckRouteResponse;
import com.gaurang.mapnotifier.bean.Route;
import com.gaurang.mapnotifier.bean.RouteDto;

import java.util.List;

public interface RouteService {

    RouteDto addRoute(RouteDto routeDto);
    RouteDto geRouteById(Long id);
    List<Route> getRoutes();
    List<Route> deleteRoutes(List<Long> ids);
    CheckRouteResponse checkEta(Long id);
}
