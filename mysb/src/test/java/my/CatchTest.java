package my;

import my.ex.ExCatch;
import my.hc.HcCatch;
import my.moe.MoeCatch;
import myspringBoot.MysbApplication;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MysbApplication.class)
public class CatchTest {

    @Autowired
    private HcCatch hcCatch;
    @Autowired
    private MoeCatch moeCatch;
    @Autowired
    ExCatch exCatch;

    @Test
    @Ignore
    public void hcCatchTest() throws Exception {
        hcCatch.main();
    }

    @Test
    public void moeCatchTest() throws Exception {
        moeCatch.main();
    }

    @Test
    public void exCatchTest() throws Exception {
        exCatch.main();
    }

}
