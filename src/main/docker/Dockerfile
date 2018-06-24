FROM java

# Add Maintainer Info
LABEL maintainer="miroslav.ivanov.1988@gmail.com"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Add the application's jar to the container
ADD falcon-io-task-0.0.1-SNAPSHOT.jar falcon-io-task.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/falcon-io-task.jar"]