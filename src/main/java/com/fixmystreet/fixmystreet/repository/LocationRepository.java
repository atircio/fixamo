package com.fixmystreet.fixmystreet.repository;

import com.fixmystreet.fixmystreet.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
