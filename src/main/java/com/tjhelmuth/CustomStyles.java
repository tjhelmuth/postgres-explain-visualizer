package com.tjhelmuth;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public enum CustomStyles {
    INSTANCE;

    private final Color editorBackground;
    private final Color editorForeground;
    private final Color textColor;

    private final String uiPanelSelector = ".splitpanes.default-theme .splitpanes__pane";
    private final String uselessHeaderSelector = ".nav.nav-pills";
    private final String statsHeaderSelector = ".plan-stats";
    private final String borderBottomSelector = ".border-bottom";
    private final String borderTopSelector = ".border-top";
    private final String nodeTypeSelector = ".node-type";
    private final String subPlanSelector = ".subplan";

    CustomStyles(){
        this.editorBackground = UIManager.getColor("EditorPane.background");
        this.editorForeground = UIManager.getColor("EditorPane.foreground");
        this.textColor = UIManager.getColor("Tree.textForeground");
    }

    public String generateCssBlock(){
        StringBuilder sb = new StringBuilder();
        sb.append("<style>");
        sb.append(block(uiPanelSelector, "background-color", colorStr(editorBackground)));
        sb.append(block(statsHeaderSelector, "background-color", colorStr(editorForeground)));
        sb.append(block(uselessHeaderSelector, "display", "none"));
        sb.append(block(nodeTypeSelector, "color", colorStr(this.textColor)));
        sb.append(block(subPlanSelector, "color", colorStr(this.textColor)));
        sb.append("</style>");
        return sb.toString();
    }

    private String colorStr(Color color){
        return String.format("rgb(%s, %s, %s)", color.getRed(), color.getBlue(), color.getGreen());
    }

    private String block(String selector, String key, String value){
        StringBuilder sb = new StringBuilder();
        sb.append(selector).append(" {");
        sb.append(key).append(": ").append(value).append(";").append(System.lineSeparator());
        sb.append("}").append(System.lineSeparator());
        return sb.toString();
    }


}
