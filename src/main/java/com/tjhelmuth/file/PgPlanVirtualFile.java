package com.tjhelmuth.file;

import com.intellij.database.console.JdbcConsole;
import com.intellij.testFramework.LightVirtualFile;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Getter
public class PgPlanVirtualFile extends LightVirtualFile {
    //did we actually run the query with analyze
    private final boolean executed;

    private final UUID explainId = UUID.randomUUID();

    //console that executed this explain query
    private final JdbcConsole console;

    private final String query;

    public PgPlanVirtualFile(@NotNull String name, @NotNull String planText, boolean executed, JdbcConsole console, String query) {
        super(name, PgPlanFileType.INSTANCE, planText);
        this.executed = executed;
        this.console = console;
        this.query = query;
    }
}
