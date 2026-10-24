## The README is the front door

When someone opens your repository, the README renders below the file list. It is the first thing visitors read, and often the only thing.

A good README answers:

- What is this project?
- Who is it for?
- How do I run it?
- How do I contribute?
- What license applies?

## A solid starting structure

A README usually contains these sections, in this order:

- **Title** — the project name as a top-level heading.
- **One-line summary** — what it does and who it is for.
- **Features** — three to six short bullets.
- **Getting started** — clone, install, and run commands.
- **Usage** — the main flow, step by step.
- **Contributing** — how to report issues and open pull requests.
- **License** — the license name, linked to the LICENSE file.

Write it in Markdown. GitHub renders headings, lists, tables, code blocks, images, and badges.

## Badges

Badges are small status images. For example, a build status badge:

```markdown
![CI](https://github.com/USER/REPO/actions/workflows/android.yml/badge.svg)
```

They are useful when they show real information (build, coverage, version). Do not stack twenty of them at the top.

## Repository settings that matter

- **Description** — one line shown in search results and your profile.
- **Topics** — tags like `android`, `kotlin`, `git`, `education`. They help people discover the project.
- **Visibility** — public or private.
- **Default branch** — usually `main`.
- **Social preview** — an image shown when the repo is shared.
- **Releases** — versioned downloads, with notes.
- **License** — without one, others legally cannot reuse your code.

## Add a license

On GitHub: Add file → Create new file → type `LICENSE` → choose a template.

Common choices:

- **MIT** — permissive, simple, popular for small projects.
- **Apache-2.0** — permissive, includes a patent grant.
- **GPL-3.0** — copyleft; derivatives must stay open.

## Templates for contributors

GitHub picks up these files automatically:

- `.github/ISSUE_TEMPLATE/` — issue forms.
- `.github/PULL_REQUEST_TEMPLATE.md` — a checklist shown when opening a PR.
- `CONTRIBUTING.md` — how to set up and submit changes.
- `CODE_OF_CONDUCT.md` — community expectations.
- `SECURITY.md` — how to report vulnerabilities privately.

## Protect your default branch

Settings → Branches → Add branch protection rule:

- Require a pull request before merging.
- Require status checks to pass.
- Require review from code owners.
- Block force pushes.

This turns `main` into a stable line and pushes work through pull requests.

## Keep the repo tidy

- Delete merged branches.
- Archive abandoned repositories instead of leaving them half-alive.
- Use releases for versions instead of committing ZIP files.
- Put generated artifacts in `.gitignore`.

> [!TIP] Update the README whenever setup steps change. A README with commands that no longer work is worse than no README.

> [!NOTE] A repository without a license is "all rights reserved" by default, even if it is public. Add one if you want others to use your code.
