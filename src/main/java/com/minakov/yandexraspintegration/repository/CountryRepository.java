package com.minakov.yandexraspintegration.repository;

import com.minakov.yandexraspintegration.model.CountryEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CountryRepository extends GenericRepository<CountryEntity, UUID>, JpaSpecificationExecutor<CountryEntity> {

}
