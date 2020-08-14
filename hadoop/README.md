Table of contents
* [Introduction](#introduction)
* [Hadoop Cluster](#hadoop-cluster)
	- [HDFS](#hdfs)
	- [Hive](#hive)
	- [YARN](#yarn)
	- [MapReduce](#map-reduce)
* [Hive Project](#hive-project)
* [Future Improvements](#Improvements)

# Introduction
- The purpose of this project is to build a big data platform and evaluate core Hadoop components, including MapReduce, HDFS, and YARN. 
After finished the project, I understood the structure of HDFS, the logic behind the MapReduce function, and how to use Hive. 
From this project, I learnt how to use Hive to either write or read from HDFS and gs cloud. also applied possible optimization techniques to lower query's processing time. 
In this project, Google Cloud Platform is used to build a hadoop cluster with 1 master node and 2 worker node. 
They are managed by YARN and user can connect to Hive Server by either CLI(beeline) through SSH or Zeppelin Notebook through browser. 
In this project, Zeppelin is mainly used, and there is a json file, which can be imported as a Zeppelin Notebook, under hive folder in this project. 
Basically, in the notebook, the data from Google storage is written to HDFS with a correct format and 2 optimizations are done 
to further increase the performance of the HiveQL queries.
 # Hadoop Cluster
![Architecture](/hadoop/assets/architecture.png)
 ## HDFS
 The Hadoop Distributed File System (HDFS) is the primary data storage system used by Hadoop applications. It employs a NameNode and DataNode architecture to implement a distributed file system that provides high-performance access to data across highly scalable Hadoop clusters.   
 ### NameNode (The Master Node)
 It is the master daemon that maintains and manages the DataNodes (slave nodes). It records the metadata of all the files stored in the cluster, e.g. The location of blocks stored, the size of the files, permissions, hierarchy, etc. 
 ### DataNode (The Worker Node)
DataNodes are the slave nodes in HDFS.The actual data is stored on DataNodes and send it to resource manager.
## **Hive**
Hive is an ETL and Data warehousing tool developed on top of Hadoop Distributed File System (HDFS). Hive makes job easy for performing operations like Data Encapsulation, Ad-hoc queries and analysis on huge datasets. For connecting with hive, it can be done through CLI using beeline and or by zepelline.
### CLI(Beeline)
Hive Beeline is a CLI tool which allows user to connect to a Hive instance via JDBC. It is installed along hive. 
### Zeppelin
At times, it becomes difficult to manage run large number of queries. Hence, zeppeline provides a clean User interface for executing queries.
## **YARN** 
(Yet Another Resource Negotiator),Apache Hadoop YARN is the resource management and job scheduling technology in the open source Hadoop distributed processing framework.  
### Resource Manager 
It runs on master node. It manages and assigns resources to different jobs.  
###  Node Manager 
It runs on worker node. It gather resources usage information and send it to resource manager. 
## **Map Reduce**
MapReduce is a framework using which we can write applications to process huge amounts of data, in parallel, on large clusters of commodity hardware in a reliable manner.
### Map Phase
This phase applies business logic to the data. The input data gets converted into key-value pairs based on the user-defined functions(mapper). This part is done by the data nodes. They will gather and process data based on the given bussiness logic. The output key-value pair will be sent to the reducer located at name node.
### Reduce Phase
The Reduce phase takes the output of Map Phase as input. It applies aggregation based on the requirements. Then, the output of this reducer located at name node will be the final result

# Hive Project
In this project, there are mainly three parts: loading data into HDFS, optimization of queries and comparison of performance between hive and spark. Each part is described in the screenshot below along with the code and output. Overall, the basic operations are studied as well as the more advanced optimization.
![Diagram](./assets/Zeppelin.png)  
# Improvements
1. More comparisons can be done between columnar file formats with parquets format by using spark and hive.
2. Perfomances can be compared for both columnar format approach and spark approach.
3. Project can be extended by manipulating data more and making more comparisons even with two tables.
