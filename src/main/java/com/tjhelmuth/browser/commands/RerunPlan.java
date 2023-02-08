package com.tjhelmuth.browser.commands;

import com.intellij.openapi.actionSystem.ActionManager;
import com.tjhelmuth.ExplainAnalyzeVisualizerAction;
import com.tjhelmuth.ExplainContext;
import com.tjhelmuth.ExplainWindow;
import com.tjhelmuth.PlanProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cef.network.CefRequest;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class RerunPlan implements SimpleCommandHandler<Void> {
    private final ExplainWindow window;

    @NotNull
    @Override
    public CommandResult<Void> execute(CefRequest request) throws IOException {
        log.info("Refreshing plan");

        ExplainContext context = window.getContext();
        new ExplainAnalyzeVisualizerAction().updateExisting(context.getConsole(), context.getFile());

        return CommandResult.emptyHandled();
    }

    @Override
    public boolean isApplicable(CefRequest request) {
        return request.getURL().contains("myapp/rerun");
    }
}
