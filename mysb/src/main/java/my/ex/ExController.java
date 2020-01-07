package my.ex;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import dao.ExPoolMapper;
import dao.domain.ExPool;
import dao.domain.ExPoolExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Controller
public class ExController {

    enum Site {
        e, ex;

        public String toString() {
            if (this == e) {
                return "https://e-hentai.org";
            }
            if (this == ex) {
                return "https://exhentai.org";
            }
            return "";
        }
    }

    @Autowired
    private ExPoolMapper exPoolMapper;

    @RequestMapping(value = "ex/index", method = RequestMethod.POST)
    @ResponseBody
    public String test1(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ExPoolExample ex = new ExPoolExample();
        ex.setOrderByClause("exid desc");
        ex.createCriteria().andLookedEqualTo(0);
        List<ExPool> list = exPoolMapper.selectByExample(ex);

//		假如是e站就替換掉url: {
//			returnJsp = "/jsp/e.jsp";
//			for (HashMap m : list) {
//				String url = (String) m.get("url");
//				url = url.replaceFirst(Pattern.quote(Site.ex.toString()), Site.e.toString());
//				m.put("url", url);
//			}
//		}

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter wr = mapper.writer();
        String s = wr.writeValueAsString(list);
//        request.setAttribute("test", s);
//        request.setAttribute("count", list.size());
        return s;
    }

    @RequestMapping(value = "ex/update", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String test2(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ExPool exPool = exPoolMapper.selectByPrimaryKey(Integer.parseInt(request.getParameter("exid")));
        if (exPool != null) {
            String action = request.getParameter("action");
            exPool.setLooked(Integer.parseInt(action));
            exPoolMapper.updateByPrimaryKey(exPool);
            ObjectMapper mapper = new ObjectMapper();
            ObjectWriter wr = mapper.writer();
            return wr.writeValueAsString(exPool);
        }
        return "{}";
    }

}
