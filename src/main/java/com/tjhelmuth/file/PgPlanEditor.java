package com.tjhelmuth.file;

import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorLocation;
import com.intellij.openapi.fileEditor.FileEditorState;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.UserDataHolderBase;
import com.intellij.openapi.vfs.VirtualFile;
import com.tjhelmuth.ExplainWindow;
import com.tjhelmuth.ExplainWindowService;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.beans.PropertyChangeListener;

public class PgPlanEditor extends UserDataHolderBase implements FileEditor {
    private JComponent component;
    private final Project project;
    private final VirtualFile file;

    public PgPlanEditor(Project project, VirtualFile file){
        this.project = project;
        this.file = file;
    }

    @Override
    public @NotNull JComponent getComponent() {
        if(this.component != null){
            return this.component;
        }

        PgPlanVirtualFile planFile = (PgPlanVirtualFile) file;

        ExplainWindowService service = project.getService(ExplainWindowService.class);
        ExplainWindow window = service.createWindow(planFile.getContent().toString());
        this.component = window.getContent();
        return this.component;
    }

    @Override
    public @Nullable JComponent getPreferredFocusedComponent() {
        return this.component;
    }

    @Override
    public @Nls(capitalization = Nls.Capitalization.Title)
    @NotNull String getName() {
        return null;
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
