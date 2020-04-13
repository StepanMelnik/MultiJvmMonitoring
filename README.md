# MultiJvmMonitoring

Fetches all processes in the system and looks for java processes.
After it connects to java processes by JMX and fetches all needed properties to monitor the processes.


## Description

Let's say the system has a lot of java processes.
Also it's hard to activate JMX per java to monitor the process.

MultiJvmMonitoring fetches specific java processes only and connects to each java process by JMX and monitors needed JVM properties.

Let's say we want to handle what java process consumes extra CPU.
We can use Log4jRepoter that analyzes all threads in java process and profiles what method consumes high CPU.


### Build

> mvn clean install

### Config
Create **config.json** file:

```json
{
	"reportType" : "LOG4J",
	"refreshInterval" : 10000,
	"thresholdCpuToProfile" : 0.001,
	"processNames" : 
		[
			"java.exe", "javaw.exe", "Tomcat.exe", "eclipse.exe", "test.exe"
		],
	"ignorePackages" : 
		[
			"java.", "com.sun.", "javax.", "org.eclipse", "org.apache.", "org.junit.", "org.mockito.", "com.sme.monitoring.jvm"
		]
}
```

* reportType is a type of reporter:
     - LOG4J generates a report and saves the result in a file by log4j framework;
     - ZABBIX sends a report to zabbix (has not supported yet);
     - SPRING sends a report to Spring Monitoring and Management (has not supported yet).
* refreshInterval specifies time to scan all java processes in the system;
* thresholdCpuToProfile is threshold of CPU to profile java process; 
* processNames is a list of java processes to be monitored;
* ignorePackages is a list of packages to ignore them while profiling threads.


### Usage
#### Java version


The application works with classes from tools.jar.
It means JDK java should be used to load classes from tools.jar.

Open console window and run the following command to check that you really use jdk:
> java -version

> java -XshowSettings:properties -version 2>&1|find "sun.boot.library.path"

The last command should return JDK path.
If you still see JRE path, use a path to JDK java by hand.  


#### Run application

Copy config.json to current application folder:

> copy config.json jvm-monitoring-report/target

> cd jvm-monitoring-report/target

Run application in Windows:
> java -cp .;"%JAVA_HOME%"/lib/tools.jar;jvm-monitoring-report-0.0.0.Dev-SNAPSHOT.jar com.sme.monitoring.jvm.report.Log4jReporterMain

Run application in Linux:
> java -cp .:"%JAVA_HOME%"/lib/tools.jar:jvm-monitoring-report-0.0.0.Dev-SNAPSHOT.jar com.sme.monitoring.jvm.report.Log4jReporterMain


