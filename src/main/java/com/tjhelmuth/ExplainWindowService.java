package com.tjhelmuth;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.project.Project;
import com.tjhelmuth.file.PgPlanVirtualFile;

public class ExplainWindowService {
    public ExplainWindow createWindow(String plan, ExplainContext context) {
        return new ExplainWindow(plan, context);
    }
}
