#!/bin/sh

psql -U postgres <<DDL
CREATE DATABASE hello_service;
REVOKE CONNECT ON DATABASE hello_service FROM PUBLIC;
CREATE USER hello_service WITH PASSWORD 'hello_service';
GRANT CONNECT ON DATABASE hello_service TO hello_service;
DDL

psql hello_service < /docker-entrypoint-initdb.d/hello-service.sql.dump
