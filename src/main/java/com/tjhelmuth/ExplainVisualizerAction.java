package com.tjhelmuth;

import com.intellij.database.actions.ExplainActionBase;
import com.intellij.database.console.JdbcConsole;
import com.intellij.database.datagrid.DataRequest;
import com.intellij.database.plan.ExplainPlanProvider;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.tjhelmuth.file.PgPlanVirtualFile;
import org.jetbrains.annotations.NotNull;

public class ExplainVisualizerAction extends ExplainActionBase.Ui {
    private ExplainPlanProvider planProvider = new PlanProvider();

    public ExplainVisualizerAction(){
        super(true);
    }

    @Override
    public void setDefaultIcon(boolean isDefaultIconSet) {
        super.setDefaultIcon(isDefaultIconSet);
    }

    @Override
    protected boolean isSupported(AnActionEvent e) {
        JdbcConsole console = getConsole(e);
        ExplainPlanProvider provider = console != null ? ExplainPlanProvider.getDefaultProvider(console.getDataSource()) : null;
        return provider != null && console.getDataSource().getDbms().isPostgres() && provider.isSupported(console.getDataSource().getVersion(), this.myRun);
    }

    @Override
    protected void explainStatement(@NotNull ExplainPlanProvider provider, @NotNull JdbcConsole console, @NotNull String statement) {
        //intentionally ignoring provider;

        DataRequest.RawRequest request = planProvider.createExplainRequest(console, (model) -> {
            if(model instanceof PgPlanModel){
                ApplicationManager.getApplication().invokeLater(() -> {
                    PgPlanModel mdl = (PgPlanModel) model;
                    PgPlanVirtualFile file = new PgPlanVirtualFile("test", mdl.getJson());
                    FileEditor[] editors = FileEditorManager.getInstance(console.getProject()).openFile(file, true);
                });
            }
        }, console.getDataSource(), statement, this.myRun);

        if (request != null) {
            console.getMessageBus().getDataProducer().processRequest(request);
        }
    }
}
