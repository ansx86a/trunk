package dao;

import dao.domain.Person;
import myspringBoot.MysbApplication;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MysbApplication.class)
public class DaoTest {

    @Autowired
    private PersonMapper personMapper;

    @Test
    public void test() {
        Person person = personMapper.selectByPrimaryKey(1);
        System.out.println(ReflectionToStringBuilder.reflectionToString(person));
        person.setAge(person.getAge() + 1);
        personMapper.updateByPrimaryKey(person);
        System.out.println(ReflectionToStringBuilder.reflectionToString(personMapper.selectByPrimaryKey(1)));
    }
}
