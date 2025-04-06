package com.recipedb.api.dao;

import java.util.Optional;

public interface Dao<T> {

    Optional<T> get(int id);
    void save(T t);
    void update(T t);
    void delete(T t);
}
