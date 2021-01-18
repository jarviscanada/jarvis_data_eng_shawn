# Spark/Scala Project
## Introduction
- London Gift Shop (LGS) is a UK-based online store that sells gift-ware for more than 10 years. But the company's revenue is not growing in recent years. The LGS marketing team wants to utilize the latest data technologies to understand their customers better to develop sales and marketing specific techniques. Because most of the customers of the company are wholesalers, the data pattern of the selling is very stable. Since the UGS marketing team doesn't have IT capability and the UGS IT department doesn't have enough resources to work on more projects, UGS CTO decides to engage with Jarvis consulting which offers software and data engineering services.
- The work below is the demonstration of the proof of concept(POC) project that can help the LGS marketing by answering some specific business questions using the big data technics. The data that was provided to Jarvis consultant is the transaction data from 01/12/2009 to 09/12/2011. we decided to use Azure Databricks, an Apache Spark service provided by Microsoft. To interact with our Spark cluster we used the PySpark interpreter in Databricks notebooks.


# Zeppelin and Spark Implementation
[[Notebook](https://github.com/jarviscanada/jarvis_data_eng_shawn/blob/feature/Spark/spark/notebook/Retail%20Data%20Analytics%20with%20PySpark.ipynb)]
- Our team selected Azure Databricks to carry on the project. The dataset provided by LGS consisted of transaction data between 01/12/2009 and 09/12/2011. The data came in the form of CSV and was uploaded to Azure DBFS, a distributed file system available to our cluster, and read it into our cluster as a Dataframe. The Dataframe spanned all the workers in the cluster which allowed us to leverage more computational power for our work. Our analytical work focused on finding trends in their transactions over the given time frame and categorizing customers based on the recency and frequency of their visits. This information could be used to inform marketing campaigns towards certain customer segments, such as offering rewards programs to long-time customers.


# Databricks and Spark Implementation
![zeppelin](.assets/databricks&spark.png)
-  We evaluated Spark on a sample World Development Index dataset from the year 2016. The cluster and storage was set up on Google Cloud Platform, arranged as 1 master node and 2 workers configuration. The nodes were loaded with images that were preconfigured for Hadoop and Spark. Once the cluster was ready, we uploaded the dataset into the Hadoop Distributed File System and created an external Hive table from it. We used PySpark to load this table into a Dataframe which we performed our analytics on. Our analytics mostly consisted of finding historical GDP data on all listed countries.

# Future Improvements
