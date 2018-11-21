package com.ender.tablettop.repository;

import com.ender.tablettop.domain.Gamemaster;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Gamemaster entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GamemasterRepository extends JpaRepository<Gamemaster, Long> {

}
