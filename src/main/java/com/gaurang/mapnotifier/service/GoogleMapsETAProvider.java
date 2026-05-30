package com.gaurang.mapnotifier.service;


import com.gaurang.mapnotifier.bean.Location;
import com.gaurang.mapnotifier.bean.Route;
import com.gaurang.mapnotifier.repo.LocationRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
@ConditionalOnProperty(name = "eta.provider", havingValue = "google")
public class GoogleMapsETAProvider implements EtaProvider{

    private final LocationRepository locationRepository;
    private final RestClient restClient;

    @Value("${google.maps.api-key}")
    private String apiKey;

    @Value("${google.maps.routes-url}")
    private String routesUrl;

    public GoogleMapsETAProvider(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
        this.restClient = RestClient.create();
    }

    @Override
    public double getETAMinutes(Route route) {
        Location origin = locationRepository.findById(route.getOriginId())
                .orElseThrow(() -> new RuntimeException("Origin not found"));

        Location destination = locationRepository.findById(route.getDestinationId())
                .orElseThrow(() -> new RuntimeException("Destination not found"));

        Map<String, Object> requestBody = Map.of(
                "origin", Map.of(
                        "location", Map.of(
                                "latLng", Map.of(
                                        "latitude", origin.getLatitude(),
                                        "longitude", origin.getLongitude()
                                )
                        )
                ),
                "destination", Map.of(
                        "location", Map.of(
                                "latLng", Map.of(
                                        "latitude", destination.getLatitude(),
                                        "longitude", destination.getLongitude()
                                )
                        )
                ),
                "travelMode", "DRIVE",
                "routingPreference", "TRAFFIC_AWARE"
        );


        try{
            Map response = restClient.post()
                    .uri(routesUrl)
                    .header("Content-Type", "application/json")
                    .header("X-Goog-Api-Key", apiKey)
                    .header("X-Goog-FieldMask", "routes.duration")
                    .body(requestBody)
                    .retrieve()
                    .body(Map.class);
            List<Map<String, Object>> routes = (List<Map<String, Object>>) response.get("routes");
            if (routes == null || routes.isEmpty()) {
                throw new RuntimeException("No route returned from Google Routes API");
            }

            String duration = (String) routes.get(0).get("duration");

            return parseDurationToMinutes(duration);
        }catch (Exception ex){
         throw new RuntimeException("failed to fetch routes from google maps", ex);
        }



    }

    private double parseDurationToMinutes(String duration) {
        if (duration == null || !duration.endsWith("s")) {
            throw new RuntimeException("Invalid duration from Google Routes API: " + duration);
        }

        double seconds = Double.parseDouble(duration.replace("s", ""));
        return seconds / 60.0;
    }
}
