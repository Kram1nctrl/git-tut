## A branch is just a pointer

This is the single most useful thing to understand about Git branches: a branch is a lightweight, movable pointer to a commit. It is not a copy of your files, and creating one costs almost nothing.

```text
        main
          ↓
A --- B --- C
```

`main` points at commit `C`. When you make a new commit on `main`, the pointer slides forward:

```text
              main
                ↓
A --- B --- C --- D
```

That is all a commit on a branch does.

## HEAD: where you are

`HEAD` is a pointer to the branch you currently have checked out. When you commit, `HEAD` tells Git which branch pointer to move.

```bash
git status
```

The first line says something like `On branch main`. That is `HEAD` speaking.

## Why branches matter

Without branches, everyone edits the same timeline. With branches, you can:

- build a feature without breaking `main`,
- experiment and throw the result away,
- review and test changes before merging,
- let several people work in parallel.

A branch can be created and deleted in milliseconds, so use them liberally.

## The classic layout

```text
main        A --- B ----------- M
                 \             /
feature           C --- D --- E
```

- `main` stays stable.
- `feature` holds work in progress.
- `M` is a merge commit that combines both lines.

## Detached HEAD

If you check out a commit hash instead of a branch, you get a "detached HEAD":

```bash
git switch --detach a3f9c21
```

You can look around and even commit, but those commits belong to no branch and are easy to lose. To keep the work, create a branch before leaving:

```bash
git switch -c rescue-work
```

> [!TIP] If `git status` says "HEAD detached at…", make a branch immediately if you have made any commits. Otherwise you may lose them.

## Branches are local by default

`git branch` lists local branches. To see remote-tracking branches too:

```bash
git branch -a
```

Remote branches appear as `origin/main`, `origin/feature`. They are read-only snapshots of what the remote had at your last fetch.

> [!NOTE] A branch name is just a label. `main`, `master`, `trunk`, and `develop` are conventions, not Git requirements. Teams usually protect `main` and treat it as the deployable line.
