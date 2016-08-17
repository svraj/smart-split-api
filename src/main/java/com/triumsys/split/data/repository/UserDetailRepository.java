package com.triumsys.split.data.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.triumsys.split.data.entity.UserDetail;


public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
}
