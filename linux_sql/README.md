## Introduction
The Jarvis Linux Cluster Administration (LCA) team manages a Linux cluster of 10 nodes/servers which are running CentOS 7. 
The idea is  to monitor the system usage of the this nodes and server that helps the LCA team to meet their business needs. 
I need to implement the program using bash scripts and the PostgreSQL database in order to monitor the system hardware infomation and cpu resource, and also write SQL queries to answer some business need. 
This project helps the infrastructure team to get idea of the cpu usage and improve the system performance. 

## Architecture and Design
1) Describtion of the nodes/server relationship:   
![Diagram](https://user-images.githubusercontent.com/33403067/81481156-0fbc3480-91fc-11ea-96b8-25a345fe6676.png)
2) Describtion of the tables   
**host_info:** The script collects hardware specification data and then insert the data to the psql instance, collects the system info such as cpu_number, cou_architecture, cpu_model, cou_cache, cpu_speed, total_memory infomation  
**host_usage:** The script collects server usage data and then insert the data into the psql database. The script will be executed every minute using Linux crontab, it collects the spu usage infomation such as cpu_idle, cou_kernel_usage, disk_io_usage,dick_available infomation.  
3) Describtion of the scripts  
**docker_psql.sh:** This script offers a automate start/stop/create operation for the psql container, it returns error message if the certain condition is not meet.  
**host_info.sh:** This script grep the system infomation and put into the psql database.
**host_usage.sh:** The script collects server usage data and then insert the data into the psql database.

## Usage
1) The `ddl.sql` file in SQL are used to create database and to define the type and structure of the data that will be stored in a database. by excuteing in the psql database `\i ddl.sql`, the two tables are automaticely generated.
2) by running the `./host_info.sh -h "localhost" -U "postgres" -d "host_agent" -p 5432 `  the system infomation is collect and copied into query.  
3) by running the `./host_usage.sh -h "localhost" -U "postgres" -d "host_agent" -p 5432 `  the system infomation is collect and copied into query.  
4) using bash command `crontab -e` into crontable setup page and edit the crontable with command `* * * * * bash /home/centos/dev/jarvis_data_eng_shawn/linux_sql/scripts/host_usage.sh "localhost" 5432 "host_agent" "postgres"  >>/tmp/host_usage.log` the `host_usage.sh` script will collect the spu_usage every minute.  

## Improvements  
1) handle hardware update 
2) can identifiy the wrong infomation and 
