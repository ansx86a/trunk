package ui.ieOk動作;

import java.util.Arrays;
import java.util.HashSet;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;

public class 愛看漫讀完漫畫頁事件 extends LoadAdapter {
	private Browser br;
	private boolean isnext = false;
	private HashSet<String> set = new HashSet<>();

	@Override
	public void onFinishLoadingFrame(FinishLoadingEvent event) {
		// System.out.println("isMainFrame:"+event.isMainFrame());
		// if (!event.isMainFrame()) {
		// // event.getBrowser().executeJavaScript("alert('xxxx');");
		// return;
		// }

		br = event.getBrowser();
		br.executeJavaScript(getScript1());
		JSValue document = br.executeJavaScriptAndReturnValue("document");
		try {
			JSValue jvHtml = document.asObject().getProperty("toJavaValueHtml");
			JSValue jvPage = document.asObject().getProperty("toJavaValuePage");
			// this.isnext = true;
			if (jvHtml.getStringValue().length() > 0) {
				String img = jvHtml.getStringValue();
				int size = set.size();
				set.add(img);
				if(set.size()>size){
					System.out.println(img);
					String pages = jvPage.getStringValue().replaceAll("\\(", "").replaceAll("\\)", "");
					String[] pageArray = pages.split("/");
					System.out.println(Arrays.toString(pageArray));
					if(!pageArray[0].equals(pageArray[1])){
						br.executeJavaScript(nextScript1());	
					}else{
						System.out.println(set);
					}
					
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public String getScript1() {
		StringBuffer sb = new StringBuffer();
		//圖檔
		sb.append("var html = $(\"#mangaFile\").attr(\"src\");");
		sb.append("document.toJavaValueHtml = html;");
		//取得頁數(xx/total)
		sb.append("var nowpage = $(\"#page\").parent().text();");
		sb.append("document.toJavaValuePage = nowpage;");
		return sb.toString();
	}

	public String nextScript1() {
		StringBuffer sb = new StringBuffer();
		sb.append("$(\"#mangaFile\").click();");
		return sb.toString();
	}

	
	public boolean getIsnext() {
		return isnext;
	}

	public void setIsnext(boolean isnext) {
		this.isnext = isnext;
	}

}
