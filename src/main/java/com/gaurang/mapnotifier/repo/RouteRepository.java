package com.gaurang.mapnotifier.repo;

import com.gaurang.mapnotifier.bean.Route;
import com.gaurang.mapnotifier.bean.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
     Route findByIdAndIsDeleted(Long id, byte isDeleted);

    List<Route> findAllByIsDeleted(byte isDeleted);

    List<Route> findAllByStatusAndIsDeletedAndNextCheckScheduledEpochLessThanEqual(Status status, byte isDeleted, long nextCheckScheduledEpochIsLessThan);
}
