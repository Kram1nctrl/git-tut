## The problem Git solves

Picture a folder called `project-final-final-v3`. Inside it: `project-final-v3-FIXED.zip`. We have all been there. Copying folders is how many people "version" their work, and it breaks down fast. Which copy is the newest? What changed between them? How do you combine two copies that both have useful edits?

A **version control system** (VCS) keeps that history for you. It records what changed, when, and who changed it, so you can move backwards, compare versions, and work on several ideas at once without duplicating folders.

## What Git actually is

Git is a free, open-source, **distributed** version control system. Linus Torvalds built it in 2005 to manage the Linux kernel, and it is now the default tool for software teams everywhere.

- **Distributed** means every clone contains the full history. You can commit, branch, and inspect history with no internet connection.
- **Snapshot-based** means each commit records the state of your project at that moment, not just a list of changed lines.
- **Fast** because almost everything happens locally on your machine.

## Commits are snapshots with an ID

Every time you commit, Git takes a snapshot of your project and stores it under a unique 40-character ID (a SHA-1 hash). You will usually see the first seven characters, like `a3f9c21`.

That ID is a fingerprint of the snapshot. If even one byte changes, the ID changes. This is why Git history is very hard to tamper with silently.

## The mental model

Three ideas are enough to get started:

- A **repository** (repo) is your project folder plus its hidden `.git` history.
- A **commit** is a saved snapshot with a message explaining it.
- A **branch** is a movable label that points at a commit, letting you try ideas safely.

Everything else in Git is built on those three ideas.

> [!TIP] Think in snapshots, not in "saving files". You edit files freely, then decide which changes belong in the next snapshot.

## See it on your machine

If Git is installed, this prints the version:

```bash
git --version
```

Then ask Git to explain itself:

```bash
git help
git help commit
```

## Key terms you will meet again

- **Working directory** — the files you can see and edit.
- **Staging area** — a waiting room for changes you want in the next commit.
- **Remote** — another copy of the repository, usually on GitHub.
- **HEAD** — a pointer to the commit you currently have checked out.
- **Origin** — the default nickname Git gives your main remote.

> [!NOTE] In the next lesson we separate two things people constantly mix up: Git and GitHub. They are not the same product.
