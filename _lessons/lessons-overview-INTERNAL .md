# Serverless Workshop 
## Übungen 

Bevor mit den Übungen gestartet wird, erfolgt eine Einführung in die Serverlesspresso Anwendung. 

Was wollen wir heute machen und lernen?
> Den Serverlesspresso Show-Case zeigen. 

Wie gehen wir dabei vor? 
> Die verschiedenen Übungen kurz skizzieren. 

### 00: Setup & Workshop Einführung
*	IAM vs Stammbenutzer
*	durch die AWS Konsole navigieren
*	CloudFormation erklären und einspielen
	* 	best practiceses: Naming / Prefix 

### 01: Hello Serverless
*	My First Lambda
*	Trennung AWS Infra Code / Business Logik 
*	Einspielen via AWS Console
*	AUSBLICK: Update via SAM 

### 02: Lambda & Cloud Components
*	Lambda nutzt Dynamo DB 
*	was ist ein XYZ Client?
*	wie kann ich ihn nutzen? (Credentials vs. IAM Rollen/Rechte)
*	wie sind die APIs aufgebaut (Request, call, Response)
*	Client vs. Enhanced Client (DB Mapping)
*	Order Counter: Update Expression vs Read&Write
*	AUSBLICK: lokal (Local dynamoDB zeigen) // AWS Integration in IntelliJ 

**TODO:**

Step 01 und Step 02: 

* Order vs AnnotatedOrder oder nur Order? > nur ORDER
* lokale Dynamo DB 

Step 03: 

* order-counting-service mit UpdateExpression implementieren > DONE but TESTING
* lesson_02.md entsprechend anpassen / erweitern 


### 03: Lambda next 
*	am Beispiel Order Management 
*	eine vs. N Lambdas? 
*	Konzept der Layer (AWS Base Layer, AWS Cloud Component Layer, Application Base Layer)
*	Cold Start Problematik
	*	mehr Speicher (kann schon viel ausmachen)
	* SnapShot 
	*	Concurrent Instances ($! - AWESOME but costly)
*	Best Practices 
	*	DB in static
	*	…

### 04: Workflows / Step Functions
*	am Beispiel Order Processing 
*	Schritt für Schritt aufbauen 
*	Testing: wie spielt man das durch? 
*	CloudShell zum Absetzen der „Antworten“
*	Konzept EventBridge
*	AUSBLICK: Tooling wie EventBridge Atlas 
*	AUSBLICK: OrderManagement next -> Step Function Workflow statt Lambdas schneller / günstiger

### 05: API Gateway
*	Konzept erläutern und Aufbau zeigen 
*	Schritt für Schritt aufbauen (am Beispiel Create Order)
*	Stage erstellen und zugriff von Aussen
*	Export von openAPI und CloudFormation Template
*	Import von openAPI Export in Postman

### 06: Testing 
*	Generelle Testkonzepte für die Cloud 
*	lokal vs. Cloud 
*	best practices 
* loacal stack: 
https://docs.localstack.cloud/getting-started/installation/#localstack-cockpit

https://docs.localstack.cloud/user-guide/tools/cockpit/
