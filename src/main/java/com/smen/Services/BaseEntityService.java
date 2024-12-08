package com.smen.Services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public abstract class BaseEntityService<T, Long> {

    private final JpaRepository<T, Long> repository;

    public BaseEntityService(JpaRepository<T, Long> repository) {
        this.repository = repository;
    }

    public Optional<T> getByid(Long id) {
        return repository.findById(id);
    }

    public List<T> getAll() {
        return repository.findAll();
    }

    public boolean deleteById(Long id) {
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
