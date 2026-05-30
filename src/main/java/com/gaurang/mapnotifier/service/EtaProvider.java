package com.gaurang.mapnotifier.service;

import com.gaurang.mapnotifier.bean.Route;

public interface EtaProvider {
    double getETAMinutes(Route route);
}
