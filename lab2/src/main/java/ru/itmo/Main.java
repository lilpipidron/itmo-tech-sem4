package ru.itmo;

import org.flywaydb.core.Flyway;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.itmo.cats.CatRepositoryImp;
import ru.itmo.owners.OwnerRepositoryImp;

public class Main {
    public static void main(String[] args) {
        Flyway flyway = Flyway.configure()
                .dataSource("jdbc:postgresql://localhost:5432/cats_owners", "postgres", "postgres")

                .locations("classpath:db/migration")
                .load();

        flyway.migrate();

        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        Session session = sessionFactory.openSession();

        CatRepositoryImp catsRepository = new CatRepositoryImp(session);
        OwnerRepositoryImp ownerRepositoryImp = new OwnerRepositoryImp(session);

        session.close();

        sessionFactory.close();

    }
}
