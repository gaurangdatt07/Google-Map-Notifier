package com.gaurang.mapnotifier.service;


import com.gaurang.mapnotifier.bean.Location;
import com.gaurang.mapnotifier.bean.Route;
import com.gaurang.mapnotifier.repo.LocationRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

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

        // We'll implement actual API call in the next step.
        throw new UnsupportedOperationException("Google Maps ETA provider not implemented yet");

    }
}
