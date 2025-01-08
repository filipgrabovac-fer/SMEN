package com.smen.Services;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class BaseEntityService<T, ID> {

    private final JpaRepository<T, ID> repository;

    protected BaseEntityService(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    public Optional<T> getById(ID id) {
        return repository.findById(id);
    }

    public List<T> getAll() {
        return repository.findAll();
    }

    public boolean deleteById(ID id) {
        Optional<T> item = repository.findById(id);
        if (item.isPresent()) {
            repository.delete(item.get());
            return true;
        }
        return false;
    }

    public T create(T item) {
        return repository.save(item);
    }

}
