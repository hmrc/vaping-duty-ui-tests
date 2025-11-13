#!/usr/bin/env bash
set -euo pipefail

# Default values
BROWSER="chrome"
ENVIRONMENT="local"
HEADLESS="true"

POSITIONAL_ARGS=()

while [[ $# -gt 0 ]]; do
  case $1 in
    -b|--browser)
      BROWSER="$2"
      shift 2
      ;;
    -e|--environment)
      ENVIRONMENT="$2"
      shift 2
      ;;
    -h|--headless)
      HEADLESS="$2"
      shift 2
      ;;
    --help)
      echo "Usage: $0 [-b|--browser <chrome|firefox|edge>] [-e|--environment <env>] [-h|--headless <true|false>]"
      echo "Example: $0 -b chrome -e local -h true"
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

set -- "${POSITIONAL_ARGS[@]}"

for arg in "${POSITIONAL_ARGS[@]}"; do
  if [[ "$arg" =~ ^(chrome|firefox|edge)$ ]]; then
    BROWSER="$arg"
  elif [[ "$arg" =~ ^(local|qa|staging)$ ]]; then
    ENVIRONMENT="$arg"
  elif [[ "$arg" =~ ^(true|false)$ ]]; then
    HEADLESS="$arg"
  fi
done

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