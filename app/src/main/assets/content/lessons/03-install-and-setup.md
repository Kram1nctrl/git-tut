## Install Git

Pick the method for your system:

- **Windows:** download the installer from git-scm.com. It includes Git Bash, a terminal that understands Unix-style commands.
- **macOS:** run `git --version`. If Git is missing, macOS offers to install the command line tools, or use `brew install git`.
- **Ubuntu / Debian:** `sudo apt install git`
- **Fedora:** `sudo dnf install git`
- **Arch:** `sudo pacman -S git`

Then confirm it works:

```bash
git --version
```

## Tell Git who you are

Every commit carries a name and email address. Set them once, globally:

```bash
git config --global user.name "Kram1n"
git config --global user.email "you@example.com"
```

Use the email you want associated with your commits. If you care about privacy, GitHub lets you use a `noreply` address from your account settings.

## Set a sensible default branch

Modern Git and GitHub call the first branch `main`. Older Git installations default to `master`. Make the default explicit:

```bash
git config --global init.defaultBranch main
```

This only affects repositories you create from now on.

## Choose your editor

When Git needs a multi-line commit message, it opens an editor. The default is often Vim, which surprises new users. Point it at something familiar:

```bash
git config --global core.editor "nano"
```

If you use VS Code:

```bash
git config --global core.editor "code --wait"
```

## Make the terminal readable

Two small quality-of-life settings:

```bash
git config --global color.ui auto
git config --global pull.rebase false
```

The second one tells Git how to combine changes when you pull. We cover rebase later; `false` means "merge", which is the safest default while learning.

## Check your configuration

```bash
git config --list --show-origin
```

This prints every setting and the file it came from. There are three levels:

- **System** — applies to every user on the machine.
- **Global** — applies to your user account (`~/.gitconfig`).
- **Local** — applies to one repository (`.git/config`), and overrides the others.

## Line endings, briefly

Windows and Unix disagree about line endings. If you collaborate across systems and see entire files marked as changed, configure:

```bash
# Windows
git config --global core.autocrlf true

# macOS / Linux
git config --global core.autocrlf input
```

> [!TIP] You only need to do this setup once per computer. If a command later complains that it does not know who you are, it means this step was skipped.

> [!WARNING] Do not set `user.name` and `user.email` to fake values "just to test". Those values are baked into every commit and are annoying to rewrite later.
