## Issues are the project's memory

A GitHub issue records a bug, a feature request, a question, or a task. Unlike chat messages, issues are searchable, linkable, and stay open until resolved.

Every repository has an **Issues** tab. If it is missing, the maintainer disabled issues in settings.

## A useful bug report

Include:

- **What happened** — the actual behaviour.
- **What you expected** — the intended behaviour.
- **Steps to reproduce** — numbered, minimal.
- **Environment** — OS, version, device, browser.
- **Evidence** — logs, screenshots, stack traces.
- **Attempts** — what you already tried.

A report that cannot be reproduced cannot be fixed.

## A useful feature request

- The problem you are trying to solve.
- Your proposed solution.
- Alternatives you considered.
- Who benefits and how often.

Lead with the problem. "Add a dark mode toggle" is weaker than "The app is hard to read outdoors; a dark theme would help".

## Labels, milestones, assignees

- **Labels** classify: `bug`, `enhancement`, `documentation`, `good first issue`.
- **Milestones** group issues into a release or sprint.
- **Assignees** show who is working on it.
- **Projects** place issues on a board with columns like Todo, Doing, Done.

Maintainers control labels. As a contributor, you can still use them to find approachable work: filter by `label:"good first issue"`.

## Link issues to code

In a commit message or pull request description:

```text
Closes #42
Fixes #42
Resolves #42
```

When the pull request merges into the default branch, GitHub closes issue 42 automatically and links the conversation.

To reference without closing, just write `#42` or `GH-42`.

## Task lists

Inside an issue or PR description:

```markdown
- [x] Add parser
- [ ] Write tests
- [ ] Update docs
```

GitHub renders these as checkboxes and shows progress.

## Search and filter

```text
is:open is:issue label:bug sort:updated-desc
is:open assignee:@me
is:pr is:merged author:your-name
```

The search bar understands these qualifiers. They work across all of GitHub, not just one repository.

## Triage etiquette

- Search before opening a duplicate.
- One issue per problem.
- Do not "+1" with empty comments; use reactions instead.
- Close your own issue if you solve it, with a short explanation.
- If a maintainer asks for a reproduction, provide a minimal one.

> [!TIP] When you start work on an issue, comment "I'd like to take this" and wait for a maintainer to assign it. It avoids two people writing the same fix.

> [!NOTE] Issues are public by default. Never paste tokens, passwords, or personal data into one. Use GitHub's private security advisory flow for vulnerabilities.
