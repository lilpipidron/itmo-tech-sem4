package ru.itmo;

import org.flywaydb.core.Flyway;

public class Main {
    public static void main(String[] args) {
        Flyway flyway = Flyway.configure()
                .dataSource("jdbc:postgresql://localhost:5432/cats_owners", "postgres", "postgres")

                .locations("classpath:db/migration")
                .load();

        flyway.migrate();

    }
}
