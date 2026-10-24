## Copy a commit onto another branch

Cherry-pick takes the change from one commit and applies it as a new commit on your current branch:

```bash
git switch main
git cherry-pick a3f9c21
```

The new commit has a different hash but the same changes and message.

## When cherry-pick helps

- A bug fix on `main` is also needed on an old release branch.
- You want one commit from a messy branch without the rest.
- You accidentally committed to the wrong branch and want to move the commit.

## Cherry-pick several commits

```bash
git cherry-pick a3f9c21 8b2d1e0 f1c0a99
```

Or a range:

```bash
git cherry-pick main..feature
```

The range excludes `main` and includes everything after it.

## Keep a reference to the original

```bash
git cherry-pick -x a3f9c21
```

This adds `(cherry picked from commit a3f9c21…)` to the message. Helpful when tracking backports.

## Handle conflicts

A cherry-pick is a merge under the hood, so it can conflict:

```bash
# resolve files
git add <files>
git cherry-pick --continue
```

Or:

```bash
git cherry-pick --abort
```

To skip the current commit in a multi-commit pick:

```bash
git cherry-pick --skip
```

## Move a commit to the right branch

You committed to `main` by mistake:

```bash
git log --oneline -1          # note the hash
git switch feature/correct-branch
git cherry-pick <hash>
git switch main
git reset --hard HEAD~1       # only if not pushed
```

Safer alternative without rewriting: revert it on `main` and cherry-pick it where it belongs.

## Cherry-pick vs merge vs rebase

- **Merge** brings an entire branch and its history.
- **Rebase** replays your branch's commits onto a new base.
- **Cherry-pick** copies selected commits.

Use cherry-pick for surgical backports, not as a substitute for merging a feature.

## Duplicate commits

Cherry-picking creates a copy. If you later merge the original branch, Git may see two commits with the same change and produce a conflict or duplicate history. Prefer merging when the whole branch is needed.

> [!TIP] Cherry-pick is ideal for hotfixes: fix once on `main`, then pick the same commit onto `release/1.0` and `release/0.9`.

> [!WARNING] Cherry-picking a merge commit requires `-m 1` to choose the parent, just like reverting one. It is usually better to pick the individual commits instead.
