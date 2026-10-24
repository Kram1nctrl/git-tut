## More than a code host

GitHub has planning and community tools that matter as much as the code. Knowing them makes you effective in a team, not just at the terminal.

## Projects

Projects are flexible boards, tables, and roadmaps built from issues and pull requests.

- **Board view** — columns like Todo, In Progress, Done.
- **Table view** — spreadsheet-like with custom fields.
- **Roadmap view** — timeline by target date.
- **Custom fields** — priority, estimate, sprint, status.
- **Automation** — move items when a PR opens, review is requested, or an issue closes.

A small team can run entirely on Projects: one board per product area, one roadmap per quarter.

## Organizations

An organization is a shared account for a team. It owns repositories, projects, and people.

- **Teams** — groups with repository permissions, for example `android-team` with write access to app repos.
- **Roles** — owner, member, billing manager, security manager.
- **Base permissions** — the default access for all members.
- **Repository roles** — read, triage, write, maintain, admin.
- **Audit log** — who changed what, useful for compliance.
- **Single sign-on** — connect to an identity provider on paid plans.

## Discussions

A forum inside the repository for questions, ideas, and announcements. Unlike issues, discussions do not need to be resolved; they are conversations.

Use discussions for "How do I…?" and issues for "This is broken" or "We should build…".

## Wikis

A simple documentation space attached to a repository. Good for long-form notes, though many teams prefer a `/docs` folder because it goes through pull review.

## Sponsors and funding

Open-source maintainers can receive sponsorships through GitHub Sponsors. Projects can also add a `FUNDING.yml` file to show a Sponsor button.

## Profile README

Create a repository named exactly like your username (`Kram1nctrl/Kram1nctrl`). Its `README.md` appears on your profile page. It is a lightweight portfolio: what you build, what you are learning, how to reach you.

## Community health files

GitHub surfaces these in the **Community** tab:

- `README.md`
- `LICENSE`
- `CONTRIBUTING.md`
- `CODE_OF_CONDUCT.md`
- `SECURITY.md`
- Issue and pull request templates

They signal that a project is maintained and welcoming.

## Notifications without noise

- **Watch** a repository: All Activity, Participating, or Ignore.
- **Custom watch**: only releases, only issues, only security alerts.
- **Notification filters**: `reason:review-requested`, `is:unread`.
- **Saved replies** for repetitive review comments.

> [!TIP] Watch repositories at "Participating" instead of "All Activity". You will still see mentions, reviews, and assigned issues without drowning in every comment.

> [!NOTE] Organizations can enforce policies across repositories: default branch names, allowed Actions, required workflows, and two-factor authentication.
