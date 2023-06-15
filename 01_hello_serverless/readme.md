## AWS Serverless Workshop
### Hello-World   

This example will give you a first impression of the AWS Serverless 
programming paradigm and - in addition - will help you to get to know the [AWS Management Console](https://aws.amazon.com/de/console/).

During the example you will learn how to program your first AWS Lambda function, how to create and upload the corresponding deployment artifact to the AWS Cloud and finally how to test your newly created Lambda  function. 

#### Program your first Lambda Function

Let's start by writing the most simple Lambda Function possible, a.k.a. HelloWorld. Lets "jump" into the code and follow the embedded instruction: 

    de.openknowledge.workshop.cloud.serverless.lambda.HelloWorld

HINT: Look at TODOs 
> Taking a closer look at the **TODO** comments inside the HelloWorld class will enable you to fulfill this exercise.   

#### Create a deployment artifact

Once finished all the specified TODOs, we are able to build a deployment artifact (JAR) including the `HelloWorld` class itself and all necessary dependencies. 

To do so, switch to the 01\_hello\_world folder and call ... 

    mvn clean package 
  
As a result of this maven operation you will find the generated deployment artifact inside the target folder. 

    ./target/hello-serverless-world-1.0-SNAPSHOT.jar
    
#### Create a Lambda Function in the AWS Cloud   

Ok, all local work is done. Let's go to the cloud!

Log-in to your AWS Account and choose *Services -> Lambda* in the main menu. This will bring you to the Lambda main page. Click the *create function* button and follow the instructions on screen.

The description below will guide you through all necessary steps to create and configure your Lambda function in the AWS cloud: 

1. Create function 
2. Upload deployment artifact
3. Link Lambda function handler method

HINT: Further reading
> for a deeper understanding see also [Create a Simple Lambda Function](https://docs.aws.amazon.com/lambda/latest/dg/get-started-create-function.html).

##### Step 1: Create function 

During the first step of the function creation process we have to define a unique name for our Lambda function  and the requested runtime environment, 
which in our case is Java 8. It is  a best practice to use a prefix for the function name as an individual namespace. 
 
In addition we have to connect our Lambda function with an IAM Security Role (Identity & Access Management), to make sure 
that no unauthorized person/role is allowed  to execute this function.    

Go to the  *"Author from the scratch"* section and fill in the following fields

* Name: *myprefix*\_helloworld (choose your own prefix!)
* Runtime: Java 8
* Role: *"Choose an existing role"
* Existing Role: lambda\_basic\_execution
 
Press *Create function* button afterwards, which will lead you to the configuration page for the created AWS Lambda function. That's all. 

Until now we have created an AWS Lambda configuration in the Cloud but there is still no real code connected to it. 
To do so, we have to upload our deployment artifact and link our `HelloWorld` class to the configuration.

##### Step 2: Upload deployment artifact 

Go to the section *"Function code"* and choose 

* Code entry type: *"Upload a Zip- or JAR-File"*

Click *Upload* button and choose the above mentioned deployment artifact.  


##### Step 3: Link HelloWorld handler method 

Next we have to link the Lambda handler method of our `HelloWorld` example to the current AWS Lambda configuration so that the AWS Lambda Runtime knows what method to call.

The pattern to specify the handler method looks like

    [fullqualified className]::[methodName]

Assuming the full qualified class name is

```java 
   de.openknowledge.workshop.cloud.serverless.lambda.HelloWorld
```
and the handler method signature is 

```java 
  public HelloWorldResponse greet(HelloWorldRequest request)
```

go to the section *"Function code"* again and fill in  
 
 * Handler: *de.openknowledge.workshop.cloud.serverless.lambda.HelloWorld::greet*   

**IMPORTANT**: Dont't forget to save 
> Do not forget to save the configuration changes. Just click the *save* button of the configuration pages main menu (upper right corner).  

Great, we are done! Almost. Last thing we have to do is to test our Lambda function.

#### Test your Lambda Function 

To test the AWS Lambda function and its configuration we simply have to click the *test* button (upper right corner, next to the save button).

But wait! What do we want to test? Our `HelloWorld` example expects a special request object containing the attributes *firstName* and *lastName*. So let's create a corresponding test event. 

Choose "configure test event" from the "Select a test event" dropdown box: 

 * Event name: *helloWorldTestEvent*
 * Copy the following JSON into the textarea

```json
{
    "firstName" : "Max", 
    "lastName" : "Mustermann"
}
```

Now you can test our Lambda function! Just click the *test* button and wait what will happen ... 

#### Any problems? No problem! 

Ok, but what can we do if any problem occurs. How can we detect the source of the problem and start to debug? First of all we have to find out what exactly went wrong. 

To do so, goto [CloudWatch](https://console.aws.amazon.com/cloudwatch/), choose *Logs* in the main menu (on the left) and the corresponding Log Group of your Lambda function afterwards. You will see a list of log streams. Choose the one of your interest by examining the column *last event time*. This will bring you to the specifig log information your are looking for. 

btw: Happy debugging!

#### What's next? 

Congratulations! Your have just created your first AWS Lambda function and hence entered the infinitive Universe of Serverless Computing. 

Do yourself a favor, take a break and play look/around a little bit. E.g. inspect and try out the different configuration choices of your lambda function (memory, timeout, concurrency) or switch fom *Configuration* tab to *Monitoring* tab and click one of the *Jump to Logs* links to deep dive into the Lambda functions log with the help of AWS CloudWatch.

You should also test your Lambda function with a non valid test event, e.g. with empty payload. What does the result look like?      