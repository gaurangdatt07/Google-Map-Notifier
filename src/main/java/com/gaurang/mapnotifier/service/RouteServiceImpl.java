package com.gaurang.mapnotifier.service;

import com.gaurang.mapnotifier.bean.Route;
import com.gaurang.mapnotifier.bean.RouteDto;
import com.gaurang.mapnotifier.repo.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteServiceImpl implements RouteService {

    @Autowired
    private RouteRepository routeRepository;

    @Override
    public List<Route> addRoutes(List<RouteDto> routeDtos) {
        return List.of();
    }

    @Override
    public Route geRouteById(String id) {
        return null;
    }

    @Override
    public List<Route> getRoutes() {
        return routeRepository.findAll();
    }

    @Override
    public List<RouteDto> deleteRoutes(List<String> ids) {
        return List.of();
    }
}
