package com.tjhelmuth;

import com.intellij.database.actions.RunQueryAction;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.AnActionResult;
import com.intellij.openapi.actionSystem.ex.AnActionListener;
import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.NotNull;

public class RunQueryActionListener implements AnActionListener {
    private static final Logger log = Logger.getInstance(RunQueryActionListener.class);

    @Override
    public void afterActionPerformed(@NotNull AnAction action, @NotNull AnActionEvent event, @NotNull AnActionResult result) {
        if(action instanceof RunQueryAction){
            RunQueryAction queryAction = (RunQueryAction) action;
            log.info("Running query");
        }

        String message = String.format("ACTION PERFORMED %s -- %s : %s", action, event.getClass(), result.getClass());
        log.info(message);
    }
}
