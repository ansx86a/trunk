package myspringBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//config可以移到MySpringConfig，預計會scan application的這個package
//@ComponentScan({"hello"})
public class MysbApplication {

    public static void main(String[] args) {
        SpringApplication.run(MysbApplication.class, args);
    }

}
