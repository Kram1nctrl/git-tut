## Three commands, three jobs

- `git fetch` — download new commits from the remote. Does not change your files.
- `git pull` — fetch, then integrate into your current branch.
- `git push` — upload your commits to the remote.

Understanding the difference prevents most "Git ate my work" panics.

## Push

First push of a new branch:

```bash
git push -u origin feature/login
```

After that:

```bash
git push
```

`-u` sets the upstream, so Git remembers the destination.

If the remote rejects your push with "non-fast-forward", someone else pushed first. Pull before pushing:

```bash
git pull
git push
```

## Pull

```bash
git pull
```

This is shorthand for `git fetch` followed by `git merge origin/<current-branch>`.

If you prefer a linear history:

```bash
git pull --rebase
```

That fetches, then replays your local commits on top of the remote ones.

Set it once for a repository:

```bash
git config pull.rebase true
```

## Fetch

```bash
git fetch origin
```

This updates `origin/main` and friends but leaves your working directory and local branches untouched. It is the safe way to see what changed:

```bash
git fetch
git log HEAD..origin/main --oneline
git diff HEAD origin/main
```

Then decide whether to merge, rebase, or do nothing.

## Tracking branches

A local branch can track a remote branch. Check with:

```bash
git branch -vv
```

The output shows the upstream and whether you are ahead or behind:

```text
* main  a3f9c21 [origin/main: ahead 2] Add quiz screen
```

Set or change tracking:

```bash
git branch --set-upstream-to=origin/main main
```

## Force push, carefully

Rewriting history (amend, rebase, reset) requires a force push:

```bash
git push --force-with-lease
```

`--force-with-lease` refuses to overwrite if the remote changed since your last fetch. Plain `--force` overwrites blindly and can destroy teammates' commits.

> [!WARNING] Never force-push `main` on a shared repository. If your team uses protected branches, GitHub will block it anyway.

## Push tags

Tags are not pushed by default:

```bash
git push origin v1.0.0
git push --tags
```

## Delete a remote branch

```bash
git push origin --delete feature/old
```

## A safe daily rhythm

```bash
git fetch            # see what is new
git status           # am I ahead or behind?
git pull --rebase    # integrate (if you prefer linear history)
# work, add, commit
git push
```

> [!TIP] When unsure, fetch first. Fetching cannot break anything; merging and rebasing can, and you should do those with your eyes open.

> [!NOTE] "Your branch is ahead of 'origin/main' by 2 commits" means you have local commits that are not uploaded yet. "Behind" means the remote has commits you do not have.
