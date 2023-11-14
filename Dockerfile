FROM openjdk:17 AS kjob
LABEL authors="dbontch"

#RUN mkdir data

ADD target/kjob-1.0.0-SNAPSHOT.jar kjob.jar

ENTRYPOINT ["java", "-jar", "kjob.jar"]
EXPOSE 8088
