#!/usr/bin/env bash
set -euo pipefail
shopt -s nocasematch

# Defaults
BROWSER="chrome"
ENVIRONMENT="local"
HEADLESS="true"
SECURITY_ASSESSMENT="true"

POSITIONAL_ARGS=()

while [[ $# -gt 0 ]]; do
  case $1 in
    -b|--browser)
      [[ -n "${2-}" && ! "${2}" =~ ^- ]] || { echo "Error: Missing value for $1"; exit 1; }
      BROWSER="$2"
      shift 2
      ;;
    -e|--environment)
      [[ -n "${2-}" && ! "${2}" =~ ^- ]] || { echo "Error: Missing value for $1"; exit 1; }
      ENVIRONMENT="$2"
      shift 2
      ;;
    -h|--headless)
      HEADLESS="true"
      shift
      ;;
    -v|--visible|--headed)
      HEADLESS="false"
      shift
      ;;
    -s|--security)
      # Simple toggle: -s means "enable security/DAST"
      SECURITY_ASSESSMENT="true"
      shift
      ;;
    --help)
      echo "Usage: $0 [-b chrome|edge|firefox] [-e local|qa|staging] [-h] [-v] [-s]"
      echo
      echo "Options:"
      echo "  -b, --browser     Browser (chrome|edge|firefox). Default: chrome"
      echo "  -e, --environment Environment (local|qa|staging). Default: local"
      echo "  -h, --headless    Run in headless mode"
      echo "  -v, --visible     Run with visible browser (headed)"
      echo "  -s, --security    Enable ZAP / DAST (security.assessment=true)"
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

# --- Summary ---
echo "----------------------------------------"
echo "Running UI Tests"
echo "----------------------------------------"
echo "BROWSER             = ${BROWSER}"
echo "ENVIRONMENT         = ${ENVIRONMENT}"
echo "HEADLESS            = ${HEADLESS}"
echo "SECURITY_ASSESSMENT = ${SECURITY_ASSESSMENT}"
echo "----------------------------------------"

TEST_FAILED=false

#
# ---  DAST / ZAP setup (START) ---
#
if [[ "${SECURITY_ASSESSMENT}" == "true" ]]; then
  echo "Security assessment enabled – starting local ZAP..."

  export ZAP_FAIL_ON_SEVERITY="Low"
  export ZAP_FORWARD_ENABLE="true"
  export ZAP_FORWARD_PORTS="9949"

  printf 'ZAP_FORWARD_PORTS=[%s]\n' "$ZAP_FORWARD_PORTS"

  if [[ -f alert-filters.json ]]; then
    export ZAP_LOCAL_ALERT_FILTERS="${PWD}/alert-filters.json"
    echo "Using filters from ${ZAP_LOCAL_ALERT_FILTERS}"
  fi

  if ! [[ -d dast-config-manager ]]; then
    git clone git@github.com:hmrc/dast-config-manager.git
  fi

  (
    cd dast-config-manager
    git fetch --all
    git reset --hard "origin/$(git rev-parse --abbrev-ref origin/HEAD | cut -d '/' -f 2)"
    git pull --force
    make local-zap-running
  )
fi
#
# --- DAST / ZAP setup (END) ---
#

#
# --- SBT call ---
#
sbt clean \
  -Dbrowser="${BROWSER}" \
  -Denvironment="${ENVIRONMENT}" \
  -Dbrowser.option.headless="${HEADLESS}" \
  -Dzap.proxy="${SECURITY_ASSESSMENT}" \
  -Dsecurity.assessment="${SECURITY_ASSESSMENT}" \
  test testReport || TEST_FAILED=true

#
# --- Stop ZAP if enabled ---
#
if [[ "${SECURITY_ASSESSMENT}" == "true" ]]; then
  echo "Stopping ZAP..."
  (
    cd dast-config-manager
    make local-zap-stop
  )
fi

#
# --- Final result ---
#
if [[ "${TEST_FAILED}" == "true" ]]; then
  echo "sbt tests failed — ZAP report may still be available in dast-config-manager/target"
  exit 1
fi

echo "Tests completed successfully"
