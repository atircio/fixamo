package com.fixmystreet.fixmystreet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fixmystreet.fixmystreet.model.*;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
