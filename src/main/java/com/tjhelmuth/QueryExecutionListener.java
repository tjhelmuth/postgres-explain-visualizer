package com.tjhelmuth;

import com.intellij.database.psi.DbPsiFacade;
import com.intellij.execution.ExecutionListener;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.NotNull;

public class QueryExecutionListener implements ExecutionListener {
    private static final Logger log = Logger.getInstance(QueryExecutionListener.class);
    private DbPsiFacade facade;

    @Override
    public void processStarted(@NotNull String executorId, @NotNull ExecutionEnvironment env, @NotNull ProcessHandler handler) {
        String message = String.format("EXECUTION STARTED: %s,   %s,   %s", executorId, env, handler);
        log.info(message);
    }
}
