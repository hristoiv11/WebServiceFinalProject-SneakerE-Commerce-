#!/usr/bin/env bash

spring init \
--boot-version=3.2.3 \
--build=gradle \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name=customers-service \
--package-name=com.laboneproject.customers \
--groupId=com.laboneproject.customers \
--dependencies=web,webflux,validation \
--version=1.0.0-SNAPSHOT \
customers-service

spring init \
--boot-version=3.2.3 \
--build=gradle \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name=sneakers-service \
--package-name=com.laboneproject.sneakers \
--groupId=com.laboneproject.sneakers \
--dependencies=web,webflux,validation \
--version=1.0.0-SNAPSHOT \
sneakers-service

spring init \
--boot-version=3.2.3 \
--build=gradle \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name=inventory-service \
--package-name=com.laboneproject.inventory \
--groupId=com.laboneproject.inventory \
--dependencies=web,webflux,validation \
--version=1.0.0-SNAPSHOT \
inventory-service

spring init \
--boot-version=3.2.3 \
--build=gradle \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name=purchases-service \
--package-name=com.laboneproject.purchases \
--groupId=com.laboneproject.purchases \
--dependencies=web,webflux,validation \
--version=1.0.0-SNAPSHOT \
purchases-service

spring init \
--boot-version=3.2.3 \
--build=gradle \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name=api-gateway \
--package-name=com.laboneproject.apigateway \
--groupId=com.laboneproject.apigateway \
--dependencies=web,webflux,validation \
--version=1.0.0-SNAPSHOT \
api-gateway

