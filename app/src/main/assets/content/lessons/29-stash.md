## Park your work temporarily

You are mid-change and need to switch branches, pull, or fix something urgent. Stash saves your uncommitted work in a safe place and gives you a clean working directory.

```bash
git stash push -m "half-finished login form"
```

Now:

```bash
git status
```

Clean. Switch branches, do the urgent work, then come back.

## Restore your work

```bash
git stash pop
```

This applies the most recent stash and removes it from the stash list.

If you want to apply it but keep it in the list:

```bash
git stash apply
```

## See what you stashed

```bash
git stash list
```

```text
stash@{0}: On main: half-finished login form
stash@{1}: WIP on feature/login: 1a2b3c4 Add form
```

Inspect the contents:

```bash
git stash show -p stash@{1}
```

## Include untracked files

By default, stash only saves tracked changes. New files stay in your working directory:

```bash
git stash push -u -m "includes new files"
```

`-u` is `--include-untracked`. Use `-a` (`--all`) to include ignored files too, which is rarely what you want.

## Stash only part of your work

```bash
git stash push -p src/app.kt
```

Git asks chunk by chunk, like `git add -p`.

## Drop a stash

```bash
git stash drop stash@{0}
git stash clear
```

`clear` deletes every stash. There is no confirmation.

## Turn a stash into a branch

If the stashed work belongs on its own branch:

```bash
git stash branch feature/rescued stash@{0}
```

This creates the branch at the commit the stash was made from, applies the stash, and drops it if successful.

## Conflicts when popping

If the code changed since you stashed, `git stash pop` can conflict. Git leaves the stash in place if applying fails. Resolve the conflicts, stage the files, then drop the stash manually.

## What stash does not cover

- It does not stash ignored files unless you ask.
- It does not replace commits. Long-term work should live on a branch.
- It is local only; stashes are not pushed.

> [!TIP] Name your stashes. `git stash push -m "fix-navbar-before-demo"` is far easier to recognize a week later than `WIP on main`.

> [!WARNING] Stashes are easy to forget. Run `git stash list` occasionally and clean up. A stash that lives for months is probably a branch that should exist.
