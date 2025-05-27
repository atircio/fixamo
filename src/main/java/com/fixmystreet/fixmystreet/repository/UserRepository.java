package com.fixmystreet.fixmystreet.repository;

import com.fixmystreet.fixmystreet.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
