#!/bin/bash
cd /home/kavia/workspace/code-generation/noteease-106390-5853bc88/notes_frontend
./gradlew lint
LINT_EXIT_CODE=$?
if [ $LINT_EXIT_CODE -ne 0 ]; then
   exit 1
fi

