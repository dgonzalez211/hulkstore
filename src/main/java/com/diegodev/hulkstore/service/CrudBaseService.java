package com.diegodev.hulkstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@RequiredArgsConstructor
public abstract class CrudBaseService<R extends JpaRepository<E, Integer>, E> {
    protected final R repository;

    public Optional<E> get(Integer id) {
        return repository.findById(id);
    }

    public E update(E entity) {
        return (E) repository.save(entity);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public Page<E> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<E> list(Pageable pageable, Specification<E> filter) {
        return repository.findAll((Example) filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

    public E save(E entity) {
        return repository.save(entity);
    }
}