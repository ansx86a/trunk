package hook;

import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 * 由function來delgate熱鍵
 * @author rockx
 *
 */
public class KeyHook建置器 implements NativeKeyListener {

	private Function<Object, Object> f;
	private int code;

	public KeyHook建置器(int code, Function<Object, Object> f) {
		this.f = f;
		this.code = code;
	}

	public static void 加入一個hook(int code, Function<Object, Object> f) {
		KeyHook建置器 key = new KeyHook建置器(code, f);
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException e) {
			e.printStackTrace();
		}
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.WARNING);
		GlobalScreen.addNativeKeyListener(key);
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent arg0) {

	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent arg0) {
		System.out.println(ToStringBuilder.reflectionToString(arg0));
		if (arg0.getRawCode() == this.code) {
			f.apply(null);
		}
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent arg0) {

	}

}
