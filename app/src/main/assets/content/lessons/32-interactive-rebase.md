## Rewrite a series of commits

Interactive rebase opens a list of commits and lets you edit history before it is shared:

```bash
git rebase -i HEAD~4
```

Your editor shows:

```text
pick a3f9c21 Add login form
pick 8b2d1e0 Fix typo
pick f1c0a99 Add validation
pick 2c4b7a8 Remove debug logs

# Rebase ...
# Commands:
# p, pick   use commit
# r, reword change message
# e, edit   stop to amend
# s, squash combine into previous commit
# f, fixup  like squash but discard this message
# d, drop   remove commit
```

Change the word at the start of each line, save, and close.

## The commands that matter

- **pick** — keep the commit as is.
- **reword** — keep the changes, edit the message.
- **edit** — pause so you can amend the commit.
- **squash** — combine with the previous commit, edit the combined message.
- **fixup** — combine with the previous commit, discard this message.
- **drop** — remove the commit entirely.
- **exec** — run a shell command after the commit.

Lines can be reordered. Git replays them in the order shown.

## Clean up before a pull request

A common workflow: you made six messy commits and want one coherent commit.

```bash
git rebase -i HEAD~6
# change every line after the first to "fixup"
```

Result: one commit with the first message. Then:

```bash
git push --force-with-lease
```

## Edit a commit in the middle

Mark it `edit`:

```bash
git rebase -i HEAD~5
```

Git stops at that commit. Make changes, then:

```bash
git add .
git commit --amend --no-edit
git rebase --continue
```

## Fixup commits, automatically

Instead of interactive editing, commit a fix directly to an earlier commit:

```bash
git commit --fixup a3f9c21
git rebase -i --autosquash main
```

Git creates a `fixup! Add login form` commit and places it next to the target automatically.

## Reorder commits

Move lines in the editor. If the reorder causes conflicts, resolve them and continue. Reordering commits that touch the same lines often conflicts.

## Split a commit

Mark it `edit`, then:

```bash
git reset HEAD~1
git add -p            # stage the first part
git commit -m "Part one"
git add .
git commit -m "Part two"
git rebase --continue
```

## Abort or recover

```bash
git rebase --abort
```

Or, if already finished:

```bash
git reflog
git reset --hard HEAD@{n}
```

## When not to do this

- The commits are already on a shared branch.
- The history is signed and others verify signatures.
- You are unsure what the result should look like.

Interactive rebase is powerful and safe **for local history**. It is risky for published history.

> [!TIP] Rebase interactively before opening a pull request, not after reviewers have started. Rewriting a branch mid-review makes comments hard to follow.

> [!WARNING] A `drop` line deletes the commit's changes. If you might need them, create a backup branch first: `git branch backup-before-rebase`.
