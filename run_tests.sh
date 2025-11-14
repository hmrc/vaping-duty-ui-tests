#!/usr/bin/env bash
set -euo pipefail
shopt -s nocasematch

# Default values
BROWSER="chrome"
ENVIRONMENT="local"
HEADLESS="true"

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
    --help)
      echo "Usage: $0 [-b|--browser <chrome|firefox|edge>] [-e|--environment <local|qa|staging>] [-h|--headless] [-v|--visible]"
      echo "Examples:"
      echo "  $0 -b chrome -e local -h            # run with a headless browser (sets headless=true)"
      echo "  $0 -v, --visible, --headed          # run with a visible browser (sets headless=false)"
      echo "  $0 staging                          # auto detects environment"
      echo "  $0 firefox qa false                 # positional fallbacks (browser env headless)"
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

# ---- Restore positionals ----
if ((${#POSITIONAL_ARGS[@]:-0})); then
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
echo "----------------------------------------"

sbt clean \
  -Dbrowser="${BROWSER}" \
  -Denvironment="${ENVIRONMENT}" \
  -Dbrowser.option.headless="${HEADLESS}" \
  -Dsecurity.assessment=false \
  test testReport