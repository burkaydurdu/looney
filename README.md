# looney
[![Netlify Status](https://api.netlify.com/api/v1/badges/21b162db-0bab-4d16-ac0b-1c52c640670e/deploy-status)](https://app.netlify.com/sites/looneyy/deploys)

## Install Leiningen
* [Debian](https://packages.debian.org/stable/leiningen) (since Buster)
* [Ubuntu 18.04](https://launchpad.net/ubuntu/+source/leiningen-clojure)
* [asdf](https://github.com/miorimmax/asdf-lein)
* [nix](https://github.com/NixOS/nixpkgs/blob/master/pkgs/development/tools/build-managers/leiningen/default.nix)
* [Homebrew](https://github.com/Homebrew/homebrew-core/blob/master/Formula/leiningen.rb) (macOS, `brew install leiningen`)
* [Archlinux](https://www.archlinux.org/packages/community/any/leiningen/)
* [Chocolatey](http://chocolatey.org/packages/lein) (Windows, `choco install lein`)
* [Scoop](https://scoop.sh/) (Windows, `scoop install leiningen`)
* [FreeBSD Ports](http://www.freshports.org/devel/leiningen/)
* [SDKMAN!](http://sdkman.io/)
* [openSUSE](https://build.opensuse.org/package/show/devel:languages:clojure/leiningen)

## Install NPM, Node

[Package Manager](https://nodejs.org/tr/download/package-manager)

## Create Project

Re-frame template
```sh
lein new re-frame fro-cix +10x +test +kondo +cider +routes
```

## Development

### Running the App

Start a temporary local web server, build the app with the `dev` profile, and serve the app,
browser test runner and karma test runner with hot reload:

```sh
npm install
npx shadow-cljs watch app
```

Please be patient; it may take over 20 seconds to see any output, and over 40 seconds to complete.

When `[:app] Build completed` appears in the output, browse to
[http://localhost:8280/](http://localhost:8280/).

[`shadow-cljs`](https://github.com/thheller/shadow-cljs) will automatically push ClojureScript code
changes to your browser on save. To prevent a few common issues, see
[Hot Reload in ClojureScript: Things to avoid](https://code.thheller.com/blog/shadow-cljs/2019/08/25/hot-reload-in-clojurescript.html#things-to-avoid).

Opening the app in your browser starts a
[ClojureScript browser REPL](https://clojurescript.org/reference/repl#using-the-browser-as-an-evaluation-environment),
to which you may now connect.


#### Connecting to the browser REPL from VS Code with Calva

See the [re-frame-template README](https://github.com/day8/re-frame-template) for [Calva](https://github.com/BetterThanTomorrow/calva) instuctions. See also https://calva.io for Calva documentation.


#### Connecting to the browser REPL from other editors

See
[Shadow CLJS User's Guide: Editor Integration](https://shadow-cljs.github.io/docs/UsersGuide.html#_editor_integration).
Note that `npm run watch` runs `npx shadow-cljs watch` for you, and that this project's running build ids is
`app`, `browser-test`, `karma-test`, or the keywords `:app`, `:browser-test`, `:karma-test` in a Clojure context.

Alternatively, search the web for info on connecting to a `shadow-cljs` ClojureScript browser REPL
from your editor and configuration.


#### Connecting to the browser REPL from a terminal

1. Connect to the `shadow-cljs` nREPL:
    ```sh
    lein repl :connect localhost:8777
    ```
    The REPL prompt, `shadow.user=>`, indicates that is a Clojure REPL, not ClojureScript.

2. In the REPL, switch the session to this project's running build id, `:app`:
    ```clj
    (shadow.cljs.devtools.api/nrepl-select :app)
    ```
    The REPL prompt changes to `cljs.user=>`, indicating that this is now a ClojureScript REPL.
3. See [`user.cljs`](dev/cljs/user.cljs) for symbols that are immediately accessible in the REPL
without needing to `require`.

### Running Tests

Build the app with the `prod` profile, start a temporary local web server, launch headless
Chrome/Chromium, run tests, and stop the web server:

```sh
npm install
npm run ci
```

Please be patient; it may take over 15 seconds to see any output, and over 25 seconds to complete.

Or, for auto-reload:
```sh
npm install
npm run watch
```

Then in another terminal:
```sh
karma start
```

### Running `shadow-cljs` Actions

See a list of [`shadow-cljs CLI`](https://shadow-cljs.github.io/docs/UsersGuide.html#_command_line)
actions:
```sh
npx shadow-cljs --help
```

Please be patient; it may take over 10 seconds to see any output. Also note that some actions shown
may not actually be supported, outputting "Unknown action." when run.

Run a shadow-cljs action on this project's build id (without the colon, just `app`):
```sh
npx shadow-cljs <action> app
```
### Debug Logging

The `debug?` variable in [`config.cljs`](src/cljs/fro_cix/config.cljs) defaults to `true` in
[`dev`](#running-the-app) builds, and `false` in [`prod`](#production) builds.

Use `debug?` for logging or other tasks that should run only on `dev` builds:

```clj
(ns fro-cix.example
  (:require [fro-cix.config :as config])

(when config/debug?
  (println "This message will appear in the browser console only on dev builds."))
```

## Production

Build the app with the `prod` profile:

```sh
npm install
npm run release
```

Please be patient; it may take over 15 seconds to see any output, and over 30 seconds to complete.

The `resources/public/js/compiled` directory is created, containing the compiled `app.js` and
`manifest.edn` files.
