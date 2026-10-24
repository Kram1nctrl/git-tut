## Three places, not one

New Git users think of "saving" as a single action. Git actually moves your work through three places:

1. **Working directory** — the real files on disk that you edit.
2. **Staging area** (also called the index) — a preparation zone for the next commit.
3. **Repository** — the committed history inside `.git`.

The flow is:

```text
working directory  --git add-->  staging area  --git commit-->  repository
```

## Why a staging area at all?

Because real work is messy. You fix a bug and also rename a variable and also update the README. Those are three different changes. Staging lets you choose exactly what goes into each commit, even when the edits live in the same file.

```bash
git add src/login.kt
git commit -m "Fix login timeout"

git add README.md
git commit -m "Document login behaviour"
```

Two clean commits instead of one confusing blob.

## See the difference between the places

```bash
git status
```

- Changes in the working directory: **not staged for commit**.
- Changes in staging: **changes to be committed**.

To see the actual lines:

```bash
git diff            # working directory vs staging
git diff --staged   # staging vs last commit
```

This distinction explains a classic confusion: you edit a file, run `git diff`, see your change, then run `git diff --staged` and see nothing. The change is not staged yet.

## Staging parts of a file

Sometimes only some lines should be committed. Use patch mode:

```bash
git add -p src/login.kt
```

Git shows each chunk and asks what to do. The useful answers:

- `y` — stage this chunk
- `n` — skip it
- `s` — split it into smaller chunks
- `q` — quit

## Unstaging

Changed your mind about a file?

```bash
git restore --staged src/login.kt
```

The file stays edited in your working directory, but leaves the staging area. Nothing is lost.

## A picture that sticks

Think of packing a box:

- Your room is the **working directory**.
- The box you are filling is the **staging area**.
- The sealed, labelled box on the shelf is the **commit**.

You can put things in the box, take them out again, and only seal it when the contents make sense.

> [!TIP] `git status` is the most useful command in Git. Run it constantly. It tells you which of the three places your changes are currently in.

> [!WARNING] `git commit -a` stages and commits all tracked files in one step. It skips new, untracked files. Convenient, but easy to misuse when you meant to craft a careful commit.
