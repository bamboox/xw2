FROM frolvlad/alpine-oraclejdk8:slim
LABEL maintainer="bamboo <892078604@qq.com>"

ENV profile=prod SERVER_PORT=8088 JAVA_OPTS=

ADD xw-0.0.1-SNAPSHOT.jar app.jar

CMD	java  -Djava.net.preferIPv4Stack=true -jar app.jar --server.port=${SERVER_PORT}
