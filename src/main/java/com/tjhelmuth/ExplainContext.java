package com.tjhelmuth;

import com.intellij.database.console.JdbcConsole;
import com.tjhelmuth.file.PgPlanEditor;
import com.tjhelmuth.file.PgPlanVirtualFile;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.jetbrains.annotations.NotNull;

@Value @AllArgsConstructor
public class ExplainContext {
    @NotNull PgPlanVirtualFile file;
    @NotNull PgPlanEditor editor;

    @NotNull JdbcConsole console;

    /**
     * Did we actually execute the query... explain + analyze
     */
    public boolean didExecute(){
        return file.isExecuted();
    }
}
