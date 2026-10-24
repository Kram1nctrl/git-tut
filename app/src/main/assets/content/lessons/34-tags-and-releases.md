## Tags mark releases

A tag is a permanent label on a commit, usually a version number. Unlike branches, tags do not move.

```bash
git tag v1.0.0
```

## Lightweight vs annotated

A lightweight tag is just a pointer:

```bash
git tag v1.0.0
```

An annotated tag stores a message, author, and date. Use this for releases:

```bash
git tag -a v1.0.0 -m "First public release"
```

## List and inspect

```bash
git tag
git tag -l "v1.*"
git show v1.0.0
```

## Tag an older commit

```bash
git tag -a v0.9.0 a3f9c21 -m "Last beta"
```

## Push tags

Tags are not pushed by default:

```bash
git push origin v1.0.0
git push --tags          # all tags
```

## Delete a tag

```bash
git tag -d v1.0.0                  # local
git push origin --delete v1.0.0    # remote
```

Deleting a published tag is disruptive; downstream builds may depend on it.

## Semantic versioning

`MAJOR.MINOR.PATCH`, for example `2.4.1`:

- **MAJOR** — incompatible changes.
- **MINOR** — new features, backwards compatible.
- **PATCH** — bug fixes, backwards compatible.

Pre-releases use suffixes: `v2.0.0-beta.1`, `v2.0.0-rc.1`.

## GitHub releases

A release wraps a tag with notes and downloadable assets. On GitHub: **Releases → Draft a new release**, choose or create a tag, write notes, attach files.

With the CLI:

```bash
gh release create v1.0.0 \
  --title "GitTut 1.0.0" \
  --notes "First release: 40 lessons, quizzes, offline cheat sheet." \
  app/build/outputs/apk/release/app-release.apk
```

You can also generate notes from merged pull requests:

```bash
gh release create v1.0.1 --generate-notes
```

## Release checklist

- [ ] Version bumped in the build file.
- [ ] Tests pass on CI.
- [ ] Changelog or release notes written.
- [ ] Tag created and pushed.
- [ ] Artifacts attached and verified.
- [ ] Installation instructions updated.

## Keep release branches separate

Some teams maintain `release/1.x` branches so patch releases do not pull in new features from `main`. Tag the release from that branch.

> [!TIP] Always create annotated tags for releases. They record who tagged what and when, which lightweight tags do not.

> [!WARNING] Moving or re-tagging a published version breaks reproducibility. If a release is wrong, publish a new patch version instead of rewriting the old tag.
