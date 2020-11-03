# Introduction
- London Gift Shop (LGS) is a UK-based online store that sells gift-ware for more than 10 years. But the company's revenue is not growing in recent years. The LGS marketing team wants to utilize the latest data technologies to understand their customers better to develop sales and marketing specific techniques. Because most of the customers of the company are wholesalers, the data pattern of the selling is very stable. Since the UGS marketing team doesn't have IT capability and the UGS IT department doesn't have enough resources to work on more projects, UGS CTO decides to engage with Jarvis consulting which offers software and data engineering services.
- The work below is the demonstration of the proof of concept(POC) project that can help the LGS marketing by answering some specific business questions using the data wrangling technics. The data that was provided to Jarvis consultant is the transaction data from 01/12/2009 to 09/12/2011. to store the data, we used a PostgreSQL container and using Jupyter Notebook for the analysis purpose. We utilized the Numpy and Pandas library for Python data manipulation and Matplotlib for the data graphical plotting.

# Project Architecture
- The LGS IT team did reveal their application architecture for the project. The LGS store runs on the Azure Cloud as a Resource Group. The front-end is handled by a content delivery network which retrieves resources from blob storage as needed. The back-end is handled by Azure's API Management service, which in turn routes requests to a scalabe Azure Kubernetes cluster. There is also an SQL Server running to perform OLTP. From this server, the LGS team has provided us a retail.sql file to perform our analytics on. On our side, the data is stored in a PostgreSQL instance and interpreted through a Jupyter notebook.
- Draw an architecture Diagram (please do not copy-paste any diagram from the project board)

# Data Analytics and Wrangling
- [[Notebook](https://github.com/jarviscanada/jarvis_data_eng_shawn/blob/feature/PythonData/python_data_wrangling/psql/retail_data_analytics_wrangling.ipynb)]
- Discuss how would you use the data to help LGS to increase their revenue (e.g. design a new marketing strategy with data you provided)

# Improvements
- List three improvements that you want to do if you got more time
