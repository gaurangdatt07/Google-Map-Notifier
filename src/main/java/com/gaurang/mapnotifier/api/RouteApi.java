package com.gaurang.mapnotifier.api;

import com.gaurang.mapnotifier.bean.Route;
import com.gaurang.mapnotifier.bean.RouteDto;
import com.gaurang.mapnotifier.service.RouteService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/route")
public class RouteApi {

    @Autowired
    RouteService routeService;

    @PostMapping("/watch")
    public RouteDto route(@RequestBody final RouteDto route){
        return routeService.addRoute(route);
    }

    @GetMapping("/{id}")
    public RouteDto route(@PathParam("id") final Long id){
        return routeService.geRouteById(id);
    }

    @GetMapping()
    public List<Route> routes(){
        return routeService.getRoutes();
    }

    @DeleteMapping("/delete/{id}")
    public List<Route> deleteRoute(@PathParam("id")Long routeId){
        return routeService.deleteRoutes(Collections.singletonList(routeId));
    }

}
