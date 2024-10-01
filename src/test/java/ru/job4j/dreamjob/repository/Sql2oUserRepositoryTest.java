package ru.job4j.dreamjob.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.job4j.dreamjob.configuration.DatasourceConfiguration;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.repository.user.Sql2oUserRepository;

import java.util.Properties;

import static org.assertj.core.api.Assertions.*;

class Sql2oUserRepositoryTest {

    private static Sql2oUserRepository sql2oUserRepository;
    private static Sql2o sql2o;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oUserRepositoryTest.class.getClassLoader()
                .getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        sql2o = configuration.databaseClient(datasource);

        sql2oUserRepository = new Sql2oUserRepository(sql2o);
    }

    @BeforeEach
    public void clearDatabase() {
        try (var connection = sql2o.open()) {
            connection.createQuery("DELETE FROM users").executeUpdate();
        }
    }

    @Test
    public void whenSaveUserThenGetUser() {
        var user = sql2oUserRepository.save(new User(0, "email@g.com", "user1", "user123"));
        var savedUser = sql2oUserRepository.findByEmailAndPassword(user.get().getEmail(), user.get().getPassword());
        assertThat(savedUser).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    public void whenSaveTwoUsersThenGetError() {
        var user1 = sql2oUserRepository.save(new User(0, "email@g.com", "user1", "user123"));
        var user2 = sql2oUserRepository.save(new User(0, "email@g.com", "user1", "user123"));

        assertThat(user1).isPresent();
        assertThat(user2).isEmpty();
    }
}