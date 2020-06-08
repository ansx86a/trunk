package dao;

import my.ex.ExCatch;
import myspringBoot.MysbApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MysbApplication.class)
public class ExTest {

    @Autowired
    private ExCatch eExCatch;

    @Test
    public void runEx() throws Exception {
        eExCatch.main();
    }
}
