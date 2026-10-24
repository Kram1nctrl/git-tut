## Git is the engine, GitHub is the garage

The shortest version:

- **Git** is the version control software that runs on your computer.
- **GitHub** is a website and cloud service that hosts Git repositories and adds collaboration tools on top.

You can use Git for years without ever creating a GitHub account. You cannot use GitHub without Git (well, not meaningfully).

## A useful analogy

Git is like a very good notebook: it records every version of your work. GitHub is like a shared library where you publish that notebook, let others read it, suggest edits, and track who is doing what.

## What GitHub adds

Git itself knows nothing about pull requests or issues. GitHub adds:

- **Remote hosting** — your repo lives on a server, accessible from anywhere.
- **Pull requests** — propose changes and discuss them before merging.
- **Issues** — bug reports, feature requests, and task tracking.
- **Actions** — run tests and builds automatically in the cloud.
- **Pages** — publish a static website straight from a repository.
- **Social features** — stars, forks, followers, and profiles.

> [!NOTE] Alternatives exist: GitLab, Bitbucket, Gitea, and Codeberg all host Git repositories too. The Git skills you learn here transfer directly.

## Which one do you need?

If you want to learn version control for your own projects, start with **Git**. If you want to collaborate, build a portfolio, or contribute to open source, add **GitHub**.

This course teaches both, but always keeps the line clear:

- Git commands (`add`, `commit`, `branch`, `merge`) work anywhere.
- GitHub features (`pull request`, `fork`, `Actions`) are platform-specific.

## Same words, different meanings

A few terms get confusing because both worlds use them:

- **Repository** means the same thing in both, but on GitHub it also refers to the web page for that repo.
- **Fork** is GitHub's copy of someone else's repo into your account. Git itself has no fork command.
- **Pull request** is a GitHub concept. Git has `git request-pull`, but it is not what people mean.
- **Clone** is Git copying a repository to your machine; GitHub just gives you the URL.

## Check the connection

Once you have a GitHub repository, Git talks to it through a remote URL:

```bash
git remote -v
```

That prints something like:

```text
origin  https://github.com/Kram1nctrl/git-tut.git (fetch)
origin  https://github.com/Kram1nctrl/git-tut.git (push)
```

`origin` is just a nickname. The URL is where the data actually lives.

> [!TIP] When something fails with "repository not found" or "permission denied", the problem is almost always the GitHub side: wrong URL, missing login, or no access to that repo. Git itself is usually fine.
