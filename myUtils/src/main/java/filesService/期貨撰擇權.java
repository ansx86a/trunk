package filesService;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;

public class 期貨撰擇權 {

	public static void main(String[] args) throws IOException {
		期貨撰擇權 a = new 期貨撰擇權();
		a.整理選擇權io(new File("z:/2"));
	}

	public void 整理期貨io(File f) throws IOException {
		System.out.println("gogogo");
		for (File subf : f.listFiles()) {
			if (!subf.getName().endsWith(".csv")) {
				continue;
			}
			String text = FileUtils.readFileToString(subf, "big5");
			// 小台MTX，台指TX
			// 交易日期 商品代號 到期月份(週別) 成交時間 成交價格 成交數量(B+S) 近月價格 遠月價格 開盤集合競價
			List<String> m = Arrays.asList(text.split("\r\n")).stream()
					.filter(o -> o.indexOf(",MTX ") > 0 && o.indexOf("W") > 0).collect(Collectors.toList());
			System.out.println(m.size());
			File newFile = f.toPath().resolve("小台週").resolve(subf.getName()).toFile();
			FileUtils.writeLines(newFile, m);
		}
	}

	public void 整理選擇權io(File f) throws IOException {
		System.out.println("gogogo");
		for (File subf : f.listFiles()) {
			if (!subf.getName().endsWith(".csv")) {
				continue;
			}
			String text = FileUtils.readFileToString(subf, "big5");
			text = text.replaceAll(" ", "");
			// 小台MTX，台指TX
			// 交易日期 商品代號 履約價格 到期月份(週別) 買賣權別 成交時間 成交價格 成交數量(B or S) 開盤集合競價

			List<String> m = Arrays.asList(text.split("\r\n")).stream().filter(o -> {
				if (o.indexOf(",TXO,") < 0 || o.indexOf("W") < 0) {
					return false;
				}
				double  d=Double.parseDouble( o.split(",")[6]);
				if(d>=150||d<=5){
					return false;
				}
				
				return true;
			}).collect(Collectors.toList());
			System.out.println(m.size());
			File newFile = f.toPath().resolve("小台選").resolve(subf.getName()).toFile();
			FileUtils.writeLines(newFile, m);
		}
	}

}
