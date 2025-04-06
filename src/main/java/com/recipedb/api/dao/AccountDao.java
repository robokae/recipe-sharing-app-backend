package com.recipedb.api.dao;

import com.recipedb.api.model.Account;
import com.recipedb.api.util.DatabaseUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class AccountDao implements Dao<Account> {

    public static final Logger LOGGER = LoggerFactory.getLogger(AccountDao.class);

    @Autowired
    private DatabaseUtil databaseUtil;

    @Override
    public Optional<Account> get(int id) {
        return Optional.empty();
    }

    public Optional<Account> findByUsername(String username) {
        String query = "select * from Account where username = ?";
        Account account = null;

        try (ResultSet rs = databaseUtil.executeQuery(query, List.of(username))) {
            if (rs.next()) {
                account = Account.builder()
                        .id(rs.getInt("id"))
                        .firstName(rs.getString("firstName"))
                        .lastName(rs.getString("lastName"))
                        .username(username)
                        .password(rs.getString("password"))
                        .role(rs.getString("role"))
                        .email(rs.getString("email"))
                        .description(rs.getString("description")).build();
            }

        } catch(SQLException e) {
            LOGGER.error("SQLException: {}", e.getMessage());
        }
        return Optional.ofNullable(account);
    }

    @Override
    public void save(Account account) {
        String query = """
                insert into Account(firstName, lastName, username, password, role, email)
                values (?, ?, ?, ?, ?, ?)
                """;
        try  {
            List<Object> params = List.of(account.getFirstName(), account.getLastName(), account.getUsername(),
                    account.getPassword(), account.getRole(), account.getEmail());
            databaseUtil.executeUpdate(query, params);
        } catch (SQLException e) {
            LOGGER.error("SQLException: {}", e.getMessage());
        }
    }

    @Override
    public void update(Account account) {}

    @Override
    public void delete(Account account) {}
}
