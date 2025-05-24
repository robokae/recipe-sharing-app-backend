package com.recipedb.api.repository;

import com.recipedb.api.model.Account;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    @NonNull
    Optional<Account> findByUsername(@NonNull String username);

    @NonNull
    Optional<Account> findById(@NonNull String id);
}
