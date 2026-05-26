package com.magicwand.repository;

import com.magicwand.entity.MagicProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MagicProviderRepository extends JpaRepository<MagicProvider, Long> {

    Optional<MagicProvider> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);
}
