package hook;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class Test2 implements NativeKeyListener {

	public static void main(String[] args) throws Exception {
		Test2 t = new Test2();
		GlobalScreen.registerNativeHook();
		GlobalScreen.addNativeKeyListener(t);
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.WARNING);
		// Don't forget to disable the parent handlers.
		/// logger.setUseParentHandlers(false);

		System.out.println("sleep");
		Thread.sleep(10000);
		System.out.println("end");

	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent arg0) {
		// System.out.println(ToStringBuilder.reflectionToString(arg0));
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent arg0) {
		System.out.println("release");
		System.out.println(ToStringBuilder.reflectionToString(arg0));
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent arg0) {
		System.out.println("typed");
		System.out.println(ToStringBuilder.reflectionToString(arg0));
	}

}
