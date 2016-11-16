package clientServer;

import java.util.HashMap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RMIClient {

	private static Irmi client = null;

	public static void main(String[] args) throws Exception {

		Irmi service = init();
		// System.out.println(service.sayName("Kkmanzz"));
		// System.out.println(service.sayHowMach(100, 37));
		String result = service.cookiesHttp("https://exhentai.org/s/b948193c64/972830-10");
		System.out.println(result);
	}

	public static Irmi init() {
		ApplicationContext context = new ClassPathXmlApplicationContext("clientServer/RMIClient.xml");

		Irmi service = (Irmi) context.getBean("rmiServiceProxy");
		return service;
	}

	public static Irmi get() {
		if (client != null) {
			return client;
		} else {
			client = init();
			return client;
		}
	}

}
