Table of contents
* [Introduction](#Introduction)
* [Quick Start](#Quick-Start)
* [Architecture](#Architecture)
* [REST API USAGE](#REST-API-Usage)
* [Docker Deployment](#Docker-Deployment)
* [Improvements](#Improvements)

# Introduction
- This is an online stock trading simulation REST API which helps users,traders and front-end developers to view, create and manage traders, accounts, quotes and 
security orders. This online simulation trading REST API is built based on a three-tie microservice MVC architecture and using Springboot and maven for dependency management.
It uses IEX Cloud as a data source and PostgreSQL to persist data. 
This application can be accessed by various users by consuming REST API through web browser via swagger or by other open source applcations al well like postman.

# Quick Start

### Prequiresites
 *  `CentOS` machine or VM
 *  `Docker`
 *  Minimun `Java 8` version
 *  `Maven`
 *  `IEX Cloud` account for API token
 *  `Swagger` or `postman`
- Start the docker
```$xslt
systemctl status docker || systemctl start docker
```
- Create a new docker network
```$xslt
docker network create --driver bridge trading-net
```
- Build two images, one is for the database, and the other is for the application. build the image at the root directory alone with the Dockerfile

- Create the trading database image
```
cd ./springboot/psql
docker build -t trading-psl .  #docker builds ./Dokcerfile by default
docker image ls -f reference=trading-psl
```
- Create the trading app image
```$xslt
cd ./springboot/
docker build -t trading-app . #docker builds ./Dokcerfile by default
docker image ls -f reference=trading-psl
```
- Create docker container
```$xslt
docker run --name trading-psql-dev \
-e POSTGRES_PASSWORD=password \
-e POSTGRES_DB=jrvstrading \
-e POSTGRES_USER=postgres \
--network trading-net \
-d -p 5432:5432 trading-psql

IEX_PUB_TOKEN="your_token"
#start trading-app container which is attached to the trading-net docker network
docker run --name trading-app-dev \
-e "PSQL_URL=jdbc:postgresql://trading-psql-dev:5432/jrvstrading" \
-e "PSQL_USER=postgres" \
-e "PSQL_PASSWORD=password" \
-e "IEX_PUB_TOKEN=${IEX_PUB_TOKEN}" \
--network trading-net \
-p 8080:8080 -t trading-app
```

### Testing Application with Swagger 
Swagger is an open-source software framework backed by a large ecosystem of tools that helps developers design, build, document, and consume RESTful web services. While most users identify Swagger by the Swagger UI tool, the Swagger toolset includes support for automated documentation, code generation, and test-case generation. 
To run or consume the application, navigate through `http://localhost:8080/swagger-ui.html#/`

# Architecture
- Draw a component diagram which contains controllers, services, DAOs, psql, IEX Cloud, WebServlet/Tomcat, HTTP client, and SpringBoot. 
- briefly explain the following components and services (3-5 sentences for each)
  - Controller layer (e.g. handles user requests....)
  - Service layer
  - DAO layer
  - SpringBoot: webservlet/TomCat and IoC
  - PSQL and IEX

# REST API Usage
## Swagger
What's swagger (1-2 sentences, you can copy from swagger docs). Why are we using it or who will benefit from it?
## Quote Controller
- High-level description for this controller. Where is market data coming from (IEX) and how did you cache the quote data (PSQL). Briefly talk about data from within your app
- briefly explain each endpoint
  e.g.
  - GET `/quote/dailyList`: list all securities that are available to trading in this trading system blah..blah..
## Trader Controller
- High-level description for trader controller (e.g. it can manage trader and account information. it can deposit and withdraw fund from a given account)
- briefly explain each endpoint
##Order Controller
- High-level description for this controller.
- briefly explain each endpoint
## App controller
- briefly explain each endpoint
## Optional(Dashboard controller)
- High-level description for this controller.
- briefly explain each endpoint

# Docker Deployment
- docker diagram including images, containers, network, and docker hub
e.g. https://www.notion.so/jarviscanada/Dockerize-Trading-App-fc8c8f4167ad46089099fd0d31e3855d#6f8912f9438e4e61b91fe57f8ef896e0
- describe each image in details (e.g. how psql initialize tables)

# Improvements
If you have more time, what would you improve?
- at least 5 improvements
