package clientServer;

import java.util.HashMap;

public interface Irmi {

	public String sayName(String name);

	public int sayHowMach(int a, int b);

	public HashMap masterMethod(HashMap map);

	public String cookiesHttp(String url) throws Exception;

}
