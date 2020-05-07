#!/bin/bash

lscpu_out=`lscpu`

l2_cache=$(echo "$lscpu_out"  | egrep "L2 cache:" | awk '{print $3}' | sed 's/[^0-9]*//g' | xargs)

echo $l2_cache
