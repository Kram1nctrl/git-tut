## Rebase moves your commits

Rebase takes the commits unique to your branch and replays them on top of another branch:

```bash
git switch feature/login
git rebase main
```

Before:

```text
main:     A --- B --- C
               \
feature:        D --- E
```

After:

```text
main:     A --- B --- C
                       \
feature:                D' --- E'
```

`D'` and `E'` are new commits with new hashes. The old ones are gone from the branch (but remain in the reflog).

## Why rebase?

- **Linear history** — no merge commits cluttering the graph.
- **Easier review** — your changes sit on top of the latest base.
- **Cleaner pull requests** — reviewers see only your work.

## Rebase vs merge

| Merge | Rebase |
| --- | --- |
| Preserves real history | Rewrites history into a line |
| Creates a merge commit | No extra commit |
| Safe on shared branches | Only for unpublished work |
| Conflicts resolved once | Conflicts may repeat per commit |

Both are correct. Teams often merge into `main` and rebase feature branches locally.

## Conflicts during rebase

Git pauses and marks the conflicted files:

```bash
git status
```

Resolve them, then:

```bash
git add <files>
git rebase --continue
```

To skip the current commit:

```bash
git rebase --skip
```

To abandon the rebase and return to the starting point:

```bash
git rebase --abort
```

## The golden rule

> [!WARNING] Never rebase commits that other people have pulled. You are replacing them with new commits, so everyone else's history diverges. If you must, coordinate with the team and use `--force-with-lease`.

A good rule: rebase your own branch, merge shared branches.

## Update a PR branch

```bash
git fetch origin
git rebase origin/main
git push --force-with-lease
```

The pull request updates automatically. Reviewers may see "force-pushed" in the timeline, which is normal.

## Rebase onto a specific commit

```bash
git rebase --onto main old-base feature
```

This replays commits from `feature` that are not in `old-base` onto `main`. Useful for removing a dependency on a branch that was merged differently.

## Interactive rebase

Add `-i` to edit, squash, reorder, or drop commits:

```bash
git rebase -i HEAD~3
```

The next lesson covers this in detail.

## Recovering from a bad rebase

```bash
git reflog
git reset --hard HEAD@{n}
```

Find the entry just before `rebase (start)`.

> [!TIP] Run `git fetch` before rebasing so you are rebasing onto the real latest `main`, not a stale local copy.

> [!NOTE] `git pull --rebase` is rebase applied to pulling. It replays your local commits on top of the fetched remote branch, avoiding a merge commit.
