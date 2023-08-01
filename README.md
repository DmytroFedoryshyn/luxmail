# luxmail
This is a web application for managing a mail service. <br>
It allows users to perform a set of operations with mails, such as:
<ul>
<li>Logging in as a user</li>
<li>Write a new mail</li>
<li>View all incoming and sent mails</li>
<li>Delete a mail</li>
<li>Reply to a mail</li>
<li>Filter mails by title and content</li>
</ul>

<h3>Project Structure</h3>
The project consists of multiple packages:
<ul>
<li><i>config</i> - contains all of the necessary configuration for a Spring framework</li>
<li><i>controller</i> - contains REST controllers which create endpoints for the server</li>
<li><i>dao</i> - contains data access objects (DAO's) used for interacting with the DB</li>
<li><i>dto</i> - contains data transfer objects (DTO's) used for exchanging information
with the client</li>
<li><i>exception</i> - contains all of the custom exceptions</li>
<li><i>lib</i> - contains custom validation annotations</li>
<li><i>model</i> - contains all classes of the model layer.</li>
<li><i>service</i> - contains all classes of the service layer.</li>
<li><i>util</i> - contains all utility classes for simplifying some tasks.</li>
</ul>
<h3>Used technologies</h3>
<ul>
    <li>Java 8</li>
    <li>Spring Boot</li>
    <li>Spring Security</li>
    <li>Hibernate</li>
    <li>Tomcat 9</li>
    <li>H2 database</li>
    <li>REST</li>
    <li>JSON</li>
    <li>Jackson</li>
    <li>Maven</li>
</ul>
<h3>How to install and use the project</h3>
<ol>
<li>Firstly, run <code>mvn clean package</code>. It should generate an executable JAR file.
<li>Then run java -jar project.jar</li>
</ol>

    
