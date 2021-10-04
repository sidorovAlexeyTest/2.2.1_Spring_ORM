package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      userService.add(new User("User1", "Lastname1", "user1@mail.ru"));
      userService.add(new User("User2", "Lastname2", "user2@mail.ru"));
      userService.add(new User("User3", "Lastname3", "user3@mail.ru"));
      userService.add(new User("User4", "Lastname4", "user4@mail.ru"));

      Car car1 = new Car("Toyota", 90);
      Car car2 = new Car("Volvo", 100);
      Car car3 = new Car("Porch", 90);
      Car car4 = new Car("Hundai", 2000);

      User user5 = new User("Alex", "Rubanov", "rub@mail.ru");
      user5.setCar(car1);
      User user6 = new User("Nikita", "Djigurda", "djiga@mail.ru");
      user6.setCar(car2);
      User user7 = new User("Ivan", "Petrov", "petricy@mail.ru");
      user7.setCar(car3);
      User user8 = new User("Petr", "Ivanov", "ivacya@mail.ru");
      user8.setCar(car4);

      List<User> users = userService.listUsers();

      userService.add(user5);
      userService.add(user6);
      userService.add(user7);
      userService.add(user8);

      users.add(userService.getUserByCar("Toyota", 90));
      users.add(userService.getUserByCar("Volvo", 100));
      users.add(userService.getUserByCar("Porch", 90));
      users.add(userService.getUserByCar("Hundai", 2000));
      for (User user : users) {
         System.out.println("Id = "+user.getId());
         System.out.println("First Name = "+user.getFirstName());
         System.out.println("Last Name = "+user.getLastName());
         System.out.println("Email = "+user.getEmail());
         System.out.println();
      }
      context.close();
   }
}
