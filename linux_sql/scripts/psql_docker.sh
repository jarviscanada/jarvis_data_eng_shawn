#!/bin/bash

PGSQL_DATA_PATH='/var/lib/postgresql/data'
SERVER_CONTAINER="jrvs-psql"
DATA_CONTAINER="jrvs-psql"

function getStatus(){
    CONTAINER_ID=$(docker ps -a | grep -v Exit | grep $SERVER_CONTAINER | awk '{print $1}')
    if [[ -z $CONTAINER_ID ]] ; then
        echo 'Not running.'
        return 1
    else
        echo "Running in container: $CONTAINER_ID"
        return 0
    fi
}


CONTAINER_ID=$(docker ps -a | grep -v Exit | grep ERVER_CONTAINER | awk '{print $1}')

if [ "$1" == "start" ];then
	docker start $DATA_CONTAINER
	getStatus 
elif [ "$1" == "stop" ];then
	if [[ -z $CONTAINER_ID ]]; then
		echo "already stopped"
	else
		docker stop $DATA_CONTAINER
		getStatus
	fi 
else 
	echo "wrong command"
fi

exit 0
