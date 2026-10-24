## Free hosting from a repository

GitHub Pages publishes static files (HTML, CSS, JavaScript, images) as a website at:

```text
https://username.github.io/repository-name/
```

For a repository named `username.github.io`, the site is served at the root: `https://username.github.io/`.

No server, no database, no build step required. It is ideal for documentation, portfolios, and small demos.

## Two publishing sources

**Deploy from a branch**

Settings → Pages → Source: Deploy from a branch → choose `main` and `/ (root)` or `/docs`.

**GitHub Actions**

Settings → Pages → Source: GitHub Actions. Then a workflow builds and deploys the site. This supports any static site generator: Vite, Astro, Hugo, Jekyll, and so on.

## A minimal site

```html
<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>GitTut</title>
  </head>
  <body>
    <h1>Hello from GitHub Pages</h1>
  </body>
</html>
```

Save it as `index.html`, push to `main`, enable Pages, wait a minute.

## Project sites and paths

A project site lives under a subpath. Absolute links like `/style.css` break. Use relative paths:

```html
<link rel="stylesheet" href="./style.css" />
```

Or configure your framework's base path, for example in Vite:

```js
export default {
  base: '/repository-name/',
}
```

## Custom domain

1. Add a `CNAME` file containing your domain, or set it in Settings → Pages.
2. At your DNS provider, point the domain:
   - apex domain → `A` records to GitHub's IPs, or `ALIAS`/`ANAME`;
   - `www` subdomain → `CNAME` to `username.github.io`.
3. Enable **Enforce HTTPS** once the certificate is issued.

DNS can take up to 24 hours, though it is often much faster.

## Actions deployment example

```yaml
name: Deploy Pages

on:
  push:
    branches: [main]

permissions:
  contents: read
  pages: write
  id-token: write

jobs:
  deploy:
    runs-on: ubuntu-latest
    environment:
      name: github-pages
    steps:
      - uses: actions/checkout@v4
      - uses: actions/configure-pages@v5
      - uses: actions/upload-pages-artifact@v3
        with:
          path: .
      - uses: actions/deploy-pages@v4
```

## Limits and rules

- Repositories must be public on free plans (private Pages requires a paid plan).
- Soft limit: about 1 GB per site, 100 GB bandwidth per month.
- No server-side code. Everything runs in the browser.
- Builds from a branch are limited in frequency; Actions deployments are more flexible.

## Common mistakes

- Publishing the repository root when the built site is in `dist/`.
- Broken absolute asset paths under a project subpath.
- Committing `node_modules/` and trying to publish it.
- Forgetting that Pages only serves static files.

> [!TIP] Use Pages for project documentation, and enable it from the `/docs` folder. It keeps docs versioned with the code and gives you a shareable URL instantly.

> [!WARNING] Never publish `.env` files or API keys through Pages. Everything in the published folder is public.
