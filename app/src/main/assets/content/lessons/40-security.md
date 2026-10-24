## Your account is the keys to everything

A compromised GitHub account can read private code, push malicious commits, and publish releases in your name. Treat security as part of learning Git, not an advanced topic.

## Turn on two-factor authentication

Settings → Password and authentication → Enable 2FA.

Use an authenticator app or a hardware security key. SMS works but is weaker. Save the recovery codes somewhere offline.

Organization owners can require 2FA for every member.

## Tokens, not passwords

Git operations over HTTPS use personal access tokens:

- Prefer **fine-grained** tokens scoped to specific repositories.
- Grant the minimum permissions needed.
- Set an expiry and rotate regularly.
- Use one token per machine or tool.
- Revoke tokens you no longer use.

```bash
gh auth status          # check scopes
```

If a token leaks, revoke it immediately, then rotate any secrets it could reach.

## SSH key hygiene

- Use a passphrase on your key.
- One key per device.
- Remove keys for devices you no longer use.
- Prefer `ed25519` keys.

```bash
ssh-keygen -t ed25519 -C "laptop"
ssh -T git@github.com
```

## Never commit secrets

Common leaks:

- `.env` files with real credentials,
- API keys hardcoded in source,
- keystores and signing passwords,
- CI configuration with tokens,
- database dumps.

Protect yourself:

```bash
git check-ignore -v .env
git log -S "api_key" --oneline
```

If a secret is committed, assume it is compromised. Rotate it first, then clean history.

## Secret scanning and push protection

GitHub scans public repositories for known token formats and can block pushes containing them. Enable **Push protection** in Settings → Code security.

For private repos on paid plans, secret scanning alerts notify you when a token appears.

## Dependabot

Dependabot opens pull requests when your dependencies have updates or known vulnerabilities.

`.github/dependabot.yml`:

```yaml
version: 2
updates:
  - package-ecosystem: gradle
    directory: /
    schedule:
      interval: weekly
```

Enable alerts under Settings → Code security → Dependabot alerts.

## Code scanning

CodeQL analyses your code for security bugs on every push. Set it up from the **Security** tab with a starter workflow. It supports Java, Kotlin, JavaScript, Python, Go, and more.

## Signed commits

```bash
git config --global commit.gpgsign true
git config --global user.signingkey <KEY_ID>
```

Signed commits show **Verified** on GitHub. They prove the commit came from the key holder, not just someone who typed your name in `user.email`.

## Reporting a vulnerability

Do not open a public issue. Use the repository's **Security → Report a vulnerability** flow (private advisory), or the contact listed in `SECURITY.md`.

## Quick audit of a repository

- Is branch protection enabled on `main`?
- Are Actions permissions restricted?
- Are secrets stored in repository settings, not in code?
- Is 2FA required for contributors?
- Are dependencies monitored?

> [!TIP] Assume every public repository is being scanned by bots. A token pushed and deleted within a minute may already be copied. Rotate first, clean second.

> [!WARNING] `git rm` a secret and committing does not remove it from history. Anyone can check out the old commit. History rewriting is required, and rotation is still mandatory.
