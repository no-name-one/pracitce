package ru.ithub.nero;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        User user = context.getBean("myBean", User.class);
        System.out.println(user.getName());

        User user2 = context.getBean(User.class);
        System.out.println(user2);

        context.close();
    }
}