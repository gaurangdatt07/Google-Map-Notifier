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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "route_name")
    private String routeName;


    @Column(name = "origin_id")
    private Long originId;


    @Column(name = "destination_id")
    private Long destinationId;

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

    @Column(name = "target_eta_minutes")
    private Double targetEtaMinutes;

    @Column(name = "is_deleted")
    private byte isDeleted;
}
