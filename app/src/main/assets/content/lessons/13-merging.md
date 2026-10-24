## Merge means "bring changes together"

To merge a feature into `main`:

```bash
git switch main
git pull                 # make sure main is current
git merge feature/login
```

Git now tries to combine the two histories.

## Fast-forward merge

If `main` has not moved since the feature branch was created, Git can simply slide the `main` pointer forward:

```text
Before:  main: A---B
                \
                 feature: C---D

After:   main: A---B---C---D
                         ↑
                       feature
```

No merge commit is created. History stays linear. This is called a **fast-forward**.

## Three-way merge

If both branches moved, Git creates a merge commit with two parents:

```text
main:    A---B-------M
              \     /
feature:       C---D
```

`M` is a real commit. Its message is usually `Merge branch 'feature/login' into main`.

Git finds the **merge base** (the last commit the branches share) and combines the changes from both sides since that point.

## Force a merge commit

Some teams prefer a merge commit even when a fast-forward is possible, because it records that a feature was integrated:

```bash
git merge --no-ff feature/login
```

The opposite, `--ff-only`, refuses to merge unless a fast-forward is possible. It keeps history linear and is a good default for `main`:

```bash
git merge --ff-only feature/login
```

## Preview before merging

```bash
git merge --no-commit --no-ff feature/login
git diff --staged
```

This stages the result but stops before committing, so you can inspect it. If you change your mind:

```bash
git merge --abort
```

## Merge only what you want

A merge brings the whole branch. If you only need one commit from it, use `git cherry-pick` (covered later).

## After the merge

```bash
git log --oneline --graph -10
```

You should see the combined history. Then push:

```bash
git push origin main
```

## Merge vs rebase

Both integrate changes; they produce different history shapes.

- **Merge** preserves what actually happened, including parallel work. History is truthful but can look busy.
- **Rebase** rewrites commits onto a new base, producing a straight line. Cleaner, but rewrites hashes.

A common policy: merge feature branches into `main`, and use rebase only on your own unpublished branch.

> [!TIP] Always merge **into** the branch that should receive the changes. Switch to `main` first, then merge the feature. Doing it the other way around updates the feature branch, not `main`.

> [!WARNING] Never merge a branch you have not pulled. Merging stale `main` into your feature, or a stale feature into `main`, creates avoidable conflicts.
