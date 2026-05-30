package com.gaurang.mapnotifier.bean;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckRouteResponse {
    private Long routeId;
    private String routeName;
    private String status;
    private Double targeteETA;
    private Long checkHappenedAt;
    private Long nextCheckScheduledAt;
    private Double checkEtaCalculated;
}
