package dao;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.Multiset;
import dao.domain.HcomicPool;
import dao.domain.HcomicPoolExample;
import dao.domain.MoePool;
import dao.domain.MoePoolExample;
import my.moe.MoeCatch;
import myspringBoot.MysbApplication;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public void testDownloadMoe() {
        List<MoePool> all = moePoolMapper.selectByExample(new MoePoolExample());
        System.out.println(all.size());
        HcomicPoolExample ex = new HcomicPoolExample();
        List<HcomicPool> hcList = hcomicPoolMapper.selectByExample(ex);
        for (HcomicPool hc : hcList) {
            System.out.println(ToStringBuilder.reflectionToString(hc));
        }
    }

    @Test
    public void testChechMoeTitle() {
        List<MoePool> all = moePoolMapper.selectByExample(new MoePoolExample()).stream().filter(o -> StringUtils.isNotBlank(o.getTitle2())).collect(Collectors.toList());
        System.out.println("count = " + all.size() + "    ====================================================");
        ListMultimap<String, MoePool> listMap = MultimapBuilder.ListMultimapBuilder.hashKeys().arrayListValues().build();
        for (MoePool moePool : all) {
            if (!MoeCatch.確認要使用title1(moePool.getTitle1(), moePool.getTitle2())) {
                //System.out.println(ToStringBuilder.reflectionToString(moePool));
                listMap.put(moePool.getTitle2(), moePool);
            }
        }
        for (Map.Entry<String, Collection<MoePool>> entry : listMap.asMap().entrySet()) {
            if (entry.getValue().size() > 0) {
                System.out.println(entry.getValue().size());
                System.out.println(entry.getValue().iterator().next().getTitle2());
            }
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
