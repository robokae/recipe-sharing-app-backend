package com.recipedb.api.dao;

import java.util.Optional;

public interface Dao<T> {

    default Optional<T> get(int id) {
        return Optional.empty();
    }

    void save(T t);
    void update(T t);
    void delete(T t);
}
