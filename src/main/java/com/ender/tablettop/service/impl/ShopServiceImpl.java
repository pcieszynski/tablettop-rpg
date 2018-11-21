package com.ender.tablettop.service.impl;

import com.ender.tablettop.service.ShopService;
import com.ender.tablettop.domain.Shop;
import com.ender.tablettop.repository.ShopRepository;
import com.ender.tablettop.service.dto.ShopDTO;
import com.ender.tablettop.service.mapper.ShopMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Shop.
 */
@Service
@Transactional
public class ShopServiceImpl implements ShopService {

    private final Logger log = LoggerFactory.getLogger(ShopServiceImpl.class);

    private final ShopRepository shopRepository;

    private final ShopMapper shopMapper;

    public ShopServiceImpl(ShopRepository shopRepository, ShopMapper shopMapper) {
        this.shopRepository = shopRepository;
        this.shopMapper = shopMapper;
    }

    /**
     * Save a shop.
     *
     * @param shopDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ShopDTO save(ShopDTO shopDTO) {
        log.debug("Request to save Shop : {}", shopDTO);

        Shop shop = shopMapper.toEntity(shopDTO);
        shop = shopRepository.save(shop);
        return shopMapper.toDto(shop);
    }

    /**
     * Get all the shops.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ShopDTO> findAll() {
        log.debug("Request to get all Shops");
        return shopRepository.findAllWithEagerRelationships().stream()
            .map(shopMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the Shop with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<ShopDTO> findAllWithEagerRelationships(Pageable pageable) {
        return shopRepository.findAllWithEagerRelationships(pageable).map(shopMapper::toDto);
    }
    

    /**
     * Get one shop by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ShopDTO> findOne(Long id) {
        log.debug("Request to get Shop : {}", id);
        return shopRepository.findOneWithEagerRelationships(id)
            .map(shopMapper::toDto);
    }

    /**
     * Delete the shop by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Shop : {}", id);
        shopRepository.deleteById(id);
    }
}
