package org.example.reminder.business.repository;

import org.example.reminder.business.model.BusinessEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import java.util.List;

@Repository
public interface BusinessRepository extends JpaRepository<BusinessEntity, Integer> {

    Optional<BusinessEntity> findById(Long id);

    List<BusinessEntity> findAllByBusinessVerifiedFalse();

    List<BusinessEntity> findAll();

}
