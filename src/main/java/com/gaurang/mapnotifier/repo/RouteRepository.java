package com.gaurang.mapnotifier.repo;

import com.gaurang.mapnotifier.bean.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<Route, String> {
}
