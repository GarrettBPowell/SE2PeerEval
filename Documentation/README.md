# SE2PeerEval

Project Description:
The latest and greatest state of the art peer evaluation software. Efficently evaluate your team member's contributions to projects.

Running the Project:
Project can be run via command prompt from Project Folder SE2PeerEval with the following commands
 mvn install assembly:assembly
 java -cp target/java_postgres-1.0-SNAPSHOT-jar-with-dependencies.jar PeerEval

To load files into the database, put the csv file you want to load into the resources folder of the project and then run the project with the 
commands above. This folder can be located at SE2PeerEval\src\main\resources


Information:
Members:
Christa Greenwood
Megan Skeen
Garrett Powell

Roles:
Christa Greenwood - Database Managment 
Megan Skeen - Documenter, Tester
Garrett Powell - Java Developer

# Glossary:

 - An **Evaluation** is a set of answers returned based off of the survey presented. 
 
 - A **Survey** is a set of questions presented to a team member. 

 - A **Rating** a numerical value assigned as the answer to a question in the survey. 

 - A **Team** is a group of team members that fill out surveys about each other. 

 - A **Team Member** is an individual on a team. 
 
 - A **Due-Date** is the date in which a survey must be filled out by. 
 
 - A **Question** is an element in a survey that a team member must answer in relation to the performance of another team member. 
 
 - An **Instrument** is a set of questions designed to indirectly discover a particular answer. 
 
 - A **Scale** is the range that a rating can be.  
 
 - To **Deploy** is to have the customer have a running file. 
 
 - To **Distribute** is to provide a usable file to the customer. 
 
 - To **Fill Out** is to provide the answers to a survey. 
 
 - To **Complete** is to submit a survey that has all of the answers answered. 
 
 - To **Rate** is to provide a number value as an answer to a question that ranks the individual. 
 
 - To **p0wn** is to rate a team member with the lowest possible value on every question in a survey. 

Abstract Classes:

- The User class is abstract because a user has different types and permissions based on the user.

Final Classes:

- The Student class is final because there is no class in this assignment which needs to inherit all attributes and methods of a student class. There would be no permissions lower than them and so it is a final class. 
