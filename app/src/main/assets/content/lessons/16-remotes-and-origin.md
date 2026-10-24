## Remote = a named URL

A remote is a bookmark for another copy of your repository. The default name for the main one is `origin`, but the name itself is arbitrary.

```bash
git remote add origin https://github.com/Kram1nctrl/git-tut.git
```

Now `origin` points at that URL.

## Inspect remotes

```bash
git remote -v
```

```text
origin  https://github.com/Kram1nctrl/git-tut.git (fetch)
origin  https://github.com/Kram1nctrl/git-tut.git (push)
```

One remote can have different fetch and push URLs, which is useful for mirroring setups.

## Connect an existing local repo to GitHub

The usual sequence:

1. Create an empty repository on GitHub (no README, no .gitignore).
2. Add it as a remote.
3. Push your branch and set the upstream.

```bash
git remote add origin https://github.com/Kram1nctrl/git-tut.git
git branch -M main
git push -u origin main
```

`-u` (short for `--set-upstream`) links your local `main` to `origin/main`. After that, plain `git push` and `git pull` know where to go.

## Change the URL

```bash
git remote set-url origin git@github.com:Kram1nctrl/git-tut.git
```

Common when switching from HTTPS to SSH.

## Rename or remove

```bash
git remote rename origin upstream
git remote remove old-remote
```

Renaming `origin` to `upstream` is common in fork workflows, where `origin` becomes your fork.

## Multiple remotes

A fork setup usually looks like this:

```bash
git remote add upstream https://github.com/original-owner/project.git
git remote -v
```

- `origin` → your fork, where you push.
- `upstream` → the original project, where you fetch updates.

## Remote-tracking branches

After a fetch, Git stores the remote's branches locally as `origin/main`, `origin/dev`, and so on. These are read-only snapshots.

```bash
git branch -r
```

You cannot commit directly to `origin/main`; you commit to your local `main`, then push.

## The relationship in one picture

```text
local main  ---push--->  origin/main  (on GitHub)
local main  <--pull----  origin/main
```

`git fetch` updates the snapshot without touching your work. `git pull` fetches and then integrates. We cover both in detail two lessons from now.

> [!TIP] Run `git remote -v` whenever push or pull behaves unexpectedly. A wrong URL explains most authentication and "repository not found" errors.

> [!WARNING] Adding a remote does not upload anything. It only stores a URL. You still need `git push`.
