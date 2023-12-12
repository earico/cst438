package com.cst438.domain;

import java.util.List;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface AttemptRepository extends CrudRepository<Attempt, Integer>{
	
	@Query("select a from Attempt a where a.user.alias=:alias order by a.id desc")
	List<Attempt> findByAliasLastN(
                 @Param("alias") String alias, 
                 Pageable pageable);
	
}
