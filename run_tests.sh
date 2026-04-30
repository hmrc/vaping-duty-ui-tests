#!/usr/bin/env bash
set -euo pipefail
shopt -s nocasematch

# Default values
BROWSER="chrome"
ENVIRONMENT="local"
HEADLESS="true"
SECURITY_ASSESSMENT="false"
TAGS=""

POSITIONAL_ARGS=()

while [[ $# -gt 0 ]]; do
  case $1 in
    -b|--browser)
      [[ -n "${2-}" && ! "${2}" =~ ^- ]] || { echo "Error: Missing value for $1. See --help"; exit 1; }
      BROWSER="$2"
      shift 2
      ;;
    -e|--environment)
    [[ -n "${2-}" && ! "${2}" =~ ^- ]] || { echo "Error: Missing value for $1. See --help"; exit 1; }
      ENVIRONMENT="$2"
      shift 2
      ;;
    -h|--headless)
      HEADLESS="true"
      shift 1
      ;;
    -v|--visible|--headed)
      HEADLESS="false"
      shift
      ;;
    -s|--security)
      SECURITY_ASSESSMENT="true"
      shift
      ;;
    -t|--tags)
      [[ -n "${2-}" && ! "${2}" =~ ^- ]] || { echo "Error: Missing value for $1. See --help"; exit 1; }
      TAGS="$2"
      shift 2
      ;;
    --help)
      echo "Usage: $0 [-b|--browser <chrome|firefox|edge>] [-e|--environment <local|qa|staging>] [-h|--headless] [-v|--visible] [-t|--tags <tagName>]"
      echo "Examples:"
      echo "  $0 -b chrome -e local -h            # run with a headless browser (sets headless=true)"
      echo "  $0 -v, --visible, --headed          # run with a visible browser (sets headless=false)"
      echo "  $0 staging                          # auto detects environment"
      echo "  $0 firefox qa false                 # positional fallbacks (browser env headless)"
      echo "  $0 -s                               # enable security assessment"
      echo "  $0 -t CR  | CompleteReturn          # run only complete return tests"
      echo "  $0 -t CP  | ContactPreference       # run only contact preference tests"
      echo "  $0 -t EC  | EnrolmentClaim          # run only enrolment claim tests"
      echo "  $0 -t ALL | VapingDutyTest          # run all vaping duty tests"
      exit 0
      ;;
    -*|--*)
      echo "Unknown option: $1"
      exit 1
      ;;
    *)
      POSITIONAL_ARGS+=("$1")
      shift
      ;;
  esac
done

# ----  tag shortcuts ----
case $TAGS in
  CR)  TAGS="CompleteReturn" ;;
  CP)  TAGS="ContactPreference" ;;
  EC)  TAGS="EnrolmentClaim" ;;
  ALL) TAGS="VapingDutyTest" ;;
esac

# ---- Restore positionals ----
if ((${#POSITIONAL_ARGS[@]})); then
  set -- "${POSITIONAL_ARGS[@]}"
else
  set --
fi

# ---- Summary ----
echo "----------------------------------------"
echo "Running UI Tests"
echo "----------------------------------------"
echo "BROWSER     = ${BROWSER}"
echo "ENVIRONMENT = ${ENVIRONMENT}"
echo "HEADLESS    = ${HEADLESS}"
echo "ZAP         = ${SECURITY_ASSESSMENT}"
echo "TAGS        = ${TAGS:-none}"
echo "----------------------------------------"

if [[ -n "${TAGS}" ]]; then
  TEST_CMD="testOnly * -- -n ${TAGS}"
else
  TEST_CMD="test"
fi

sbt clean \
  -Dbrowser="${BROWSER}" \
  -Denvironment="${ENVIRONMENT}" \
  -Dbrowser.option.headless="${HEADLESS}" \
  -Dzap.proxy=true \
  -Dsecurity.assessment=${SECURITY_ASSESSMENT} \
  "${TEST_CMD}" testReport