# Repository Guidelines

## Project Structure & Module Organization
- `src/main/kotlin/com/jidder` hosts the Kotlin sources for language definitions, TextMate bundle wiring, and icons.
- `src/main/resources/META-INF/plugin.xml` registers the file type, TextMate highlighter, and bundle provider; keep related resources under `src/main/resources`.
- TextMate assets (grammar, language configuration, package metadata) live under `src/main/resources/textmate/jidder/**` and are consumed by `JidderTextMateBundleProvider`.
- Shared icons, including `pluginIcon.svg`, live under `src/main/resources/icons`.
- Build logic is in `build.gradle.kts`, `settings.gradle.kts`, and `gradle.properties`. Keep `pluginSinceBuild` (243) aligned with the IntelliJ 2024.3 baseline and sync `pluginVersion` with `CHANGELOG.md`.
- The GitHub Actions workflow at `.github/workflows/build.yml` runs `buildPlugin` and `verifyPlugin`. Generated artifacts land in `build/`; never edit or commit files in that directory.

## Build, Test, and Development Commands
- `./gradlew buildPlugin` compiles sources, runs tests, and assembles the distributable ZIP (this is what CI calls).
- `./gradlew test` executes unit/integration tests; include it in local verification before opening a PR.
- `./gradlew runIde` launches a sandboxed IntelliJ IDEA IC 2024.3.6 configured via `gradle.properties` for manual verification.
- `./gradlew verifyPlugin` runs the JetBrains Plugin Verifier against the recommended IDE set declared in `build.gradle.kts`.
- `./gradlew publishPlugin` signs and uploads the plugin; reserve it for release automation once secrets are configured.
- `./gradlew wrapper --gradle-version <x>` should be the only way to bump Gradle; avoid editing `gradlew*` scripts manually.

## Coding Style & Naming Conventions
- Kotlin is the primary language; follow JetBrains defaults (4-space indentation, braces on new lines when multi-line) and keep packages under `com.jidder.*`.
- Use `PascalCase` for types/objects (e.g., `JidderTextMateBundleProvider`), `camelCase` for members/methods, and `UPPER_SNAKE_CASE` for constants.
- Co-locate resources with their code (e.g., TextMate grammars under `src/main/resources/textmate/jidder`, icons under `src/main/resources/icons`) and run IDE formatting before committing.

## Commit & Pull Request Guidelines
- Write imperative, scope-first commit messages like `Add TextMate grammar for choices`, referencing issues in the body (`Refs #12`) when applicable.
- Keep one logical change per commit. PRs should summarize behavior changes, list verification commands (`./gradlew buildPlugin`, `./gradlew test`, `./gradlew runIde`), and include screenshots/gifs for UI updates.
- Wait for the GitHub Actions `Build` workflow (build + verify jobs) to finish before requesting review, and update `CHANGELOG.md` whenever behavior changes.

## Security & Configuration Tips
- Never commit secrets. Publishing and signing rely on the `CERTIFICATE_CHAIN`, `PRIVATE_KEY`, `PRIVATE_KEY_PASSWORD`, and `PUBLISH_TOKEN` environment variables.
- When editing `gradle.properties`, ensure `platformType`, `platformVersion`, `pluginSinceBuild`, and `pluginVersion` stay aligned with the intended IntelliJ baseline and the release notes.
