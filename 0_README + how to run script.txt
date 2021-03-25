This application will use a table in NonStop SQL to check with valid details, and create a customer order in a MySQL database.

MAKE SURE MySQL DATABASE IS RUNNING, VPN IS SET UP (if required).

How to run:
1. Update config.properties with database details.
2. Run in command line: javac Application
3. Run in command line: java -classpath C:\MySQL\mysql-connector-java-8.0.23\mysql-connector-java-8.0.23\mysql-connector-java-8.0.23.jar;C:\t1249\lib\t4sqlmx.jar;.; Application

--------------------

1. create, custId
java -classpath C:\MySQL\mysql-connector-java-8.0.23\mysql-connector-java-8.0.23\mysql-connector-java-8.0.23.jar;C:\t1249\lib\t4sqlmx.jar;.; Application create 1

2. create, custId, desc
java -classpath C:\MySQL\mysql-connector-java-8.0.23\mysql-connector-java-8.0.23\mysql-connector-java-8.0.23.jar;C:\t1249\lib\t4sqlmx.jar;.; Application create 1 "desc"

3. edit, orderId
java -classpath C:\MySQL\mysql-connector-java-8.0.23\mysql-connector-java-8.0.23\mysql-connector-java-8.0.23.jar;C:\t1249\lib\t4sqlmx.jar;.; Application edit 4

4. edit, orderId, newDesc
java -classpath C:\MySQL\mysql-connector-java-8.0.23\mysql-connector-java-8.0.23\mysql-connector-java-8.0.23.jar;C:\t1249\lib\t4sqlmx.jar;.; Application edit 8 "hello from app with new desc"

--------------------
Error:
- invalid order id
java -classpath C:\MySQL\mysql-connector-java-8.0.23\mysql-connector-java-8.0.23\mysql-connector-java-8.0.23.jar;C:\t1249\lib\t4sqlmx.jar;.; Application edit 9

- invalid custId
java -classpath C:\MySQL\mysql-connector-java-8.0.23\mysql-connector-java-8.0.23\mysql-connector-java-8.0.23.jar;C:\t1249\lib\t4sqlmx.jar;.; Application create 3

- invalid command
java -classpath C:\MySQL\mysql-connector-java-8.0.23\mysql-connector-java-8.0.23\mysql-connector-java-8.0.23.jar;C:\t1249\lib\t4sqlmx.jar;.; Application


-------------------

Command Line for MySQL Connector: java -classpath C:\MySQL\mysql-connector-java-8.0.23\mysql-connector-java-8.0.23\mysql-connector-java-8.0.23.jar;.; Application
Command Line for SQLMX: java -classpath C:\t1249\lib\t4sqlmx.jar;.; Application