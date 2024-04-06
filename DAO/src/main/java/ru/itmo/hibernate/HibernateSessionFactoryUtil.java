package ru.itmo.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ru.itmo.cats.Cat;
import ru.itmo.owners.Owner;

public class HibernateSessionFactoryUtil {

    private static SessionFactory sessionFactory;

    private HibernateSessionFactoryUtil() {
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(Owner.class);
                configuration.addAnnotatedClass(Cat.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(
                        configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());

            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
        }
        return sessionFactory;
    }
}