## AWS Serverless Workshop
### Serverless Testing
 
Testing your serverless application means ganining confidence in your code and minimizing the risk of failures in production with the help of

 * **Unit Tests**, for testing the business logic of your serverless functions.
 * **Integration Tests**, for testing the interaction of your serverless functions with other cloud components (e.g. DynamoDB, S3, Kinesis, API Gateway). 
 * **Aceptance Tests**, for testing the use-cases as a whole from end to end. 

HINT:
> Due to the highly distributed character of a serverless application the biggest complexity is not within the function itself, 
but in how it interacts with other functions and services (a.k.a. cloud components). Therefore  your tests shoulnd't focus on unit 
tests but on integration tests. 
   
#### Unit Tests 

With the help of unit tests we want to make sure that our business logic is working as expected. 

If you try to test the business logic of our

	GreetingHandler

you will run into some challenges (e.g. the presence of AWS specific classes). Try to refactor the *GreetingHandler* in a way, that you can easily test the business logic by itself. 

Take a look at the test class

	GreetingServiceTest

and try to make it work with the refactored code. 

HINT
> It is always a good idea to separate the business logic from infrastructure code. 


After ensuring the correctness of our business logic we also want to make sure, that the AWS Lambda Handler (*GreetingHandler*) itself works as expected. 

Take a look at the two test classes

	GreetingHandlerTest
	GreetingHandlerTestWithMock

and try to make them work with the refactored *GreetingHandler* in a way that the handler returns the expected *GreetingResponse* object for a given *GreetingRequest* object. 
 
#### Integration Tests 

With the help of integration tests we want to make sure that the integration of our serverless function with the other components of our serverless application (e.g. DynamoDB, S3, API Gateway) works as expected. 

Fortunately we can describe the components of our serverless application and their way of interaction with the help of a declarative model called the Serverless Application Model (aka SAM template).

HINT
> To be able to follow this exercise you have to install the [AWS Command Line Interface](https://docs.aws.amazon.comcli/latest/userguide/cli-chap-install.html) and the [AWS SAM Command Line Interface](https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/serverless-sam-cli-install.html) first. 

##### Step 1: Understand the SAM template 

Take a look at the SAM template file *template.yaml* inside the *integration-test* folder and try 
to understand the SAM of our serverless application.

##### Step 2: Invoke AWS Lambda Handler locally 

The SAM template declares a ressource named *Greetings*, representing our AWS Lambda Handler *GreetingHandler::handleRequest*. You can test this handler locally by simply calling

	sam local invoke "Greetings" -e greetings-from-lars.json 
	sam local invoke "Greetings" -e greetings-failure.json 

	sam local invoke "GreetingsViaApi" -e greetings-via-api-from-lars.json 
	sam local invoke "GreetingsViaApi" -e greetings-via-api-failure.json 

from the folder where the template is placed. 

HINT
> For a better understanding for what is going on behind the scenes take a look at the four events *greetings-from-lars.json*, *greetings-failure.json*, *greetings-via-api-from-lars.json*, *greetings-via-api-failure.json*. 

##### Step 3: Call AWS Lambda Handler locally via API Gateway 

In most real live scenarios a lambda handler is not triggered directly but with the help of a cloud component, e.g. an API Gateway. 

SAM helps us to start an API Gateway locally so that we can simulate and test the behaviour of a gateway triggered AWS Lambda function. 

To start the API Gateway for our serverless application locally at port 8000 call

	sam local start-api –p 8080

from the folder where the template is placed. 

When started you can trigger the *GreetingHandler* by sending a http POST request to the serverless function proxied by the API gateway 

	curl -d '{"firstName":"Lars", "lastName":"Roewekamp"}' -H "Content-Type: application/json" -X POST http://localhost:8000/greetings/

Of course, you can also test what will happen if you send an incorrect (e.g. empty) payload  
	
	curl -d '{}' -H "Content-Type: application/json" -X POST http://localhost:8000/greetings/
	
##### Step 4: Automate test calls via API Gateway 

We will use the [REST Assured Framework](http://rest-assured.io) to automate the test calls from step 3. 

Go to the integration test class 

	GreetingHandlerIntegrationTest

and make it work ;-) 


HINT
> Even if it is possible to simulate AWS and its cloud components locally, it will never behave like the real 
 cloud environment when it comes to latency, memory consumption etc. Therefore it is stricly recommended to execute 
 most of your integration tests (also) in the real cloud and not (only) locally! As a rule of thumb execute the 
 positive path in the cloud and only exceptional circumstances - that are hard to emulate in the cloud - locally. 

#### Acceptance Tests 
 
There are different ways for acceptance test remote as well as locally: 

* use different AWS Account for testing purpose only
* use dedicated CloudFormation template for testing
  * different region
  * prefix for all components 

* use cloud emulation like [localstack](https://localstack.cloud/solutions/cloud-emulation/)
* use local emulations for dedicated cloud components 
  * DynamoDBLocal 

##### DynamoDB

Start via local installation: 

    java -Djava.library.path=./DynamoDBLocal_lib -jar DynamoDBLocal.jar -sharedDb

Start via Docker: 

    docker run -p 8000:8000 amazon/dynamodb-local

Start via Docker-Compose:

    docker run -p 8000:8000 amazon/dynamodb-local


Credentials must be set:

* AWS Access Key ID: "fakeMyKeyId"
* AWS Secret Access Key: "fakeSecretAccessKey"

Test-Call:

    aws dynamodb list-tables --endpoint-url http://localhost:8000