package com.tjhelmuth.file;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.openapi.util.NlsSafe;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class PgPlanFileType implements FileType {
    public static final String EXTENSION = "pgplan";
    public static final PgPlanFileType INSTANCE = new PgPlanFileType();

    @Override
    public @NonNls
    @NotNull String getName() {
        return "PGPLAN";
    }

    @Override
    public @NlsContexts.Label
    @NotNull String getDescription() {
        return "Postgres visualizer plugin plan";
    }

    @Override
    public @NlsSafe
    @NotNull String getDefaultExtension() {
        return EXTENSION;
    }

    @Override
    public @Nullable Icon getIcon() {
        return AllIcons.FileTypes.Diagram;
    }

    @Override
    public boolean isBinary() {
        return true;
    }
}
