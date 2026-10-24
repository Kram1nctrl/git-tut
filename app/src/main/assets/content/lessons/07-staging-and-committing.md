## Stage precisely

Add one file:

```bash
git add README.md
```

Add several:

```bash
git add README.md src/app.kt
```

Add everything, including deletions:

```bash
git add -A
```

Add only files Git already tracks (skips new files):

```bash
git add -u
```

Check what you staged before committing:

```bash
git status
git diff --staged
```

## Commit

```bash
git commit -m "Add installation section to README"
```

A commit stores:

- the snapshot of staged files,
- the author name and email from your config,
- a timestamp,
- the commit message,
- a pointer to the previous commit (its parent).

That parent pointer is what makes history a chain.

## Write a message people can use

The seven rules of a great commit message, shortened:

1. Separate subject from body with a blank line.
2. Limit the subject to about 50 characters.
3. Capitalize the subject line.
4. Do not end the subject with a period.
5. Use the imperative mood: "Add", not "Added" or "Adds".
6. Wrap the body at about 72 characters.
7. Use the body to explain **what** and **why**, not how.

For a longer message, skip `-m` and let the editor open:

```bash
git commit
```

Or add multiple `-m` flags, which become separate paragraphs:

```bash
git commit -m "Fix race in timer start" -m "The service could start twice when the activity resumed quickly. Guard the start call with a mutex."
```

## Commit often, but not too often

A good commit is a checkpoint you would be happy to return to:

- It builds and passes tests.
- It does one thing.
- Its message explains the intent.

"WIP" commits are fine on a personal branch, but clean them up before sharing (interactive rebase covers that later).

## Quick commit of tracked changes

```bash
git commit -am "Update docs"
```

This stages all **tracked** modifications and deletions, then commits. It does not include new files. Use it for small, obvious changes; avoid it when you need control.

## Empty commits

Sometimes you want a commit that changes nothing, for example to trigger CI:

```bash
git commit --allow-empty -m "Trigger build"
```

## What not to commit

Never commit:

- passwords, API keys, tokens, or `.env` files with real values,
- build output (`build/`, `dist/`, `node_modules/`),
- editor and OS junk (`.idea/`, `.DS_Store`),
- huge binaries that change often.

Once a secret is committed, it is in history forever unless you rewrite it. Treat every commit as public.

> [!TIP] If you are unsure whether a file belongs in the repo, ask: "Would a new contributor need this file to build and run the project?" If yes, commit it. If it is generated, ignore it.

> [!WARNING] `git add .` from the wrong directory can stage an entire home folder or a virtual environment. Always run `git status` afterwards, before committing.
