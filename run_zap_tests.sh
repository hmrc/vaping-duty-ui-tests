BROWSER="chrome"
ENVIRONMENT="local"
HEADLESS="true"


sbt clean \
  -Dbrowser="${BROWSER}" \
  -Denvironment="${ENVIRONMENT}" \
  -Dbrowser.option.headless="${HEADLESS}" \
  -Dzap.proxy=true \
  -Dsecurity.assessment=true \
  test testReport
