## Review is a conversation

A pull request review is not an exam. It is a structured conversation about whether a change is correct, maintainable, and safe to merge.

As an author, your job is to make review easy. As a reviewer, your job is to be useful and specific.

## For authors

- Keep the PR focused on one change.
- Write a description that explains **why**, not just what.
- Self-review the diff before requesting review.
- Run the tests and linters locally.
- Respond to every comment, even if just "done" or "good point, changed".
- Do not force-push while a review is in progress unless necessary; it hides comment history. Prefer adding commits, then squash at merge time.

## For reviewers

- Start with the big picture: is the approach right?
- Comment on specifics with line references.
- Distinguish blocking issues from nitpicks: prefix optional comments with `nit:`.
- Suggest concrete code, not vague dissatisfaction.
- Approve when it is good enough, not perfect.
- Review the tests as carefully as the code.

## GitHub review tools

On the **Files changed** tab you can:

- Click a line number to comment on it.
- Drag to select a range and comment on multiple lines.
- Use **Insert a suggestion** to propose an exact replacement.
- Start a review and choose **Comment**, **Approve**, or **Request changes**.
- Resolve conversations once addressed.

Suggestions render as a diff that the author can apply with one click. To write one, create a fenced code block whose language is `suggestion` and put the replacement line inside:

```kotlin
val timeout = 60
```

GitHub turns that block into an "Apply suggestion" button. The language tag must be exactly `suggestion` for the button to appear.

## Status checks

A PR shows checks from CI: tests, lint, build, security scans. A red check means something failed; click **Details** to see logs.

If checks are required by branch protection, the merge button stays disabled until they pass.

## Merge readiness checklist

- [ ] Description explains the change.
- [ ] Tests pass.
- [ ] New behaviour is covered by tests.
- [ ] Docs updated if needed.
- [ ] No secrets or debug code.
- [ ] At least one approval.
- [ ] Branch up to date with the base branch.

## Common review comments and what they mean

- "Can you add a test for this?" — behaviour is unverified.
- "This duplicates existing logic." — reuse instead of reinventing.
- "Nit: naming." — optional polish.
- "What happens if X fails?" — error handling gap.
- "Let's split this PR." — too much scope for one review.

## Giving feedback well

Compare:

- Weak: "This is wrong."
- Better: "This will throw if `user` is null. Could we guard it or use `requireNotNull`?"

Specific, actionable, and kind. Assume good intent.

> [!TIP] Review your own PR first. You will catch leftovers like debug logs, commented-out code, and accidental formatting changes before anyone else sees them.

> [!WARNING] Never approve a PR you do not understand. "LGTM" on unfamiliar code is how bugs reach production.
