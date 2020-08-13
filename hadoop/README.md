Table of contents
* [Introduction](#introduction)
* [Hadoop Cluster](#hadoop-cluster)
	- [Hadoop Framework](#hadoop-framework)
	- [HDFS](#hdfs-the-storage-layer-of-hadoop)
	- [MapReduce](#mapreduce-the-data-processing-layer-of-hadoop)
	- [YARN](#yarn-the-resource-management-layer-of-hadoop)
* [Hive Project](#hive-project)
* [Future Improvements](#future-improvements)

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
- cluster architecture diagram
  - 1 master and 2 workers nodes
  - HDFS, YARN, Zeppelin, Hive (hive Server, hive metastore, RDBMS), etc.
- Big data tools you evaluated (e.g. MapReuce, YARN, HDFS, Hive, Zeppelin, etc..)
- hardware specifications

# Hive Project
- discuss the purposes of the project and what you have done.
- Post your Zeppelin Notebook screenshot here
	- Make sure your Notebook is nice and clean as hiring managers will visit your project
	- use `Full Page Screen Capture` chrome extention to capture a webpage as a picture

# Improvements
If you have more time, what would you improve?
- at least three improvements
