LesPatternsDuSwag
=================

Requirements:
- Oracle JDK 8
- Maven (http://maven.apache.org/)

How to:
- Get in the directory
- run the command "mvn package"
- run the command "mvn exec:java" to execute
- run the command "mvn test" to run tests

Note: the execute command mentionned above might not work depending on your
 configuration. The following command should then work:

mvn exec:java -Dexec.mainClass="parking.implementation.gui.MainApplication"
