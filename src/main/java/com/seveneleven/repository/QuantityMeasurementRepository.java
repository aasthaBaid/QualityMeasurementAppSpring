package com.seveneleven.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seveneleven.model.entity.QuantityMeasurementEntity;

@Repository
public interface QuantityMeasurementRepository 
extends JpaRepository<QuantityMeasurementEntity, Long> {
}