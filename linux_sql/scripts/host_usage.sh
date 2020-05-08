#!/bin/bash -l
source $HOME/.bash_profile
#assign CLI arguments to variables

PGSQL_DATA_PATH='/var/lib/postgresql/data'
CONTAINER="jrvs-psql"
psql_host="$1"
psql_port="$2"
db_name="$3"
psql_user="$4"


#save hostname to a variable
hostname=$(hostname -f)

#save number of CPU to a variable
lscpu_out=`lscpu`
mem_info=`free -h`
cpu_info=`vmstat -t`
disk_info=`vmstat -d`
#note: `xargs` is a trick to remove leading and trailing white spaces
#hardware
memory_free=$(echo "$mem_info"  | egrep "Mem:" | awk '{print $4}' | sed 's/[^0-9]\.*//g' |  xargs)
cpu_idle=$(echo "$cpu_info"  | egrep "1" | awk '{print $15}' | xargs)
cpu_kernel=$(echo "$cpu_info"  | egrep "1" | awk '{print $14}' | xargs)
disk_io=$(echo "$disk_info"  | egrep "sda" | awk '{print $10}' | sed 's/[^0-9]\.*//g' | xargs)
disk_available=$(df -h /dev/sda1 --output=source,fstype,size,used,avail,pcent | egrep "/dev/sda1" | awk '{print $5}' | sed 's/[^0-9]\.*//g' | xargs)

#Executing SQL from shell scripts
psql -h $psql_host -U $psql_user -d $db_name -p $psql_port  << EOF
INSERT INTO host_usage (host_id, memory_free, cpu_idle, cpu_kernel, disk_io, disk_available) VALUES ((SELECT id FROM host_info), '$memory_free', '$cpu_idle', '$cpu_kernel', '$disk_io', '$disk_available');
EOF
