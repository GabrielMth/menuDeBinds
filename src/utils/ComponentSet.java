package utils;

import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ComponentSet {
    private JTextArea textArea;
    private JCheckBox checkBox;
    private JTextField textField;

    public ComponentSet(JTextArea textArea, JCheckBox checkBox, JTextField textField) {
        this.textArea = textArea;
        this.checkBox = checkBox;
        this.textField = textField;
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public JCheckBox getCheckBox() {
        return checkBox;
    }

    public JTextField getTextField() {
        return textField;
    }
}
