package com.gaurang.mapnotifier.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "routes")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "latitude", column = @Column(name = "origin_latitude")),
        @AttributeOverride(name = "longitude", column = @Column(name = "origin_longitude"))
    })
    private Location origin;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "latitude", column = @Column(name = "destination_latitude")),
        @AttributeOverride(name = "longitude", column = @Column(name = "destination_longitude"))
    })
    private Location destination;

    @Column(name = "interval_time")
    private double intervalTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "last_eta_minutes")
    private Double lastEtaMinutes;

    @Column(name = "last_check_epoch")
    private long lastCheckEpoch;

    @Column(name = "next_check_scheduled_epoch")
    private long nextCheckScheduledEpoch;

    @Column(name = "check_expiration_epoch")
    private long checkExpirationEpoch;

    @Column(name = "created_at_epoch")
    private long createdAtEpoch;
}
