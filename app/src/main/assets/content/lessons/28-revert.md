## Undo without rewriting history

`git revert` creates a **new commit** that applies the opposite of an earlier commit. The original stays in history, so this is safe on shared branches.

```bash
git revert a3f9c21
```

Git opens an editor with a message like `Revert "Add login timeout"`. Save it, and the change is undone going forward.

## Why not just reset?

Reset moves the branch pointer and rewrites history. If teammates already have the commit, their history and yours diverge, and someone has to force-push.

Revert adds a fix on top. Everyone can pull it normally.

```text
A --- B --- C --- D
                \
                 R   (revert of C)
```

## Revert multiple commits

```bash
git revert HEAD~3..HEAD
```

This reverts the range. Git may open an editor per commit, or you can let it run without editing:

```bash
git revert --no-edit HEAD~3..HEAD
```

Order matters: reverting a range applies them newest-first by default.

## Revert a merge commit

A merge commit has two parents, so Git needs to know which side to keep:

```bash
git revert -m 1 <merge-hash>
```

`-m 1` means "keep the first parent" (usually the branch you merged into). Reverting merges is tricky; prefer reverting the individual commits when possible.

## Handle conflicts

A revert can conflict if later commits touched the same lines. Resolve them like a merge:

```bash
# fix the files
git add <files>
git revert --continue
```

Or abort:

```bash
git revert --abort
```

## Revert without committing

Sometimes you want to inspect the inverse change first:

```bash
git revert --no-commit a3f9c21
git diff --staged
git commit -m "Undo login timeout change"
```

## When to use which

- **Local, unpushed commit** → reset or amend.
- **Pushed commit on a shared branch** → revert.
- **Several related commits** → revert each, or revert the merge.
- **Secret leaked** → reverting is not enough; rotate the secret and rewrite history.

> [!TIP] Revert is the professional undo. It leaves an honest record: the change happened, and then it was deliberately undone, with a reason in the message.

> [!WARNING] Reverting a merge commit does not prevent the changes from coming back if you merge that branch again. You may need to revert the revert or revert individual commits instead.
