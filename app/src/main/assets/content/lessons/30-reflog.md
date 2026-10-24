## Git's safety camera

The reflog records every time `HEAD` moved: commits, checkouts, merges, rebases, resets. It is local to your clone and kept for about 90 days by default.

```bash
git reflog
```

```text
a3f9c21 HEAD@{0}: commit: Add quiz screen
8b2d1e0 HEAD@{1}: reset: moving to HEAD~1
f1c0a99 HEAD@{2}: commit: Add search screen
```

This is how you undo mistakes that "destroyed" commits.

## Recover a bad reset

You ran `git reset --hard HEAD~3` and lost three commits:

```bash
git reflog
# find the commit before the reset
git reset --hard HEAD@{1}
```

Or create a branch at the lost commit, which is safer:

```bash
git switch -c rescue HEAD@{1}
```

## Recover a deleted branch

```bash
git reflog
git switch -c recovered-branch a3f9c21
```

Find the last commit that belonged to the branch, then recreate the label.

## Recover after a bad rebase

A rebase rewrites commits and moves the branch. The pre-rebase position is in the reflog:

```bash
git reflog
git reset --hard HEAD@{5}
```

Look for the entry labelled `rebase (start)` or the last `commit` before it.

## Reflog for a specific branch

```bash
git reflog show main
```

This is useful when you switched branches and `HEAD` entries are noisy.

## Expiry

Reflog entries are not permanent:

```bash
git reflog expire --expire=now --all
git gc --prune=now
```

Do not run this casually. It permanently removes unreachable commits, including the ones you might want to recover.

## Unreachable commits

If the reflog does not help, `git fsck` can find dangling commits:

```bash
git fsck --lost-found
```

They appear under `.git/lost-found/commit/`. Inspect them with `git show <hash>` and recover the ones you need.

## What the reflog cannot save

- Uncommitted changes discarded with `git restore` or `git reset --hard`.
- Files deleted outside Git and never committed.
- Work on a different machine or a different clone.

The reflog only knows about commits that existed locally.

Before any risky operation (rebase, reset, filter-branch), note the current commit:

```bash
git rev-parse HEAD
```

If things go wrong, `git reset --hard <that-hash>` brings you back.

> [!NOTE] The reflog is per-clone. A teammate's reflog cannot recover your lost commits, and yours cannot recover theirs.
