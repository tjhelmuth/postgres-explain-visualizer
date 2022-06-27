package com.tjhelmuth;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;

public class ExplainWindowService {
    private static final Logger log = Logger.getInstance(QueryExecutionListener.class);

    private final Project project;

    public ExplainWindowService(Project project) {
        this.project = project;
    }

    public ExplainWindow createWindow(String plan){
        return new ExplainWindow(project, plan);
    }
}
