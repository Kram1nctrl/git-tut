## Two ways to prove who you are

GitHub accepts two protocols:

- **HTTPS** — `https://github.com/user/repo.git`, authenticated with a token or credential helper.
- **SSH** — `git@github.com:user/repo.git`, authenticated with a key pair.

Both work. SSH is convenient for daily use; HTTPS is easier behind strict firewalls.

## HTTPS: passwords do not work

GitHub removed password authentication for Git operations. You need a **personal access token (PAT)** instead.

1. GitHub → Settings → Developer settings → Personal access tokens.
2. Choose **Fine-grained** (recommended) or **Classic**.
3. Grant the minimum scopes: `repo` for private repositories, `read:user` if needed.
4. Set an expiry.
5. Copy the token once; GitHub will not show it again.

Use it as the password when Git asks:

```text
Username: your-github-username
Password: ghp_xxxxxxxxxxxxxxxxxxxx
```

## Stop typing the token every time

Use a credential helper:

```bash
# Linux (stores credentials on disk, plain text)
git config --global credential.helper store

# macOS (Keychain)
git config --global credential.helper osxkeychain

# Windows (Git Credential Manager)
git config --global credential.helper manager
```

On Linux, `store` writes to `~/.git-credentials`. Prefer `cache` with a timeout if you share the machine:

```bash
git config --global credential.helper 'cache --timeout=3600'
```

## SSH: create a key

```bash
ssh-keygen -t ed25519 -C "you@example.com"
```

Press Enter to accept the default path, and choose a passphrase (recommended).

Start the agent and add the key:

```bash
eval "$(ssh-agent -s)"
ssh-add ~/.ssh/id_ed25519
```

## Add the public key to GitHub

Copy the **public** key:

```bash
cat ~/.ssh/id_ed25519.pub
```

GitHub → Settings → SSH and GPG keys → New SSH key → paste → save.

Test the connection:

```bash
ssh -T git@github.com
```

You should see a greeting with your username. The first time, SSH asks whether to trust the host; type `yes`.

> [!WARNING] Never share or commit the private key (`id_ed25519`, no `.pub`). Only the `.pub` file goes to GitHub.

## Switch an existing clone to SSH

```bash
git remote set-url origin git@github.com:Kram1nctrl/git-tut.git
```

## GitHub CLI: the shortcut

If you use `gh`, one command handles login and credential setup:

```bash
gh auth login
```

Choose GitHub.com, then HTTPS or SSH, and follow the browser prompt. It can also configure `git` for you.

## Two-factor authentication

Enable 2FA on your GitHub account. With 2FA, HTTPS Git operations still use tokens, not your account password. SSH keys are unaffected.

## Troubleshooting

- **403 forbidden** — token expired, missing scopes, or wrong account.
- **Permission denied (publickey)** — key not loaded (`ssh-add -l`), not added to GitHub, or using the wrong key.
- **Still asking for a password** — a stale credential is cached. Clear it:
  ```bash
  git credential reject
  ```
  Then paste the URL again and enter the token.

> [!TIP] Create a separate fine-grained token per machine or tool, with an expiry. If one leaks, revoke only that token.

> [!NOTE] Tokens are secrets. Never put them in a repository, a URL you commit, or a screenshot.
