package utils;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.*;
import com.github.kwhat.jnativehook.NativeHookException;

public class GlobalKeyListener implements NativeKeyListener {

	private static final Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
	private static Map<JTextField, ComponentSet> componentMap = new HashMap<>();
	private static GlobalKeyListener instance;

	public GlobalKeyListener() {
	}

	public static void registerHook(JTextField textField, JTextArea novaScriptArea, JCheckBox checkBox) {

		if (textField.getText().trim().isEmpty()) {
			return;
		}

		ComponentSet components = new ComponentSet(novaScriptArea, checkBox, textField);
		componentMap.put(textField, components);

		try {
			if (!GlobalScreen.isNativeHookRegistered()) {
				GlobalScreen.registerNativeHook();
			}
		} catch (NativeHookException ex) {
			logger.log(Level.SEVERE, "Erro ao registrar a tecla como nativa para bindar.", ex);
		}

		if (instance == null) {
			instance = new GlobalKeyListener();
			GlobalScreen.addNativeKeyListener(instance);
		}
	}

	public static void unregisterHook(JTextField textField) {
		componentMap.remove(textField);

		if (componentMap.isEmpty() && instance != null) {
			GlobalScreen.removeNativeKeyListener(instance);
			try {
				GlobalScreen.unregisterNativeHook();
			} catch (NativeHookException ex) {
				logger.log(Level.SEVERE, "Erro ao desregistrar a tecla nativa.", ex);
			}
			instance = null;
		}
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		for (ComponentSet set : componentMap.values()) {
			JTextArea scriptArea = set.getTextArea();
			JCheckBox checkBox = set.getCheckBox();
			JTextField textField = set.getTextField();

			String key = textField.getText();
			if (key != null && !key.isEmpty() && NativeKeyEvent.getKeyText(e.getKeyCode()).equalsIgnoreCase(key)) {
				if (checkBox.isSelected()) {
					String message = scriptArea.getText();
					StringSelection selection = new StringSelection(message);
					Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
					clipboard.setContents(selection, selection);

					try {
						Robot robot = new Robot();
						robot.keyPress(KeyEvent.VK_CONTROL);
						robot.keyPress(KeyEvent.VK_V);
						robot.keyRelease(KeyEvent.VK_V);
						robot.keyRelease(KeyEvent.VK_CONTROL);
					} catch (AWTException awtException) {
						awtException.printStackTrace();
					}
				}
			}
		}
	}
}
