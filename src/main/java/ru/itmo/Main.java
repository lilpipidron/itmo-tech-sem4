package ru.itmo;

import org.hibernate.Session;
import ru.itmo.cats.CatRepositoryImpl;
import ru.itmo.cats.CatServiceImpl;
import ru.itmo.hibernate.HibernateSessionFactoryUtil;
import ru.itmo.owners.OwnerRepositoryImpl;
import ru.itmo.owners.OwnerServiceImpl;

public class Main {
    public static void main(String[] args) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();

        CatRepositoryImpl catsRepository = new CatRepositoryImpl();
        OwnerRepositoryImpl ownerRepository = new OwnerRepositoryImpl();

        OwnerServiceImpl ownerService = new OwnerServiceImpl(catsRepository, ownerRepository);
        CatServiceImpl catService = new CatServiceImpl(catsRepository, ownerRepository);
        ownerService.addNewOwner("name", "2006-01-02");
        catService.addNewCat(1, "name", "2006-02-01", "PERSIAN", "PINK");


        session.close();
    }
}
