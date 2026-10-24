## Create a repository from scratch

Make a folder, move into it, and turn it into a Git repository:

```bash
mkdir my-first-repo
cd my-first-repo
git init
```

`git init` creates a hidden `.git` folder. That folder **is** the repository: history, configuration, branches, everything. Delete it and the folder becomes an ordinary directory again (with its files still there).

Check what happened:

```bash
git status
```

Git will say you are on branch `main` (or `master`) with no commits yet.

## Create your first file

```bash
echo "# My first repository" > README.md
git status
```

Now Git lists `README.md` under **Untracked files**. Git can see the file, but it is not part of any snapshot yet.

## Stage and commit

```bash
git add README.md
git commit -m "Add README"
```

Two commands, two different jobs:

- `git add` puts the current version of the file into the staging area.
- `git commit` records everything staged as a new snapshot.

Look at the result:

```bash
git log --oneline
```

You should see one commit with its short hash and your message.

## The everyday loop

Almost all Git work is this cycle, repeated:

1. Edit files.
2. `git status` to see what changed.
3. `git add <files>` to stage what belongs together.
4. `git commit -m "message"` to save a snapshot.
5. Repeat.

> [!TIP] Commit small and often. A commit should represent one logical change, not "everything I did today". Small commits are easier to review, revert, and understand later.

## A good commit message

The subject line should finish this sentence: "If applied, this commit will…"

- Good: `Add password reset form`
- Good: `Fix crash when opening empty project`
- Weak: `updates`
- Weak: `fixed stuff`

Keep the subject under about 50 characters. If you need more detail, leave a blank line and write a body.

## Start a repository from existing work

Already have a folder full of code? The process is the same:

```bash
cd existing-project
git init
git add .
git commit -m "Initial commit"
```

`git add .` stages everything in the current directory, including subfolders. Read the next lesson before you rely on it: it also stages things you may not want.

> [!WARNING] `git init` in your home folder is a common accident. It turns your entire home directory into one repository. If you do it, remove the `.git` folder immediately: `rm -rf ~/.git`. Double-check the path before running that.
