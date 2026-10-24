## GitHub from the terminal

The GitHub CLI (`gh`) brings issues, pull requests, releases, and workflows into your shell.

Install:

```bash
# macOS
brew install gh

# Ubuntu / Debian
sudo apt install gh

# Windows
winget install --id GitHub.cli
```

Authenticate once:

```bash
gh auth login
```

## Create a repository

```bash
gh repo create git-tut --public --source=. --push
```

That creates the repository on GitHub, adds `origin`, and pushes the current branch, all in one command.

Other useful forms:

```bash
gh repo clone Kram1nctrl/git-tut
gh repo view --web
gh repo fork
```

## Pull requests

```bash
gh pr create --title "Add quiz screen" --body "Adds a quiz per chapter."
gh pr list
gh pr view 12 --web
gh pr checkout 12
gh pr review 12 --approve
gh pr merge 12 --squash --delete-branch
```

`gh pr create --fill` uses your commit messages for the title and body.

## Issues

```bash
gh issue create --title "Crash on rotation" --body "Steps to reproduce…"
gh issue list --label bug
gh issue view 42
gh issue close 42
```

## Releases and Actions

```bash
gh release create v1.0.0 --generate-notes app-release.apk
gh run list
gh run watch
gh run download <run-id>
```

## Raw API access

Anything the CLI does not cover is available through the REST API:

```bash
gh api repos/Kram1nctrl/git-tut
gh api repos/Kram1nctrl/git-tut/topics --method PUT -f names[]=android -f names[]=git
```

## Gists

A gist is a small shareable snippet or file, backed by Git.

```bash
gh gist create script.sh --desc "Deploy helper"
gh gist list
gh gist clone <gist-id>
```

Public gists are searchable and visible to everyone. Secret gists are unlisted but **not private**: anyone with the link can read them.

## Aliases for gh

```bash
gh alias set prs 'pr list --author @me'
gh alias set bugs 'issue list --label bug'
```

## When to use the CLI

- Creating several repositories or issues quickly.
- Scripting release uploads.
- Checking CI status without leaving the terminal.
- Cloning a pull request to review it locally.

The web UI is still better for visual diffs, project boards, and discussions.

> [!TIP] `gh auth status` tells you which account is active and which scopes the token has. It is the first thing to check when a command says "forbidden".

> [!WARNING] Secret gists are not secure storage. Never put tokens, private keys, or customer data in one, even unlisted.
