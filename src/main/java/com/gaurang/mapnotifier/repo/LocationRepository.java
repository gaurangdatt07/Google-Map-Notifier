package com.gaurang.mapnotifier.repo;

import com.gaurang.mapnotifier.bean.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    /**
     * Find all locations by list of IDs and isDeleted status
     * Uses Spring Data JPA method name query derivation
     * "In" keyword handles the List parameter automatically
     * @param ids List of location IDs
     * @param isDeleted deleted status (0 = not deleted, 1 = deleted)
     * @return List of locations matching the criteria
     */
    List<Location> findAllByIdInAndIsDeleted(List<Long> ids, byte isDeleted);
}
