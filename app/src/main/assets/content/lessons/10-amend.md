## Fix the last commit

You just committed and noticed a typo, a forgotten file, or a bad message. If the commit has **not been pushed**, amend it:

```bash
git add forgotten-file.kt
git commit --amend
```

Git opens your editor with the previous message so you can adjust it. To keep the message unchanged:

```bash
git commit --amend --no-edit
```

## What amend actually does

It does not edit the old commit. It creates a **new commit** with a new hash, then moves your branch to point at the new one. The old commit becomes unreachable (but recoverable via reflog for a while).

That is why the golden rule exists:

> [!WARNING] Never amend a commit that other people have pulled. Rewriting shared history forces everyone to clean up their local copy. Amend only local, unpushed work.

## Change only the message

```bash
git commit --amend -m "Better explanation of the change"
```

## Add a file you forgot

```bash
git add missing-file.txt
git commit --amend --no-edit
```

The new snapshot contains both the original changes and the newly staged file.

## Remove a file from the last commit

```bash
git restore --staged secret.env
git commit --amend --no-edit
```

The file returns to being untracked. This removes it from the latest commit, but not from earlier ones.

## Fix the author

```bash
git commit --amend --author="Kram1n <you@example.com>" --no-edit
```

Or reset your identity for future commits:

```bash
git config --global user.name "Kram1n"
git config --global user.email "you@example.com"
```

## If you already pushed

You have two honest options:

1. **Amend and force-push** only if you are certain nobody else uses the branch:
   ```bash
   git push --force-with-lease
   ```
2. **Leave history alone** and make a new commit that fixes the problem:
   ```bash
   git commit -m "Fix typo from previous commit"
   ```

`--force-with-lease` is safer than `--force`: it refuses to overwrite if the remote moved since your last fetch.

> [!TIP] Ask yourself: "Has anyone else seen this commit?" If no, amend freely. If yes, prefer a new commit.

> [!NOTE] Amending changes the commit hash. If a pull request references the old hash, links and review comments may point at a commit that no longer exists on the branch.
