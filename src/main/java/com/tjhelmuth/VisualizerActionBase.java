package com.tjhelmuth;

import com.intellij.database.actions.ExplainActionBase;
import com.intellij.database.console.JdbcConsole;
import com.intellij.database.datagrid.DataRequest;
import com.intellij.database.plan.ExplainPlanProvider;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.util.ui.UIUtil;
import com.tjhelmuth.file.PgPlanEditor;
import com.tjhelmuth.file.PgPlanVirtualFile;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.UUID;

@Slf4j
public abstract class VisualizerActionBase extends ExplainActionBase.Ui {
    private final ExplainPlanProvider planProvider = new PlanProvider();

    public VisualizerActionBase(boolean run){
        super(run);
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

    /**
     *  Refresh an existing explain plan that we have an id for
     */
    public void updateExisting(@NotNull JdbcConsole console, PgPlanVirtualFile existingFile){
        this.doExplain(console, existingFile.getQuery(), existingFile);
    }

    @Override
    protected void explainStatement(@NotNull ExplainPlanProvider provider, @NotNull JdbcConsole console, @NotNull String statement) {
        //intentionally ignoring provider so we can use our own
        this.doExplain(console, statement, null);
    }

    private void doExplain(@NotNull JdbcConsole console, @NotNull String statement, PgPlanVirtualFile existingFile){
        DataRequest.RawRequest request = planProvider.createExplainRequest(console, (model) -> {
            if(model instanceof PgPlanModel){

                ApplicationManager.getApplication().invokeLater(() -> {
                    PgPlanModel mdl = (PgPlanModel) model;

                    PgPlanVirtualFile file = existingFile == null
                            ? new PgPlanVirtualFile(getFileName(statement), mdl.getJson(), myRun, console, statement)
                            : existingFile;

                    //we will either open the file for the first time, or open it in the existing editor
                    FileEditor[] editors = FileEditorManager.getInstance(console.getProject()).openFile(file, true);
                    if(existingFile != null){
                        Arrays.stream(editors).filter(e -> e instanceof PgPlanEditor).forEach(e -> {
                            PgPlanEditor editor = (PgPlanEditor) e;
                            editor.getExplainWindow().updatePlan(mdl.getJson());
                        });
                    }
                });
            }
        }, console.getDataSource(), statement, this.myRun);

        if (request != null) {
            console.getMessageBus().getDataProducer().processRequest(request);
        }
    }

    private String getFileName(String statement){
        return String.format("Explain: %s", StringUtils.substring(statement, 0, 6));
    }
}
