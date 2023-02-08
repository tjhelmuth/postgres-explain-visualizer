package com.tjhelmuth.file;

import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorLocation;
import com.intellij.openapi.fileEditor.FileEditorState;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.UserDataHolderBase;
import com.intellij.openapi.vfs.VirtualFile;
import com.tjhelmuth.ExplainContext;
import com.tjhelmuth.ExplainWindow;
import com.tjhelmuth.ExplainWindowService;
import lombok.Getter;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.beans.PropertyChangeListener;

/**
 * This is the editor that contains our explain diagram.
 */
public class PgPlanEditor extends UserDataHolderBase implements FileEditor {
    private final Project project;
    private final VirtualFile file;

    @Getter
    private ExplainWindow explainWindow;

    public PgPlanEditor(Project project, VirtualFile file){
        this.project = project;
        this.file = file;
    }

    @Override
    public @NotNull JComponent getComponent() {
        if(this.explainWindow != null){
            return this.explainWindow.getContent();
        }

        PgPlanVirtualFile planFile = (PgPlanVirtualFile) file;

        ExplainWindowService service = project.getService(ExplainWindowService.class);
        this.explainWindow = service.createWindow(planFile.getContent().toString(), new ExplainContext(planFile, this, planFile.getConsole()));
        return explainWindow.getContent();
    }

    @Override
    public @Nullable JComponent getPreferredFocusedComponent() {
        return this.getComponent();
    }

    @Override
    public @Nls(capitalization = Nls.Capitalization.Title)
    @NotNull String getName() {
        return file.getName();
    }

    @Override
    public void setState(@NotNull FileEditorState fileEditorState) {

    }

    @Override
    public boolean isModified() {
        return false;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void addPropertyChangeListener(@NotNull PropertyChangeListener propertyChangeListener) {

    }

    @Override
    public void removePropertyChangeListener(@NotNull PropertyChangeListener propertyChangeListener) {

    }

    @Override
    public @Nullable FileEditorLocation getCurrentLocation() {
        return null;
    }

    @Override
    public @Nullable VirtualFile getFile() {
        return file;
    }

    @Override
    public void dispose() {

    }
}
