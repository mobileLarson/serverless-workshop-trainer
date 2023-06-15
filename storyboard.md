lesson 1: 
first lambda with some best practices
- separate infrastructure and business code
- separate Request / Response Model
- Context Logger / Environment 
- Logger via SL4J vs. LambdaLogger 
- BTW: OrderIdService als eigene Lambda? 

lesson 2: 
write order to Dynamo DB 
- ONE lambda to do all
- CRUD
- CloudFormation to create App and DB
- BONUS: use Dynamo DB for OrderIdService

lesson 3:
write order to Dynamo DB
- one lambda for one operation
- layer