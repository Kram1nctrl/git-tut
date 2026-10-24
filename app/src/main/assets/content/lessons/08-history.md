## See the history

```bash
git log
```

The default output is verbose. Most people use a compact version:

```bash
git log --oneline
```

Add a graph to see branches and merges:

```bash
git log --oneline --graph --decorate --all
```

That last command is worth memorizing or aliasing. It shows the whole shape of your repository, not just the current branch.

## Filter the history

```bash
git log -5                      # last five commits
git log --author="Kram1n"       # commits by one person
git log --since="2 weeks ago"   # date range
git log -- src/app.kt           # commits touching one file
git log -S "apiKey"             # commits that added or removed a string
git log --grep="fix"            # search commit messages
```

`-S` is the "pickaxe". It is how you find when a particular line or identifier appeared.

## Inspect a single commit

```bash
git show a3f9c21
```

This prints the commit metadata, message, and the diff it introduced. Add `--stat` for just the file summary:

```bash
git show --stat a3f9c21
```

## Who changed this line?

```bash
git blame src/app.kt
```

Each line is annotated with the commit, author, and date that last touched it. Blame is a tool for understanding, not for assigning guilt.

To blame a range of lines:

```bash
git blame -L 40,60 src/app.kt
```

## Compare commits and branches

```bash
git diff HEAD~1 HEAD        # last commit vs the one before
git diff main feature       # two branches
git log main..feature       # commits on feature that are not on main
git log --left-right --graph main...feature
```

## Follow a file through renames

If a file was renamed, plain `git log -- path` stops at the rename. Use:

```bash
git log --follow -- src/old-name.kt
```

## A compact, readable log alias

Typing long flags gets old. Save this alias once:

```bash
git config --global alias.lg "log --oneline --graph --decorate --all"
```

Then use:

```bash
git lg
```

## History is local until you push

Everything above reads your local repository. You can inspect, compare, and search without network access. This is one of the biggest advantages of distributed version control.

> [!TIP] When you are lost, `git log --oneline --graph --all` is the map. It shows where branches point and how commits connect.

> [!NOTE] Commit hashes are unique but not secret. Sharing a hash is a precise way to say "this exact snapshot".
