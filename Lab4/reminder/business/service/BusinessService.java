package org.example.reminder.business.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.example.reminder.business.facade.dto.BusinessCreateRequest;
import org.example.reminder.business.model.BusinessEntity;
import org.example.reminder.business.repository.BusinessRepository;
import org.example.reminder.user.model.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessService {
    private final BusinessRepository businessRepository;

    public void createBusiness(@NotNull BusinessCreateRequest businessCreateRequest, @NotNull  UserEntity user) {
        BusinessEntity business = new BusinessEntity();
        business.setName(businessCreateRequest.name());
        business.setDescription(businessCreateRequest.description());
        business.setUser(user);
        business.setType(businessCreateRequest.type());
        businessRepository.save(business);
    }

   public List<BusinessEntity> getAllNotVerifiedBusiness() {
        return businessRepository.findAllByBusinessVerifiedFalse();
    }

    public BusinessEntity findById(Long id) {
        return businessRepository.findById(id).orElseThrow(()->new RuntimeException("business not found by Id %s".formatted(id)));
    }

    public List<BusinessEntity> findAll() {
        return businessRepository.findAll();
    }

    public void verifyBusiness(BusinessEntity entity, Boolean isVerified){
        entity.setBusinessVerified(isVerified);
        businessRepository.save(entity);
    }

}
