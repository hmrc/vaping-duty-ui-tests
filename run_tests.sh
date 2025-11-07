#!/usr/bin/env bash
set -euo pipefail

BROWSER="${1:-chrome}"
ENVIRONMENT="${2:-local}"
HEADLESS="${3:-true}"  # default: false (visible browser)

sbt clean \
  -Dbrowser="${BROWSER}" \
  -Denvironment="${ENVIRONMENT}" \
  -Dbrowser.option.headless="${HEADLESS}" \
  -Dsecurity.assessment=false \
  test testReport