#!/usr/bin/env bash

API=${1:-"http://0.0.0.0:3000/"}
curl -i -X POST $API \
  --data-urlencode "token=Ld71Mi4Y10Ky1Il13ywhNcsC" \
  --data-urlencode "team_id=T0001" \
  --data-urlencode "channel_id=C2147483705" \
  --data-urlencode "channel_name=test" \
  --data-urlencode "timestamp=1355517523.000005" \
  --data-urlencode "user_id=U2147483697" \
  --data-urlencode "user_name=Steve" \
  --data-urlencode "text=googlebot: What is the air-speed velocity of an unladen swallow?" \
  --data-urlencode "trigger_word=googlebot:"
