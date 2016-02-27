import java.util.function.Function;

public class TestFunction {

	public static void main(String[] args) {
		
		
		//這就等於是delgateGate了，強制定義了前面是傳入，後面是回傳兩個參數
		//只要把function帶過去，就可以透過function.apply(obj)來做到delgate的功用了
		//當然這樣做會不會造成很難gc，還有delgate的funtion中用到的區域變數要宣告成final才不會出問題
		//估計是java會把區域變數的值copy一份備份來用，而且永遠不變
		//delgate的細節以後再來想
		Function<String, String> f = ttt();
		f.apply("ddd");
	}

	public static Function<String, String> ttt() {
		final String k = "kkk";
		Function<String, String> f = printString -> {
			System.out.println(printString);
			System.out.println(k);
			return null;
		};
		// f.apply("sss");

		return f;
	}

}
