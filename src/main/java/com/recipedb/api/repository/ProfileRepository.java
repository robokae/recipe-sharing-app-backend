package com.recipedb.api.repository;

import com.recipedb.api.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, String> {

    @Query("select p from Profile p, Account a where p.accountId = a.id and a.username = :username")
    Optional<Profile> findByUsername(@Param("username") String username);

    Optional<Profile> findByAccountId(String accountId);
}
