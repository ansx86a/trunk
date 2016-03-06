package ui.ieOk動作;

import java.io.File;
import java.net.URISyntaxException;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;

import filesService.目錄rename;

public class Dlsite讀完命名事件 extends LoadAdapter {
	private File sourceFile;
	private Browser br;
	private String rj;
	private Cv處理 cv處理 = new Cv處理();
	private Function<String, String> cvf;

	public static void main(String arg[]) throws URISyntaxException {

	}

	public Dlsite讀完命名事件(File f, String rj, Function<String, String> cvf) {
		sourceFile = f;
		this.rj = rj;
		this.cvf = cvf;
	}

	@Override
	public void onFinishLoadingFrame(FinishLoadingEvent event) {
		if (!event.isMainFrame()) {
			// event.getBrowser().executeJavaScript("alert('xxxx');");
			return;
		}
		br = event.getBrowser();
		br.executeJavaScript(getScript1());
		// 做好介接物件注入到橋接中
		JSValue document = br.executeJavaScriptAndReturnValue("document");
		String result = "";
		try {
			JSValue jvHtml = document.asObject().getProperty("toJavaValueHtml");
			JSValue jvWorkName = document.asObject().getProperty("toJavaValueWorkName");
			JSValue jvMakerName = document.asObject().getProperty("toJavaValueMakerName");
			String html = jvHtml.getStringValue();
			html = html.replaceAll(" ", "").replaceAll("　", "");
			String titleName = jvWorkName.getStringValue();
			String makerName = jvMakerName.getStringValue();
			String cv = cv處理.getCvNames(html);
			String allAge = "";
			if (br.getURL().contains("home")) {
				allAge = "[一般]";
			}
			if (StringUtils.trimToNull(titleName) == null) {
				System.out.println("找不到rj");
				throw new Exception("blank titleName");
			}

			result = "[" + makerName + "]" + allAge + titleName + "(" + cv + ")" + rj;
			result = result.replaceAll("【ポイント.{3}還元】", "");
			br.executeJavaScript("$(\"#work_name\").text('" + result + "');");

			System.out.println(result);
			// 要蓋掉不可命名的字元 /\*|?<>
			result = StringUtils.replaceEach(result, new String[] { "/", "\\", "*", "|", "?", "<", ">", ":", "\"" },
					new String[] { "／", "＼", "＊", "｜", "？", "＜", "＞", "：", "”" });
			System.out.println(result);
			cvf.apply("(" + cv + ")" + rj);
		} catch (Exception ex) {
			// 重試一次，這次要導到一般向
			// http://www.dlsite.com/home/work/=/product_id/RJ139194.html
			// http://www.dlsite.com/maniax/work/=/product_id/%s.html";
			if (!br.getURL().contains("home")) {
				// String url =
				// String.format("http://www.dlsite.com/home/work/=/product_id/%s.html",
				// rj.toUpperCase());
				String url = String.format("http://www.dlsite.com/maniax/work/=/product_id/%s.html", rj.toUpperCase());
				//br.loadURL(url);
				return;
			}
			System.out.println("找不到");
			return;
		}
		// 開始重新命名
		if (sourceFile.isDirectory()) {
			File newFile = sourceFile.toPath().resolveSibling(result).toFile();
			// FileUtils.moveDirectory(sourceFile, newFile);
			System.out.println(sourceFile.getAbsolutePath());
			System.out.println(newFile.getAbsolutePath());
			// Files.move(sourceFile.toPath(), newFile.toPath(),
			// StandardCopyOption.COPY_ATTRIBUTES);
			try {
				目錄rename.目錄搬移(sourceFile, newFile);
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
	}

	public String getScript1() {
		StringBuffer sb = new StringBuffer();
		sb.append("var html = $(\"[class='work_article work_story']\").text();");
		sb.append("html = html.replace(/　/g, \"\").replace(/ /g,\"\");");
		sb.append("document.toJavaValueHtml = html;");
		sb.append("var workName =  $(\"#work_name\").text();");
		sb.append("document.toJavaValueWorkName = workName;");
		sb.append("var makerName = $(\"[class='maker_name']\").first().text();");
		sb.append("document.toJavaValueMakerName = makerName;");
		// sb.append("alert(html);");
		return sb.toString();
	}

}
