package dao;

import dao.domain.MoePool;
import dao.domain.MoePoolExample;
import my.moe.MoeCatch;
import myspringBoot.MysbApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MysbApplication.class)
public class MoeTest {
    @Autowired
    private MoeCatch moeCatch;
    @Autowired
    private MoePoolMapper moePoolMapper;

    @Test
    public void runMoe() throws Exception {
        moeCatch.main();
    }

    @Test
    public void 刪除目前的下載檔案() {
        MoePoolExample ex = new MoePoolExample();
        ex.createCriteria().andPostidGreaterThan(5000);
        List<MoePool> list = moePoolMapper.selectByExample(ex);
        List<Integer> idList = list.stream().filter(o -> new File(o.getFilePath()).exists())
                .map(MoePool::getPostid)
                .collect(Collectors.toList());
        System.out.println(idList);


    }

}
