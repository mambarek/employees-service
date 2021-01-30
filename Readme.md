Documents may be an independent service
Country may be to an independent service 

### Swagger UI
ATTENTION: HAL-Browser and Explorer don't work with Swagger
it rises exceptions so avoid it to add them
#### api-docs
http://localhost:8010/v2/api-docs
#### swagger ui
http://localhost:8010/swagger-ui.html

### JPA Model Gen
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>${maven-compiler-plugin.version}</version>
                <configuration>
                    <annotationProcessorPaths>
                        <annotationProcessorPath>
                            <groupId>org.hibernate</groupId>
                            <artifactId>hibernate-jpamodelgen</artifactId>
                            <version>${hibernate.version}</version>
                        </annotationProcessorPath>
                    </annotationProcessorPaths>
                </configuration>
            </plugin>
            
### Dockerfile layered             
>*$ mvn clean package*
  
To see the layered structure execute the following  
> *$ java -Djarmode=layertools -jar target/employees-service-0.0.1-SNAPSHOT.jar list*

You would see following structure
>dependencies  
spring-boot-loader    
snapshot-dependencies    
application  

####Dockerfile
>FROM adoptopenjdk:11-jre-hotspot as builder  
WORKDIR application  
ARG JAR_FILE=target/*.jar  
COPY ${JAR_FILE} application.jar  
RUN java -Djarmode=layertools -jar application.jar extract  
  
>FROM adoptopenjdk:11-jre-hotspot  
WORKDIR application  
COPY --from=builder application/dependencies/ ./  
COPY --from=builder application/spring-boot-loader/ ./  
COPY --from=builder application/snapshot-dependencies/ ./  
COPY --from=builder application/application/ ./  
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]  

#### Build docker image
>docker build -t employee-service-example .
#### Rund docker image
>docker run -it p8010:8010 employee-service-example

## Docker build with fabric8
Docker image would be created and pushed by maven at install phase see pom.xml
> mvn install

or use docker:build, docker:push
> mvn docker:build docker:push

or use Jenkins. In jenkinsfile (Pipeline) we call the two goals

## Config Server
in bootstrap-cloud.properties we define the server id from discovery server to connect to
config server. The config is defined in project "cma-deploy" in subfolder "employees-service"
