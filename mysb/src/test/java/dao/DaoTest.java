package dao;

import dao.domain.HcomicPool;
import dao.domain.HcomicPoolExample;
import dao.domain.MoePool;
import dao.domain.MoePoolExample;
import my.moe.MoeCatch;
import myspringBoot.MysbApplication;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MysbApplication.class)
public class DaoTest {

    @Autowired
    private MoePoolMapper moePoolMapper;
    @Autowired
    private HcomicPoolMapper hcomicPoolMapper;
    @Autowired
    private MoeCatch moeCatch;
    @Test
    public void runMoe() throws Exception {
        moeCatch.main();
    }


    @Test
    public void test() {
        List<MoePool> all = moePoolMapper.selectByExample(new MoePoolExample());
        System.out.println(all.size());
        HcomicPoolExample ex = new HcomicPoolExample();
        List<HcomicPool> hcList = hcomicPoolMapper.selectByExample(ex);
        for (HcomicPool hc : hcList) {
            System.out.println(ToStringBuilder.reflectionToString(hc));
        }
    }

    @Test
    public void testExample() {
        MoePoolExample ex = new MoePoolExample();
        ex.createCriteria().andTitle1EqualTo("(C91) [まるぐ屋.exe (鈍色玄)] COCKTAIL").andPostidEqualTo(5406);
        List<MoePool> list = moePoolMapper.selectByExample(ex);
        System.out.println(list.size());
        list.stream().forEach(o -> System.out.println(ToStringBuilder.reflectionToString(o)));
    }


}
