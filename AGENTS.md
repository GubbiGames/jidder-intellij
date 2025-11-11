# Repository Guidelines

## Project Structure & Module Organization
- `src/main/java/com/jidder` contains the IntelliJ language integration (file type, language, highlighters).
- `src/main/resources/META-INF/plugin.xml` registers components; keep auxiliary assets (TextMate bundles, icons) beside `src/main/resources/bundles`.
- Gradle logic lives in `build.gradle.kts`, with release metadata in `gradle.properties` and CI/static analysis configs in `qodana.yml` and `codecov.yml`.
- Generated artifacts land in `build/`; do not edit or commit anything under that directory.

## Build, Test, and Development Commands
- `./gradlew build` compiles sources, runs checks, and assembles the distributable ZIP.
- `./gradlew runIde` starts a sandboxed IntelliJ IDEA 2023.3 with the plugin installed for manual verification.
- `./gradlew verifyPlugin` runs the JetBrains Plugin Verifier against the configured `platformVersion` matrix.
- `./gradlew qodanaScan` (or the IDE Qodana run configuration) executes static analysis defined in `qodana.yml`.
- `./gradlew publishPlugin` signs and uploads; reserve it for release automation after secrets are configured.

## Coding Style & Naming Conventions
- Follow JetBrains defaults: 4-space indentation, braces on new lines for Java, and `PascalCase` types such as `JidderSyntaxHighlighterFactory`.
- Use `camelCase` members/methods and `UPPER_SNAKE_CASE` constants; keep packages under `com.jidder.*`.
- Place related resources next to their code (for example TextMate grammars under `src/main/resources/bundles`) and run IDE formatting before committing.

## Testing Guidelines
- Locate unit and platform tests in `src/test/java` (or `.../kotlin`) using JUnit plus the IntelliJ Platform test framework; suffix files with `*Test`.
- Prefer focused tests that cover PSI helpers, lexer rules, and syntax-highlighting flows; extend `PlatformTestCase` when IDE fixtures are required.
- Run `./gradlew test` locally and refresh coverage with `./gradlew koverHtmlReport` so Kover and Codecov stay green.

## Commit & Pull Request Guidelines
- Write imperative, scope-first commit messages similar to `Add Jidder TextMate syntax highlighting plugin`; reference issues in the body (`Refs #12`).
- Keep one logical change per commit. PRs should include a clear summary, verification notes (`./gradlew test`, `./gradlew runIde`), and screenshots/gifs for UI changes.
- Wait for CI (build, Qodana, Codecov) to finish before requesting review, and update `CHANGELOG.md` when behavior changes.

## Security & Configuration Tips
- Never commit secrets. Publishing and signing rely on `CERTIFICATE_CHAIN`, `PRIVATE_KEY`, `PRIVATE_KEY_PASSWORD`, and `PUBLISH_TOKEN` environment variables.
- When adjusting `gradle.properties`, keep `pluginSinceBuild` aligned with the IDEA baseline and ensure `pluginVersion` plus `CHANGELOG.md` stay in sync.
