## Reset moves the branch pointer

`git reset` moves your current branch backwards (or forwards) to another commit. What happens to your files depends on the mode.

```bash
git reset <mode> <commit>
```

The three modes are `--soft`, `--mixed` (default), and `--hard`.

## --soft: undo the commit, keep everything staged

```bash
git reset --soft HEAD~1
```

The commit disappears, but its changes are still staged, ready to be committed again differently. Perfect for "I committed too early" or "I want to combine the last three commits".

## --mixed: undo the commit and unstage

```bash
git reset HEAD~1
```

The commit disappears and its changes return to your working directory, unstaged. This is the default mode. Use it when you want to re-select what goes into the next commit.

## --hard: undo everything

```bash
git reset --hard HEAD~1
```

The commit disappears and the changes are erased from your working directory. This is destructive.

If you only want to throw away uncommitted changes but keep the commit:

```bash
git reset --hard HEAD
```

> [!WARNING] `--hard` permanently discards uncommitted work. It cannot be recovered through the reflog. Commit or stash before running it.

## Reset a single file

```bash
git reset HEAD~1 -- src/app.kt
```

This restores that file to its state in the previous commit in the staging area, without moving the branch.

## Reset to match the remote

Your local branch is a mess and you want it to match GitHub exactly:

```bash
git fetch origin
git reset --hard origin/main
```

Everything local on that branch is replaced by the remote state. Any unpushed commits are gone from the branch (though still in the reflog for a while).

## Reset vs revert

- **Reset** rewrites your local branch. Use it on commits you have not shared.
- **Revert** creates a new commit that undoes an old one. Use it on shared history.

If you already pushed the commit and others may have pulled it, do not reset and force-push. Revert instead.

## Recovering from a bad reset

Even `--hard` is recoverable if the commits existed before:

```bash
git reflog
git reset --hard HEAD@{2}
```

The reflog records where `HEAD` was, so you can jump back to the pre-reset position.

## A safe workflow for "undo my last commit"

```bash
git log --oneline -3       # confirm what you are undoing
git reset --soft HEAD~1    # keep the changes staged
git status                 # inspect
git commit -m "Better message"
```

> [!TIP] When in doubt, prefer `--soft` or `--mixed`. They never delete your file changes, so you can always adjust and commit again.

> [!NOTE] `HEAD~1` means "one commit before HEAD". `HEAD~3` is three commits back. `HEAD^` is the first parent of a merge commit; `HEAD~` is shorthand for `HEAD~1`.
