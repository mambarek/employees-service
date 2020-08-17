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
