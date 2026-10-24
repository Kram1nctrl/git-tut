## What a conflict is

A conflict happens when two branches change the **same lines** of the same file in different ways. Git can combine independent changes automatically, but it will not guess which version of a genuinely competing change you want.

```bash
git merge feature/login
```

```text
Auto-merging src/login.kt
CONFLICT (content): Merge conflict in src/login.kt
Automatic merge failed; fix conflicts and then commit the result.
```

This is normal. Nothing is broken, and no work is lost.

## See what is conflicted

```bash
git status
```

Conflicted files appear under **Unmerged paths**, marked as `both modified`.

## Read the markers

Open the file and you will find:

```text
<<<<<<< HEAD
val timeout = 30
=======
val timeout = 60
>>>>>>> feature/login
```

- Between `<<<<<<< HEAD` and `=======` is **your current branch**.
- Between `=======` and `>>>>>>>` is **the incoming branch**.
- The markers themselves must be deleted.

## Resolve it

Edit the file until it contains exactly what you want. That might be one side, the other side, or a combination:

```kotlin
val timeout = 60
```

Then stage the resolved file:

```bash
git add src/login.kt
```

When every conflict is staged, finish the merge:

```bash
git commit
```

Git pre-fills a merge message. Save and close the editor to accept it.

## Abort and start over

If the merge is more trouble than it is worth:

```bash
git merge --abort
```

Everything returns to the state before the merge began.

## Use a merge tool

Visual diff tools make conflicts much easier:

```bash
git mergetool
```

Configure your preferred tool first, for example VS Code:

```bash
git config --global merge.tool vscode
git config --global mergetool.vscode.cmd 'code --wait "$MERGED"'
```

GitHub's web editor and GitHub Desktop also provide conflict resolution interfaces.

## Reduce future conflicts

- Pull often. Long-lived branches drift apart.
- Keep commits small and focused.
- Avoid reformatting entire files in a feature branch.
- Split work by file or module where possible.
- Merge `main` into your feature regularly, or rebase onto it.

## Verify before committing

```bash
git diff --check
```

This catches leftover conflict markers and whitespace errors. You can also search the project:

```bash
grep -rn "<<<<<<<" .
```

> [!TIP] A conflict is a question, not an error. Git is saying: "Two people changed this same spot. Which version is correct?" Answer it, stage the file, and continue.

> [!WARNING] Do not commit files that still contain `<<<<<<<`, `=======`, or `>>>>>>>` markers. The code will not compile, and the markers become part of history.
