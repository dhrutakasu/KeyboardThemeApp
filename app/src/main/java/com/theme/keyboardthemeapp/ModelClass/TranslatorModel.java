package com.theme.keyboardthemeapp.ModelClass;

public class TranslatorModel {
    int id;
    String InputStr,OutputStr;

    public TranslatorModel(String inputStr, String outputStr) {
        InputStr = inputStr;
        OutputStr = outputStr;
    }

    public TranslatorModel(int id, String inputStr, String outputStr) {
        this.id = id;
        InputStr = inputStr;
        OutputStr = outputStr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInputStr() {
        return InputStr;
    }

    public void setInputStr(String inputStr) {
        InputStr = inputStr;
    }

    public String getOutputStr() {
        return OutputStr;
    }

    public void setOutputStr(String outputStr) {
        OutputStr = outputStr;
    }

    @Override
    public String toString() {
        return "TranslatorModel{" +
                "id=" + id +
                ", InputStr='" + InputStr + '\'' +
                ", OutputStr='" + OutputStr + '\'' +
                '}';
    }
}
