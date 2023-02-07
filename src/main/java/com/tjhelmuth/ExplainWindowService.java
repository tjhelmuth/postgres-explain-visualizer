package com.tjhelmuth;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.project.Project;
import com.tjhelmuth.file.PgPlanVirtualFile;

public class ExplainWindowService {
    private static final Logger log = Logger.getInstance(QueryExecutionListener.class);


    public ExplainWindowService() {}

    public ExplainWindow createWindow(String plan, PgPlanVirtualFile file, Disposable parent) {
        return new ExplainWindow(plan, file.isExecuted(), parent);
    }
}
