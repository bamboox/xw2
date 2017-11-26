#!/bin/bash

docker build -t springboot .
docker run -d springboot