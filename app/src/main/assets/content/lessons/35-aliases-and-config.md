## Shortcuts for long commands

An alias is a custom name for a Git command:

```bash
git config --global alias.st status
git config --global alias.co checkout
git config --global alias.br branch
```

Now `git st` runs `git status`.

## Aliases with flags

```bash
git config --global alias.lg "log --oneline --graph --decorate --all"
git config --global alias.last "log -1 HEAD --stat"
git config --global alias.unstage "restore --staged"
git config --global alias.undo "reset --soft HEAD~1"
```

Use them:

```bash
git lg
git unstage src/app.kt
```

## Where aliases live

They are stored in your Git config (`~/.gitconfig` by default). Edit the file directly:

```bash
git config --global --edit
```

```ini
[alias]
    st = status
    co = checkout
    lg = log --oneline --graph --decorate --all
    amend = commit --amend --no-edit
```

## Shell aliases

Prefix with `!` to run a shell command:

```bash
git config --global alias.root '!pwd'
```

Or something more useful:

```bash
git config --global alias.cleanup '!git branch --merged main | grep -v "main" | xargs -r git branch -d'
```

Shell aliases run with your permissions. Be careful with anything destructive.

## Useful settings beyond aliases

```bash
git config --global pull.ff only          # refuse non-fast-forward pulls
git config --global fetch.prune true      # clean stale remote branches
git config --global rerere.enabled true   # remember conflict resolutions
git config --global diff.algorithm histogram
git config --global core.pager "less -FRX"
git config --global init.defaultBranch main
```

- **rerere** ("reuse recorded resolution") replays conflict resolutions you have already made. Very helpful during long rebases.
- **pull.ff only** makes accidental merge commits impossible; you must choose merge or rebase explicitly.

## Different identities for work and personal projects

Do not mix your work email into personal commits. Use conditional includes:

```ini
# ~/.gitconfig
[includeIf "gitdir:~/work/"]
    path = ~/.gitconfig-work
```

```ini
# ~/.gitconfig-work
[user]
    name = Your Work Name
    email = you@company.com
```

Everything under `~/work/` uses the work identity automatically.

## Sign your commits

```bash
git config --global commit.gpgsign true
git config --global user.signingkey <KEY_ID>
```

GitHub shows a **Verified** badge for signed commits. Useful for open-source trust, optional for personal projects.

## See everything

```bash
git config --list --show-origin
```

This prints every value and the file it came from, which is the fastest way to debug "why is Git doing that?".

> [!TIP] Add aliases gradually. Only alias commands you actually type often, and keep the names obvious. A cryptic alias you forget is worse than typing the full command.

> [!WARNING] Copying alias collections from the internet without reading them is risky. Shell aliases can delete branches or rewrite history.
