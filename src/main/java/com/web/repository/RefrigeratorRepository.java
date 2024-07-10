package com.web.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.web.domain.Refrigerator;

public interface RefrigeratorRepository extends JpaRepository<Refrigerator, Long> {

	Page<Refrigerator> findByUserNumberOrderByExpDateAsc(Pageable pageable, Long userNumber );
	
}
