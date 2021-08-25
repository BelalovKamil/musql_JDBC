package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userServiceImpl = new UserServiceImpl();
        userServiceImpl.createUsersTable();
        userServiceImpl.saveUser("Oleg","Makarov", (byte)25);
        userServiceImpl.saveUser("Stepan","Razin", (byte)25);
        userServiceImpl.saveUser("Alex","Pushkin", (byte)90);
        userServiceImpl.saveUser("Sergey","Esenin", (byte)80);
        System.out.println(userServiceImpl.getAllUsers());
        userServiceImpl.cleanUsersTable();
        userServiceImpl.dropUsersTable();
    }
}