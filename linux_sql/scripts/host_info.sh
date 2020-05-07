#!/bin/bash

#assign CLI arguments to variables

PGSQL_DATA_PATH='/var/lib/postgresql/data'
CONTAINER="jrvs-psql"
psql_host="$1"
psql_port="$2"
db_name="$3"
psql_user="$4"
psql_password="$5"

#save hostname to a variable
hostname=$(hostname -f)

#save number of CPU to a variable
lscpu_out=`lscpu`
mem_info=`free -h`
cpu_info=`vmstat -t`
disk_info=`vmstat -d`
#note: `xargs` is a trick to remove leading and trailing white spaces
#hardware
cpu_number=$(echo "$lscpu_out"  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
hostname=$(hostname -f)
cpu_number=$(echo "$lscpu_out"  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(echo "$lscpu_out"  | egrep "Architecture:" | awk '{print $2}' | xargs)
cpu_model=$(echo "$lscpu_out"  | egrep "Model name:" | awk -F':' '{print $2}' | xargs)
cpu_mhz=$(echo "$lscpu_out"  | egrep "MHz:" | awk '{print $3}' | xargs)
l2_cache=$(echo "$lscpu_out"  | egrep "L2 cache:" | awk '{print $3}' | sed 's/[^0-9]*//g' | xargs)
total_mem=$(echo "$mem_info"  | egrep "Mem:" | awk '{print $2}' | sed 's/[^0-9]*//g' | xargs)
timestamp=$(date +%F_%T)

#Executing SQL from shell scripts
psql -h $psql_host -U $psql_user -d $db_name -p $psql_port $psql_password << EOF
INSERT INTO host_info (hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, l2_cache, total_mem, timestamp) VALUES ('$hostname', '$cpu_number', '$cpu_architecture', '$cpu_model', '$cpu_mhz', '$l2_cache', '$total_mem', '$timestamp');
EOF
