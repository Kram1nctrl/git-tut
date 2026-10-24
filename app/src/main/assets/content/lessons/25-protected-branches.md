## Protect the important branches

Branch protection stops accidents: force pushes, direct commits to `main`, merging without review, or merging with failing tests.

On GitHub: **Settings → Branches → Add branch protection rule** (or **Rules → Rulesets** on newer interfaces).

Target the default branch, usually `main`.

## Rules worth enabling

- **Require a pull request before merging** — no direct pushes.
- **Require approvals** — at least one reviewer.
- **Dismiss stale approvals** — a new commit resets the approval.
- **Require review from Code Owners** — the right people review the right files.
- **Require status checks** — CI must pass before merge.
- **Require conversation resolution** — all comments addressed.
- **Require linear history** — no merge commits, squash or rebase only.
- **Block force pushes** — history cannot be rewritten.
- **Restrict deletions** — the branch cannot be deleted.

Start with a small set. Too many rules make small fixes painful; too few let mistakes through.

## Rulesets vs branch protection rules

GitHub is moving toward **rulesets**, which are more flexible:

- Apply to multiple branches at once via patterns (`release/*`).
- Layer rules and see which one applies.
- Add bypass lists for release bots or admins.
- Support organization-wide enforcement.

Both work. New repositories can start with rulesets.

## CODEOWNERS

A `CODEOWNERS` file automatically requests reviews from the right people:

```text
# .github/CODEOWNERS
*               @Kram1nctrl
/docs/          @docs-team
/app/src/main/  @android-team
```

When a PR touches matching files, those owners are requested. Combined with "Require review from Code Owners", this guarantees expert review.

## Required status checks

A check appears in the list only after it has run at least once. Typical required checks:

- `build`
- `test`
- `lint`
- `security-scan`

If a required check is flaky, it blocks every merge. Fix flakiness before making it required.

## Tags and releases

Protect version tags too:

- Restrict who can create tags matching `v*`.
- Prevent tag deletion.

Rewriting a released tag breaks everyone who depends on it.

## For solo projects

Even alone, protection helps:

- Require status checks, so you cannot merge broken code.
- Block force pushes on `main`.
- Skip required approvals if you have no reviewers.

You can still push to feature branches freely and open PRs against yourself.

## If you get blocked

```text
remote: error: GH006: Protected branch update failed
```

That means the rule is doing its job. Open a pull request instead of pushing directly, or ask a maintainer with bypass permission.

> [!TIP] Protect `main` on day one of a new repository. Adding rules after history is already messy is much harder.

> [!WARNING] Do not grant bypass permissions broadly. A bypass list that includes every admin defeats the purpose of protection.
