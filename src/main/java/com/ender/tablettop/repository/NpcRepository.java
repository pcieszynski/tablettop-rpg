package com.ender.tablettop.repository;

import com.ender.tablettop.domain.Npc;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Npc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NpcRepository extends JpaRepository<Npc, Long> {

}
