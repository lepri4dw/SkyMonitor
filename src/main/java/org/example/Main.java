package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import model.User;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Создание объекта пользователя
        User user = new User();
        user.setUsername("John Doe");

        // Создание сессии Hibernate
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class)
                .buildSessionFactory();

        // Сохранение пользователя в базе данных
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Вывод всех пользователей из базы данных
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            List<User> users = session.createQuery("from User", User.class).getResultList();
            for (User retrievedUser : users) {
                System.out.println("Retrieved User: " + retrievedUser.getUsername());
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Закрытие фабрики сессий Hibernate
        sessionFactory.close();
    }
}