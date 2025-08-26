package com.project.footfair.service;

import jakarta.validation.ValidationException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public abstract class BaseService {
    protected <T, ID> T findEntityById(CrudRepository<T, ID> repository, ID id, String entityName){
        return repository.findById(id)
                .orElseThrow(() -> new ValidationException(entityName + " not found!"));
    }
}
