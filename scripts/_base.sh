#!/usr/bin/env bash

API=${1:-"http://0.0.0.0:3000/"}
T=$(basename $0)
D=$(printf %s "$(date +%s)")
case "$T" in 
  "help.sh")
    Q="help"
    ;;
  "create-qa.sh")
    Q="new qa Think of an animal that represents the Land Registry. Srsly ppl."
    ;;
  "create-binary.sh")
    Q="new binary Should feverish passengers at the airport be shown away?"
    ;;
  "vote.sh")
    Q="vote $POLLID YES"
    ;;
  "results.sh")
    Q="results $POLLID"
    ;;
  "rubbish.sh")
    Q="total rubbish"
    ;;
  *)
    echo "Unknown command $T"
    exit 1
esac

curl -i -X POST $API \
  --data-urlencode "token=K7d1YK01ylM14iI31wyNhscC" \
  --data-urlencode "team_id=T0001" \
  --data-urlencode "channel_name=scripts" \
  --data-urlencode "channel_id=C01PKU34X" \
  --data-urlencode "timestamp=$D.000005" \
  --data-urlencode "user_id=U2144738967" \
  --data-urlencode "user_name=opyate" \
  --data-urlencode "text=pollbot: $Q" \
  --data-urlencode "trigger_word=pollbot:" \
  --data-urlencode "service_id=2403647429" \
  --data-urlencode "team_domain=ateam" \


