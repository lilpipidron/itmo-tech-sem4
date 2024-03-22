package ru.itmo;

import org.flywaydb.core.Flyway;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.itmo.cats.CatRepositoryImp;
import ru.itmo.cats.CatServiceImp;
import ru.itmo.owners.OwnerRepositoryImp;
import ru.itmo.owners.OwnerServiceImp;

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
        OwnerRepositoryImp ownerRepository = new OwnerRepositoryImp(session);

        CatServiceImp catService = new CatServiceImp(catsRepository, ownerRepository);
        OwnerServiceImp ownerService = new OwnerServiceImp(catsRepository, ownerRepository);
        ownerService.addNewOwner("name", "2006-01-02");
        session.close();

        sessionFactory.close();

    }
}
