package org.example;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryManager {
    private static SessionFactory sessionFactory;

    public static SessionFactory getInstance() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration()
                    .configure().buildSessionFactory();
        }

        return sessionFactory;
    }
}