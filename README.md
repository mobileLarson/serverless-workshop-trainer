# Serverless Workshop (Trainer)

This repository contains the source code for the Serverless Workshop powered by open knowledge. Parts of this workshop are adapted from the [AWS Serverlesspresso Workshop](https://workshop.serverlesscoffee.com). 

To ensure that the workshop day starts and runs as smoothly as possible, it is recommended that the following installation steps are carried out in advance. 

> **IMPORTANT**: The described installation assumes the presence of Java 11 or higher. 


## Installation 

The installation will help to prefetch all necessary 3rd party Java libraries (e.g. from AWS). 

### Step 1: Install Lambda Layer (optional)

Some of the examples reference a library (aka lambda layer) called __ServerlessWorkshopBaseLayer__ that is part of the workshop source code. 

To make this references work correctly, we have to install this lambda layer into our maven repository.

To do so, you can use your local maven installation (if presence) or the provided maven wrapper included in this repository. 

Goto the lambda layers directory 

	cd 00_setup/lambda-layer/serverlessWorkshopBaseLayer/

and call the maven install command. Use _./mwnw_ for the provided maven wrapper or _mvn_ for your local maven version: 

	./mwnw clean install -DskipTests=true

### Step 2: Package example projects

After having installed the lambda layer, go back to the main directory of the workshop 

	cd ../../../

and call the maven package command for the whole project: 

	./mwnw clean package -DskipTests=true

This command will download all the remaining 3rd party libraries and compile and package our examples. 

### Done

The onyl thing left is looking forward to the workshop! 



