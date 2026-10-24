## Automation built into GitHub

GitHub Actions runs commands on GitHub's servers when something happens in your repository: a push, a pull request, a schedule, or a manual click.

Common uses:

- run tests on every pull request,
- build and publish an APK or package,
- deploy a website,
- post a Slack message when a release is published,
- scan for secrets and vulnerabilities.

## A first workflow

Create `.github/workflows/ci.yml`:

```yaml
name: CI

on:
  push:
    branches: [main]
  pull_request:

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: '17'
      - name: Build
        run: ./gradlew assembleDebug --no-daemon
```

Commit it, push, and open the **Actions** tab. The workflow runs automatically.

## The pieces

- **Workflow** — one YAML file in `.github/workflows/`.
- **Trigger** (`on`) — the event that starts it.
- **Job** — a group of steps on one runner. Jobs run in parallel by default.
- **Step** — a command or an action.
- **Runner** — the virtual machine (`ubuntu-latest`, `windows-latest`, `macos-latest`).
- **Action** — a reusable step, referenced with `uses:`.

## Triggers worth knowing

```yaml
on:
  push:
    branches: [main]
    tags: ['v*']
  pull_request:
  workflow_dispatch:      # manual button in the UI
  schedule:
    - cron: '0 6 * * 1'   # Mondays at 06:00 UTC
```

## Secrets

Never put tokens in the YAML. Add them under **Settings → Secrets and variables → Actions**, then reference them:

```yaml
      - name: Publish
        env:
          TOKEN: ${{ secrets.GITHUB_TOKEN }}
        run: ./publish.sh
```

`GITHUB_TOKEN` is provided automatically and expires when the job ends.

## Artifacts

Save build output so you can download it from the run:

```yaml
      - uses: actions/upload-artifact@v4
        with:
          name: app-debug
          path: app/build/outputs/apk/debug/app-debug.apk
```

## Cache dependencies

```yaml
      - uses: actions/cache@v4
        with:
          path: |
            ~/.gradle/caches
            ~/.gradle/wrapper
          key: gradle-${{ hashFiles('**/*.gradle*', 'gradle/wrapper/gradle-wrapper.properties') }}
```

Caching can cut minutes off every run.

## Matrix builds

Test several versions at once:

```yaml
    strategy:
      matrix:
        java: ['17', '21']
    steps:
      - uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: ${{ matrix.java }}
```

## Permissions

Be explicit about what the token can do:

```yaml
permissions:
  contents: read
```

Add `contents: write` only for jobs that create releases.

## Debugging failures

- Click a failed job, then a failed step, to read the log.
- Re-run failed jobs without rerunning everything.
- Use `act` locally to approximate a workflow, though parity is not perfect.

> [!TIP] Start with one small workflow that builds the project. Add caching and artifacts once it is green. Huge YAML from day one is hard to debug.

> [!WARNING] Pull requests from forks do not receive repository secrets by default. That is a security feature, not a bug. Do not try to bypass it.
