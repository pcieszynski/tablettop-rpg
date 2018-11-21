package com.ender.tablettop.repository;

import com.ender.tablettop.domain.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Shop entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {

    @Query(value = "select distinct shop from Shop shop left join fetch shop.items",
        countQuery = "select count(distinct shop) from Shop shop")
    Page<Shop> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct shop from Shop shop left join fetch shop.items")
    List<Shop> findAllWithEagerRelationships();

    @Query("select shop from Shop shop left join fetch shop.items where shop.id =:id")
    Optional<Shop> findOneWithEagerRelationships(@Param("id") Long id);

}
