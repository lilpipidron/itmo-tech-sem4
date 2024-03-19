package ru.itmo;

import org.flywaydb.core.Flyway;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import ru.itmo.cats.Cat;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Flyway flyway = Flyway.configure()
                .dataSource("jdbc:postgresql://localhost:5432/cats_owners", "postgres", "postgres")

                .locations("classpath:db/migration")
                .load();

        flyway.migrate();

        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        Session session = sessionFactory.openSession();

        Query<Cat> query = session.createQuery("FROM Cat", Cat.class);

        List<Cat> cats = query.getResultList();

        // Вывод списка котов
        for (Cat cat : cats) {
            System.out.println("Cat ID: " + cat.getId() + ", Name: " + cat.getName());
        }

        session.close();

        sessionFactory.close();

    }
}
