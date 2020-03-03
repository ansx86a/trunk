package dao;

import my.hc.HcCatch;
import myspringBoot.MysbApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MysbApplication.class)
public class HcTest {
    @Autowired
    private HcomicPoolMapper hcomicPoolMapper;
    @Autowired
    private HcCatch hcCatch;

    @Test
    public void hcCatchCatchTest() throws Exception {
        hcCatch.main(HcCatch.爬蟲type.單行本, false);
    }

    @Test
    public void hcCatchDownloadTest() throws Exception {
        hcCatch.main(HcCatch.爬蟲type.單行本, true);
    }

    @Test
    public void 漫畫自然排序法重新命名() throws IOException {
        File souceDir = new File("z:/1");
        File destDir = new File("z:/2");
        HcCatch.漫畫自然排序法重新命名(souceDir, destDir);

    }
}



