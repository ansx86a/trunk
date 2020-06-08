package dao;

import my.ex.ExController;
import myspringBoot.MysbApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MysbApplication.class)
public class ControllerTest {
    @Autowired
    ExController exController;

    @Test
    public void test() throws Exception {
        exController.test1(null, null);
    }
}
