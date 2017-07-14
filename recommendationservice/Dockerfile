FROM java:8-jre
MAINTAINER stackroute <agarwalayushee09@gmail.com>

#RUN mkdir /app
ADD /target/recommendationservice-0.0.1-SNAPSHOT.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/recommendationservice-0.0.1-SNAPSHOT.jar"]
EXPOSE 8082
