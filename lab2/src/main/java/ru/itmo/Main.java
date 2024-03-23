package ru.itmo;

import org.flywaydb.core.Flyway;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.itmo.cats.CatRepositoryImpl;
import ru.itmo.cats.CatServiceImpl;
import ru.itmo.owners.OwnerRepositoryImpl;
import ru.itmo.owners.OwnerServiceImpl;

public class Main {
    public static void main(String[] args) {
        Flyway flyway = Flyway.configure()
                .dataSource("jdbc:postgresql://localhost:5432/cats_owners", "postgres", "postgres")
                .locations("classpath:db/migration")
                .load();

        flyway.migrate();

        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

        Session session = sessionFactory.openSession();

        CatRepositoryImpl catsRepository = new CatRepositoryImpl(session);
        OwnerRepositoryImpl ownerRepository = new OwnerRepositoryImpl(session);

        CatServiceImpl catService = new CatServiceImpl(catsRepository, ownerRepository);
        OwnerServiceImpl ownerService = new OwnerServiceImpl(catsRepository, ownerRepository);
        ownerService.addNewOwner("name", "2006-01-02");
        session.close();

        sessionFactory.close();

    }
}
