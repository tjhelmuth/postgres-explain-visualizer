package com.tjhelmuth.file;

import com.intellij.openapi.util.NlsSafe;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFilePathWrapper;
import com.intellij.openapi.vfs.VirtualFileSystem;
import com.intellij.openapi.vfs.VirtualFileWithoutContent;
import com.intellij.testFramework.LightVirtualFile;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PgPlanVirtualFile extends LightVirtualFile {
    //did we actually run the query with analyze
    private final boolean executed;

    public PgPlanVirtualFile(@NlsSafe @NotNull String name, @NotNull String planText, boolean executed) {
        super(name, PgPlanFileType.INSTANCE, planText);
        this.executed = executed;
    }

    public boolean isExecuted() {
        return executed;
    }
}
