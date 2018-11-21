package com.ender.tablettop.repository;

import com.ender.tablettop.domain.MonsterType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MonsterType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MonsterTypeRepository extends JpaRepository<MonsterType, Long> {

}
