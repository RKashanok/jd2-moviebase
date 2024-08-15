# jd2-moviebase
Service for movies list operations

# PostgreSQL Docker Compose Setup

This project provides a simple setup for running a PostgreSQL database using Docker Compose.

## Prerequisites

Before you begin, ensure you have the following installed on your machine:

- [Docker](https://docs.docker.com/get-docker/)
- [Docker Compose](https://docs.docker.com/compose/install/)

## Project Structure

The project contains the following files:

- `docker-compose.yml`: Docker Compose configuration file for setting up PostgreSQL.
- `init.sql`: SQL script for initializing the database with schema and data.

## Start the Database

To start the PostgreSQL database, run:

```bash
docker-compose up
```
