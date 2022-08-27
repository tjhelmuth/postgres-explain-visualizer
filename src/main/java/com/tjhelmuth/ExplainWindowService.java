package com.tjhelmuth;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.tjhelmuth.file.PgPlanVirtualFile;

public class ExplainWindowService {
    private static final Logger log = Logger.getInstance(QueryExecutionListener.class);

    private final Project project;

    public ExplainWindowService(Project project) {
        this.project = project;
    }

    public ExplainWindow createWindow(String plan, PgPlanVirtualFile file) {
        return new ExplainWindow(project, plan, file.isExecuted());
    }
}
