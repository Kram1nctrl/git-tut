## Copy a repository to your machine

```bash
git clone https://github.com/Kram1nctrl/git-tut.git
```

This creates a `git-tut` folder containing the files **and the full history**. Unlike downloading a ZIP, a clone is a complete repository: you can commit, branch, and inspect every past commit offline.

## Clone into a specific folder

```bash
git clone https://github.com/Kram1nctrl/git-tut.git my-folder
```

## Clone a specific branch

```bash
git clone --branch dev --single-branch https://github.com/Kram1nctrl/git-tut.git
```

Useful for large repositories where you only need one line of development.

## Shallow clone

```bash
git clone --depth 1 https://github.com/Kram1nctrl/git-tut.git
```

Only the latest commit is downloaded. Fast, but history commands (`git log`, `git blame`) see almost nothing. You can deepen it later:

```bash
git fetch --deepen 50
```

## What clone sets up for you

- A folder named after the repository.
- A remote called `origin` pointing at the URL.
- A local branch tracking the remote's default branch.
- A checkout of that branch.

Verify:

```bash
cd git-tut
git remote -v
git status
```

## Clone with SSH

```bash
git clone git@github.com:Kram1nctrl/git-tut.git
```

Requires an SSH key added to your GitHub account. HTTPS works with a token or credential helper. Both are fine; pick one and stay consistent.

## Clone vs download ZIP

A ZIP gives you files with no history and no connection to the repository. You cannot pull updates, and you cannot contribute back without extra steps.

Clone when you intend to work on the code. Download a ZIP only when you want a snapshot for reference.

## Common problems

- **"Repository not found"** — private repo without access, or a typo in the URL.
- **"Permission denied (publickey)"** — SSH key not configured or not added to GitHub.
- **"destination path already exists"** — you are already inside a folder with that name, or a previous clone failed. Choose another folder or remove the old one.
- **Very slow clone** — try `--depth 1` or `--filter=blob:none` for a partial clone.

## After cloning

```bash
cd git-tut
git log --oneline -5
git switch -c feature/my-change
```

You are ready to work. Nothing else needs to be "connected".

> [!TIP] Clone once per machine per project. Cloning the same repo into five folders creates five separate histories that are easy to confuse.

> [!NOTE] A clone includes every branch's commits, even though only one branch is checked out. That is why history commands work immediately.
