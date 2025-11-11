# Repository Guidelines

## Project Structure & Module Organization
-  contains the IntelliJ language integration (file type, language, highlighters).
-  registers components; keep other assets (TextMate bundles, icons) beside .
- Gradle build logic lives in , with release metadata in  and CI/static analysis configs in  and .
- Generated artifacts accumulate under ; never edit files there manually or commit them.

## Build, Test, and Development Commands
-  compiles, runs checks, and assembles the distributable ZIP.
-  starts a sandboxed IntelliJ IDEA 2023.3 with the plugin installed for manual verification.
-  runs the JetBrains Plugin Verifier against the  matrix.
-  (or the Qodana IDE run configuration) executes static analysis tied to .
-  signs and uploads; reserve it for release automation after secrets are configured.

## Coding Style & Naming Conventions
- Follow JetBrains defaults: 4-space indentation, braces on new lines for Java, and  types ().
- Use  members/methods and  constants; keep packages under .
- Place related resources next to their code (e.g., keep TextMate grammars under ). Run IDE formatting before committing.

## Testing Guidelines
- Locate unit and platform tests in  (or ) using JUnit plus the IntelliJ Platform test framework; suffix files with .
- Prefer focused tests that cover PSI helpers, lexer rules, and syntax-highlighting paths. Extend  when IDE fixtures are required.
- Run  locally and regenerate coverage with  so Kover and Codecov stay green.

## Commit & Pull Request Guidelines
- Write imperative, scope-first commit messages similar to ; reference issues in the body ().
- Keep one logical change per commit. PRs should add a clear summary, verification notes (, ), and screenshots/gifs for UI changes.
- Wait for CI (build, Qodana, Codecov) to finish before requesting review, and update  when behavior changes.

## Security & Configuration Tips
- Never commit secrets. Publishing and signing rely on , , , and  environment variables.
- When adjusting , keep  aligned with the IDEA baseline and ensure  +  stay in sync.
