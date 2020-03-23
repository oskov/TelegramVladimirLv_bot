FROM adoptopenjdk/openjdk11:alpine-jre
RUN mkdir /opt/app
COPY /jar/app.jar /opt/app/app.jar
CMD ["java", "-jar", "/opt/app/app.jar"]
