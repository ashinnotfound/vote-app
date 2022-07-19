#!/bin/bash

function instruction() {
    echo "instruction:"
    echo "you should deploy it first : bash vote_run.sh deploy"
    echo "  bash vote_run.sh add name"
    echo "  bash vote_run.sh vote name"
    echo "  bash vote_run.sh appoint"
}

case $1 in
add)
  [ $# -ne 2 ] && { instruction; }
    ;;
vote)
  [ $# -ne 2 ] && { instruction; }
    ;;
appoint)
  [ $# -ne 1 ] && { instruction; }
    ;;
deploy)
    ;;
*)
  instruction
    ;;
esac

java -cp 'apps/*:conf/:lib/*' com.chenxun.www.client.VoteClient $@