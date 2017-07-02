#!/bin/bash
# Inspired by https://github.com/ufoscout/docker-compose-wait/blob/master/wait.sh

timeout=${WAIT_HOSTS_TIMEOUT:-30}
waitBeforeHosts=${WAIT_BEFORE_HOSTS:-10}
waitAfterHosts=${WAIT_AFTER_HOSTS:-10}

echo "Waiting for ${waitBeforeHosts} seconds."
sleep $waitBeforeHosts

shift
cmd="$@"

host=mongo
port=27017
echo "Waiting ..."
seconds=0
while [ "$seconds" -lt "$timeout" ] && ! nc -z -w1 $host $port
do
  printf "."
  seconds=$((seconds+1))
  sleep 1
done

if [ "$seconds" -lt "$timeout" ]; then
  echo "mongodb is up!"
else
  echo "  ERROR: unable to connect to mongo" >&2
  exit 1
fi
echo "All hosts are up"

echo "Waiting for ${waitAfterHosts} seconds."
sleep $waitAfterHosts

exec $cmd
