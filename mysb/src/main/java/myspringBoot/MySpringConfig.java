package myspringBoot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan({"hello", "dao"})
@Configuration
@MapperScan("dao")
public class MySpringConfig {
}
