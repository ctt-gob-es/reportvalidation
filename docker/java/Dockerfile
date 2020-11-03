#FROM java:8
FROM openjdk:8u272-buster
RUN apt update && apt install -y default-mysql-client
COPY irapvalidator.jar /usr/irapvalidator.jar
COPY start.sh /usr/start.sh
RUN chmod 755 /usr/start.sh
ENTRYPOINT ["/usr/start.sh"]
#ENTRYPOINT ["java","-jar","irapvalidator.jar"]
