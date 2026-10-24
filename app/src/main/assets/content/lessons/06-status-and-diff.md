## Your dashboard

`git status` answers three questions at once:

- Which branch am I on?
- What is staged for the next commit?
- What is changed but not staged, and what is completely untracked?

Run it before and after every Git action. It is cheap, safe, and prevents most beginner mistakes.

```bash
git status
```

## The short format

Once you are comfortable, use the compact version:

```bash
git status -sb
```

The output uses two columns of letters:

```text
 M README.md
M  src/app.kt
A  src/new-file.kt
?? notes.txt
D  old-file.txt
```

- Left column = staging area, right column = working directory.
- `M` modified, `A` added, `D` deleted, `??` untracked, `R` renamed.
- ` M` means "modified but not staged"; `M ` means "staged modification".

> [!TIP] Read the columns like two switches. The letter on the left is what the next commit would contain; the letter on the right is what is still only on disk.

## Compare with git diff

`git diff` shows line-level changes.

```bash
git diff                  # unstaged changes
git diff --staged         # staged changes
git diff HEAD             # everything since the last commit
```

Useful variations:

```bash
git diff --stat           # summary: which files, how many lines
git diff --name-only      # file names only
git diff main..feature    # difference between two branches
git diff HEAD~1 HEAD      # difference between the last two commits
```

## Reading the output

```diff
diff --git a/README.md b/README.md
index 3b18e51..a9c2f31 100644
--- a/README.md
+++ b/README.md
@@ -1,3 +1,4 @@
 # My project
+Now with installation instructions.
```

- Lines starting with `+` were added.
- Lines starting with `-` were removed.
- The `@@` line tells you where in the file the hunk begins.

## Ignore the noise

Some diffs are unreadable because of whitespace or formatting churn:

```bash
git diff -w              # ignore whitespace changes
git diff --word-diff     # highlight changed words, not lines
```

## When status surprises you

- **A file you deleted still shows up.** Git tracks the deletion; stage it with `git add -A`.
- **A file you just created is not listed.** It is probably ignored. Check with `git check-ignore -v path/to/file`.
- **Hundreds of files appear at once.** You may have opened the wrong folder, or a build tool generated output. Do not commit blindly; fix `.gitignore` first.

## A habit worth building

Before staging, run:

```bash
git status
git diff
```

After staging, run:

```bash
git status
git diff --staged
```

That four-command rhythm catches accidental secrets, debug prints, and unrelated changes before they enter history.

> [!WARNING] `git diff` never shows untracked files. A brand-new file can be full of secrets and still be invisible to diff until you stage it. `git status` is the only command that will warn you it exists.
