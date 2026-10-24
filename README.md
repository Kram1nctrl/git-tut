# GitTut

**A pocket-sized Git and GitHub school for Android.** W3Schools-style lessons, real commands, quick quizzes, and a searchable cheat sheet — all bundled in the app, so it works completely offline.

[![Android CI](https://github.com/Kram1nctrl/git-tut/actions/workflows/android.yml/badge.svg)](https://github.com/Kram1nctrl/git-tut/actions/workflows/android.yml)

## Features

- **8 chapters, 40 lessons** — from "what is version control" to GitHub Actions, Pages, and security.
- **Copy-ready code blocks** — every command is one tap from your clipboard.
- **Chapter quizzes** — 32 questions with explanations, and your best score is remembered.
- **Progress tracking** — completed lessons are saved locally; continue where you left off.
- **Full-text search** — find any lesson, command, or concept.
- **Cheat sheet** — filterable command reference with copy buttons.
- **Offline first** — no account, no network, no tracking.
- **Material 3** — light and dark themes that follow the system setting.

## Download

Grab the latest APK from the [Releases](https://github.com/Kram1nctrl/git-tut/releases) page.

Requirements: Android 8.0 (API 26) or newer.

## Screens

| Learn | Lesson | Quiz | Cheat sheet |
| --- | --- | --- | --- |
| Chapter progress and a continue button | Markdown lessons with copyable code | Instant feedback and explanations | Filterable command reference |

## Build from source

Prerequisites:

- JDK 17 or newer
- Android SDK with platform 35 and build-tools 35.0.0
- `local.properties` with `sdk.dir=/path/to/Android/sdk`

```bash
./gradlew assembleDebug
```

The APK appears at `app/build/outputs/apk/debug/app-debug.apk`.

For a release build, provide signing values through environment variables or a properties file outside the repository:

```bash
GITTUT_KEYSTORE=/path/to/release.jks \
GITTUT_KEYSTORE_PASSWORD=... \
GITTUT_KEY_ALIAS=gittut \
GITTUT_KEY_PASSWORD=... \
./gradlew assembleRelease
```

## Project structure

```text
app/src/main/
├── assets/content/
│   ├── index.json          # chapters, lesson metadata, quizzes
│   ├── cheatsheet.json     # command reference
│   └── lessons/*.md        # lesson bodies in a small Markdown subset
└── java/com/kram1nctrl/gittut/
    ├── data/               # models, Markdown parser, content loader, progress store
    ├── ui/                 # navigation, screens, theme, block renderer
    └── MainActivity.kt
```

### Content format

Lessons are Markdown files with a deliberately small feature set:

- `##` and `###` headings
- paragraphs, `**bold**`, and `` `inline code` ``
- fenced code blocks with an optional language
- `-` and `1.` lists
- callouts using `> [!TIP]`, `> [!NOTE]`, or `> [!WARNING]`

To add a lesson, create a file in `app/src/main/assets/content/lessons/`, then reference it from `index.json` with a unique `id`, title, summary, reading time, and file name. Quizzes live in the same chapter entry.

## Tech stack

- Kotlin 2.0
- Jetpack Compose + Material 3
- Navigation Compose
- AGP 8.7, compileSdk 35, minSdk 26
- No third-party runtime dependencies beyond AndroidX

## License

MIT — see [LICENSE](LICENSE).
