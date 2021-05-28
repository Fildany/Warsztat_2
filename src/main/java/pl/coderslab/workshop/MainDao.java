package pl.coderslab.workshop;

import pl.coderslab.entity.User;
import pl.coderslab.entity.UserDao;

public class MainDao {
    public static void main(String[] args) {
//        User user = new User();
//        user.setUserName("Marek");
//        user.setEmail("dupy@gmail.com");
//        user.setPassword("dupa666");

        UserDao userDao = new UserDao();

//        userDao.addUser(user);
//        userDao.delete(4);
//        User userFind = userDao.find(2);
////        System.out.println("userFind = " + userFind.getUserName());
//        userFind.setUserName("Tomek");
//        userFind.setEmail("Gruby_tomek@fat.com");
//        userDao.update(userFind);

        User[] all = userDao.findAll();
        for (int i = 0; i < all.length; i++) {

            System.out.println(all[i].getId() + " " + all[i].getUserName()+ " " + all[i].getEmail() + " " + all[i].getPassword());
        }
    }
}
