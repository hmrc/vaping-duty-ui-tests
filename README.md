**This is the template README. Please update this with project specific content.**

# vaping-duty-ui-tests

Vaping Duty UI journey tests.

## Pre-requisites

### Services

Start Mongo Docker container as follows:

```bash
docker run --rm -d -p 27017:27017 --name mongo percona/percona-server-mongodb:6.0
```

Start `VAPING_DUTY` services as follows:

```bash
sm2 --start VAPING_DUTY_ALL
```

## Tests

To run the full set of UI tests:
#### Default (chrome, local, headless browser)
```
./run_tests.sh
```

* Argument `<browser>` must be `chrome`, `edge`, or `firefox`.
* Argument `<environment>` must be `local`, `qa` or `staging`.
* Argument `<headless>` must be  `-h`.
* Argument `<Visible>` must be  `-v`.

#### Run visible Chrome browser in QA environment
```
./run_tests.sh -b chrome -e qa -v
```

## Scalafmt

Check all project files are formatted as expected as follows:

```bash
sbt scalafmtCheckAll scalafmtCheck
```

Format `*.sbt` and `project/*.scala` files as follows:

```bash
sbt scalafmtSbt
```

Format all project files as follows:

```bash
sbt scalafmtAll
```

## License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
