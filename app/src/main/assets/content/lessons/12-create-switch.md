## List branches

```bash
git branch          # local
git branch -a       # local + remote-tracking
git branch -v       # with last commit
```

The current branch is marked with `*`.

## Create a branch

```bash
git branch feature/login
```

This creates the branch at your current commit but does **not** switch to it.

## Create and switch in one step

```bash
git switch -c feature/login
```

`git switch` is the modern command, added in Git 2.23. The older equivalent still works everywhere:

```bash
git checkout -b feature/login
```

Prefer `switch` for branches and `restore` for files. They are clearer than the overloaded `checkout`.

## Switch branches

```bash
git switch main
git switch feature/login
```

Before switching, Git checks that your uncommitted changes would not be overwritten. If they would, it refuses and tells you why.

## Naming that scales

A consistent scheme saves confusion:

- `feature/short-description`
- `fix/issue-142`
- `docs/installation`
- `chore/update-dependencies`

Use lowercase and hyphens. Avoid spaces. Many teams include the issue number.

## Branch from a specific point

By default a new branch starts at `HEAD`. You can start elsewhere:

```bash
git switch -c hotfix/login main
git switch -c experiment a3f9c21
```

## Track a remote branch

When a teammate pushes a branch, fetch and check it out:

```bash
git fetch origin
git switch feature/login
```

If Git finds exactly one remote branch with that name, it creates a local branch that tracks it automatically.

## Uncommitted work when switching

Two useful paths:

**Commit it** on the current branch:

```bash
git add -A
git commit -m "WIP: login form"
```

**Stash it** if it is not ready:

```bash
git stash push -m "half-done login form"
git switch main
# later
git switch feature/login
git stash pop
```

## See where branches point

```bash
git log --oneline --graph --decorate --all
```

The `--decorate` flag prints branch names next to commits, which makes the graph much easier to read.

> [!TIP] Create the branch **before** you start coding, not after. Switching later with uncommitted changes is possible, but starting clean avoids surprises.

> [!WARNING] Branch names are case-sensitive on Linux and in Git, but Windows and macOS file systems may not be. `Feature/Login` and `feature/login` can collide for teammates. Stick to lowercase.
