FROM frolvlad/alpine-oraclejdk8:slim
LABEL maintainer="bamboo <892078604@qq.com>"

ENV profile=prod SERVER_PORT=8080 JAVA_OPTS=

ADD ../target/xw-0.0.1-SNAPSHOT.jar app.jar

CMD	java -jar -Djava.net.preferIPv4Stack=true \
	--server.port=${SERVER_PORT} \
#	--spring.profiles.active=${profile} \
    /app.jar
