## Forks drift

Your fork is a snapshot. When the original project moves forward, your fork does not update automatically. You need to sync it.

Two ways: the GitHub button, or Git commands.

## The easy way: GitHub's Sync fork

On your fork's main page, GitHub shows whether it is behind:

```text
This branch is 12 commits behind original-owner:main.
```

Click **Sync fork** → **Update branch**. GitHub merges upstream changes into your fork's default branch.

This is fine when your fork's `main` has no local commits of its own.

## The reliable way: Git commands

Add the original repository once:

```bash
git remote add upstream https://github.com/original-owner/project.git
```

Then, whenever you want to sync:

```bash
git switch main
git fetch upstream
git merge upstream/main
git push origin main
```

Or with rebase:

```bash
git fetch upstream
git rebase upstream/main
git push origin main
```

If your fork's `main` has diverged and you want it to match upstream exactly:

```bash
git fetch upstream
git reset --hard upstream/main
git push --force-with-lease origin main
```

> [!WARNING] `reset --hard` throws away local commits on that branch. Only use it on a fork's `main` that is not the source of an open pull request.

## Keep your feature branch current

While a pull request is open, the base branch may move. Update your branch:

```bash
git switch feature/my-change
git fetch upstream
git rebase upstream/main
git push --force-with-lease
```

Or merge instead of rebase:

```bash
git merge upstream/main
```

Both resolve the same problem; rebase keeps history linear, merge preserves it.

## GitHub's "Update branch" button

On the PR page, if the base branch has moved, GitHub may offer **Update branch**. It merges the base into your branch for you. Convenient, but it adds a merge commit; some teams prefer rebasing locally.

## Never work on main in a fork

If you commit directly to your fork's `main`:

- Syncing becomes a merge instead of a fast-forward.
- Every future PR may accidentally include those commits.
- Resetting becomes risky.

Keep `main` as a clean mirror of upstream. Do all work on branches.

## A weekly habit

```bash
git switch main
git fetch upstream
git merge --ff-only upstream/main
git push origin main
```

Thirty seconds, and your fork stays healthy. Enable pruning once so deleted remote branches disappear from your local list:

```bash
git config --global fetch.prune true
```

Then `git fetch --all` keeps every remote reference tidy.

> [!NOTE] A fork can be detached from its upstream network, for example if the original is deleted or made private. In that case, treat your fork as an independent repository.
