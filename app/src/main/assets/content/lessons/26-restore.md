## Undo changes in the working directory

You edited a file and want it back the way it was at the last commit:

```bash
git restore src/app.kt
```

The edits are gone permanently. Use it when you are sure.

`git restore` replaced the older, overloaded `git checkout -- file`. It only touches your working directory; history is unaffected.

## Undo everything unstaged

```bash
git restore .
```

This discards all unstaged changes in the current directory and below. Untracked files are not affected; they were never tracked.

## Unstage a file

You staged something you do not want in the next commit:

```bash
git restore --staged src/app.kt
```

The file keeps its edits but leaves the staging area. Nothing is lost.

## Restore from an older commit

Bring back the version of a file from a specific commit:

```bash
git restore --source=HEAD~1 src/app.kt
```

Or from another branch:

```bash
git restore --source=main src/app.kt
```

This writes the old content into your working directory, unstaged. Review it, then stage and commit if it is what you want.

## Restore a deleted file

If you deleted a tracked file but have not committed the deletion:

```bash
git restore path/to/deleted-file
```

If the deletion was committed, restore it from the commit before:

```bash
git restore --source=HEAD~1 path/to/deleted-file
```

## Preview before destroying

There is no undo for `restore` on uncommitted work. Check the diff first:

```bash
git diff src/app.kt
```

If the change might be worth keeping, commit it or stash it first:

```bash
git stash push -m "maybe useful"
```

## Which command undoes what?

- Working directory changes → `git restore <file>`
- Staged changes → `git restore --staged <file>`
- The last commit (local) → `git reset` or `git commit --amend`
- A commit that is already shared → `git revert <hash>`
- Lost commits → `git reflog`

> [!WARNING] `git restore <file>` deletes uncommitted edits immediately. There is no confirmation prompt and no reflog entry for uncommitted work. When in doubt, stash first.

> [!TIP] If you are not sure what you are about to lose, run `git status` and `git diff` before restoring. Five seconds of checking beats an afternoon of rewriting code.
