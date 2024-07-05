package utils;

import java.io.Serializable;

public class ComponentState implements Serializable {
    private static final long serialVersionUID = 1L;

    private String scriptAreaText;
    private boolean checkBoxSelected;
    private String textFieldText;

    public ComponentState(String scriptAreaText, boolean checkBoxSelected, String textFieldText) {
        this.scriptAreaText = scriptAreaText;
        this.checkBoxSelected = checkBoxSelected;
        this.textFieldText = textFieldText;
    }

    public String getScriptAreaText() {
        return scriptAreaText;
    }

    public void setScriptAreaText(String scriptAreaText) {
        this.scriptAreaText = scriptAreaText;
    }

    public boolean isCheckBoxSelected() {
        return checkBoxSelected;
    }

    public void setCheckBoxSelected(boolean checkBoxSelected) {
        this.checkBoxSelected = checkBoxSelected;
    }

    public String getTextFieldText() {
        return textFieldText;
    }

    public void setTextFieldText(String textFieldText) {
        this.textFieldText = textFieldText;
    }
}
