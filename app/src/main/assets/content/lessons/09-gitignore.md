## What belongs in .gitignore

A `.gitignore` file lists paths Git should pretend do not exist. Typical entries:

```gitignore
# Build output
build/
dist/
*.class

# Dependencies
node_modules/
.venv/

# Secrets
.env
*.keystore
local.properties

# Editor and OS junk
.idea/
.DS_Store
Thumbs.db
```

Create it in the repository root and commit it. Everyone who clones the project then gets the same rules.

## Pattern syntax

- `file.txt` — matches that name in any directory.
- `/file.txt` — matches only at the repository root.
- `build/` — matches a directory named build anywhere.
- `*.log` — any file ending in `.log`.
- `**/temp` — temp in any nested directory.
- `!important.log` — re-include a file that an earlier rule ignored.
- `#` starts a comment.

```gitignore
*.log
!keep.log
```

## Check why a file is ignored

```bash
git check-ignore -v path/to/file
```

The output names the exact `.gitignore` line responsible. This is the fastest way to debug "why is Git ignoring my file?".

## The big gotcha: already-tracked files

`.gitignore` only affects **untracked** files. If a file was committed before you added the rule, Git keeps tracking it. Remove it from the index without deleting it from disk:

```bash
git rm --cached .env
git commit -m "Stop tracking .env"
```

For a whole folder:

```bash
git rm -r --cached build/
git commit -m "Stop tracking build output"
```

The files stay on your disk, but future changes are ignored.

## Global ignore for your machine

Editor junk is personal. Put it in a global ignore file instead of every repo:

```bash
git config --global core.excludesfile ~/.gitignore_global
echo ".idea/" >> ~/.gitignore_global
```

## What never to ignore

Do not ignore lockfiles (`package-lock.json`, `gradle.lockfile`) or dependency manifests (`build.gradle.kts`, `requirements.txt`). They are how the project reproduces the same build elsewhere.

Also commit `.gitignore` itself. It is project configuration, not junk.

## Templates

GitHub offers starter `.gitignore` files when you create a repository, and github.com/github/gitignore has templates for almost every language and editor.

> [!TIP] Add `.gitignore` before your first commit in a new project. Retrofitting it means cleaning the index later.

> [!WARNING] If a secret was already committed, adding it to `.gitignore` does not remove it from history. You must rotate the secret and rewrite history. Assume it is compromised.
