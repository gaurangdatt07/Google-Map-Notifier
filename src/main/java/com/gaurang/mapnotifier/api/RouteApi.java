package com.gaurang.mapnotifier.api;

import com.gaurang.mapnotifier.bean.Route;
import com.gaurang.mapnotifier.service.RouteService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/route")
public class RouteApi {

    @Autowired
    RouteService routeService;

    @PostMapping("/watch")
    public Route route(@RequestBody final Route route){
        return route;
    }


    @GetMapping("/{id}")
    public Route route(@PathParam("id") final String id){
        return routeService.geRouteById(id);
    }

    @GetMapping()
    public List<Route> routes(){
        return routeService.getRoutes();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteRoute(@PathParam("id")String routeId){
        return;
    }

}
