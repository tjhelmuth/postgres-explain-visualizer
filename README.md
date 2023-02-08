# postgres-explain-visualizer

This is an intellij plugin that wraps the functionality of the Dalibo pev2 vue component. https://github.com/dalibo/pev2


The user interface is a vue.js webapp that is embedded through embedded chromium into an editor tab. The vue js app is
built using vite and standard vite commands apply. It's located at src/web

I manually copy the built files from src/web/dist into src/main/resources/webview