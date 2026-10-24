## The fork-and-pull workflow

You usually cannot push directly to someone else's repository. Instead you work through a **fork** and a **pull request**:

1. Fork the repository on GitHub (creates your own copy).
2. Clone your fork.
3. Create a branch for your change.
4. Commit and push the branch to your fork.
5. Open a pull request against the original repository.
6. Respond to review, then the maintainer merges it.

This is how nearly all open-source contribution works.

## Step 1: Fork

On the repository page, click **Fork**. Choose your account. You now have `github.com/your-name/project`.

A fork is a full copy on GitHub, not a branch. It remembers where it came from, which is what makes pull requests possible.

## Step 2: Clone and connect upstream

```bash
git clone https://github.com/your-name/project.git
cd project
git remote add upstream https://github.com/original-owner/project.git
git remote -v
```

- `origin` → your fork (push here).
- `upstream` → the original project (fetch updates from here).

## Step 3: Work on a branch

```bash
git switch -c fix/typo-in-readme
```

Never commit directly to `main` in a fork. A clean branch per contribution keeps pull requests focused and easy to review.

## Step 4: Push to your fork

```bash
git push -u origin fix/typo-in-readme
```

## Step 5: Open the pull request

After pushing, GitHub shows a **Compare & pull request** button. If not:

1. Go to the original repository.
2. Open the **Pull requests** tab.
3. Click **New pull request** → **compare across forks**.
4. Set base: `original-owner/project` `main`.
5. Set head: `your-name/project` `fix/typo-in-readme`.

Write a clear title and description:

- What changed and why.
- How you tested it.
- Screenshots for UI changes.
- `Closes #12` to auto-close the related issue on merge.

## Keep the PR small

Reviewers are volunteers. A 2,000-line pull request may sit for weeks. A focused 150-line change gets reviewed in hours.

If a feature is large, split it into a series of small PRs, or open a draft PR early to discuss direction.

## Draft pull requests

Open a draft when the work is not ready for review:

- Use the dropdown next to **Create pull request** → **Create draft pull request**.
- Mark it ready when checks pass and the description is complete.

Drafts signal "feedback welcome, but do not merge yet".

## What happens after opening

- Automated checks run (tests, lint, builds).
- Reviewers comment, request changes, or approve.
- You push more commits to the same branch; the PR updates automatically.
- The maintainer merges (merge commit, squash, or rebase).

## Merge strategies

- **Create a merge commit** — preserves every commit and adds a merge commit.
- **Squash and merge** — combines the branch into one commit on `main`. Popular for messy WIP branches.
- **Rebase and merge** — replays commits onto `main` without a merge commit.

Follow the project's convention. If unsure, squash is usually safe.

## After the merge

```bash
git switch main
git pull upstream main
git push origin main
git branch -d fix/typo-in-readme
```

Delete the branch on GitHub too; the PR page offers a button.

> [!TIP] Read `CONTRIBUTING.md` before your first pull request. It usually states the branch naming, commit style, and test requirements the maintainers expect.

> [!WARNING] Do not open a pull request from your fork's `main` branch if you plan to contribute again. It becomes hard to keep clean. Always use a dedicated branch.
