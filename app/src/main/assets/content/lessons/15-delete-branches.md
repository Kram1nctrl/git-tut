## Delete a merged branch

Once a feature is merged, the branch label is no longer needed:

```bash
git branch -d feature/login
```

Git refuses if the branch is not fully merged, which protects unmerged work.

## Delete an unmerged branch

If you truly want to throw the branch away:

```bash
git branch -D experiment/gone-wrong
```

Capital `-D` is `--delete --force`. The commits become unreachable, but `git reflog` can still recover them for a while.

## Rename a branch

```bash
git branch -m new-name            # rename current branch
git branch -m old-name new-name   # rename another branch
```

If the branch was already pushed, update the remote too:

```bash
git push origin --delete old-name
git push -u origin new-name
```

## Delete a remote branch

Deleting locally does not touch GitHub:

```bash
git push origin --delete feature/login
```

Or, with the newer syntax:

```bash
git push origin :feature/login
```

On GitHub, merging a pull request usually offers a "Delete branch" button, which does the same thing.

## Clean up stale remote references

When teammates delete branches on the remote, your local list still shows `origin/...` entries until you prune:

```bash
git fetch --prune
```

Make it automatic:

```bash
git config --global fetch.prune true
```

## See which branches are merged

```bash
git branch --merged main
git branch --no-merged main
```

The first lists branches already contained in `main`; those are safe to delete. The second lists branches with work that is not merged yet.

## Recover a deleted branch

If you deleted a branch by mistake, find its last commit:

```bash
git reflog
git switch -c recovered-branch a3f9c21
```

As long as the commit has not been garbage-collected (usually weeks), it is recoverable.

## A healthy branch list

- `main` — the deployable line.
- A handful of active feature branches.
- Nothing else.

Delete branches after merging. A repository with 90 stale branches is hard to navigate, and old branches hide the fact that work was abandoned.

> [!TIP] Run `git branch --merged main` before cleaning up. Anything listed there is already part of `main` and safe to remove.

> [!NOTE] Deleting a branch deletes only the label. The commits stay reachable as long as another branch, tag, or the reflog still points to them.
