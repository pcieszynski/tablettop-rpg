package com.ender.tablettop.repository;

import com.ender.tablettop.domain.PlayerMessage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PlayerMessage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlayerMessageRepository extends JpaRepository<PlayerMessage, Long> {

}
