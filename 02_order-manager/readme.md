## AWS Serverless Workshop
### Order Manager

This example we will use a lambda function to access another AWS service (DynamoDB). 

During this example we will learn two different ways to access DynamoDB via AWS Lambda: 

- via "old school" __DynamoDbClient__ (more flexible)
- via "modern" __EnhancedDynamoDbClient__ (OR-Mapper style)

In adition we will learn 

* how to use Lambda Layers
* how to size Lambdas correctly

#### Order Manager v1 via DynamoDbClient

Project: 01_simple_order_manager

Deployment(s): sw-oms-simple

Main Components:

* OrderHandler, OrderRequest, OrderResponse
* OrderService
* OrderRepository, DynamoDBOrderRepository

OrderHandler class takes  OrderRequest, extracts order information and uses OrderService (and OrderRepository) to store new order.  

#### Order Manager v2 via DynamoDbClient

Project: 02_advanced_order_manager

Deployment(s): sw-oms-advanced

Main Components:

* OrderHandler, OrderRequest, OrderResponse
* OrderService
* OrderRepository, DynamoDBOrderRepository

OrderHandler class takes generic OrderRequest, scans for requested action and delegates the call to specific handler class:

* CreateOrderHandler
* UpdateOrderHandler
* ReadOrderHandler
* DeleteOrderHandler

Specific result will be translated into generic one inside the OrderHandler class.


#### Order Manager v2a via EnhancedDynamoDbClient

Project: 02_advanced_order_manager

Deployment(s): sw-oms-enhanced

Main Components:

* OrderHandler, OrderRequest, OrderResponse
* OrderService
* OrderRepository, DynamoEnhancedDBOrderRepository

OrderHandler class takes generic OrderRequest, scans for requested action and delegates the call to specific handler class:

* CreateOrderHandler
* UpdateOrderHandler
* ReadOrderHandler
* DeleteOrderHandler

Specific result will be translated into generic one inside the OrderHandler class.

#### Order Manager v3 via multiple handlers

Project: 03_layered_order_manager

Deployment(s): 

* sw-oms-layered-create-order, serverless-workshop-oms-base-layer
* sw-oms-layered-update-order, serverless-workshop-oms-base-layer
* sw-oms-layered-delete-order, serverless-workshop-oms-base-layer
* sw-oms-layered-read-order, serverless-workshop-oms-base-layer

Main Components:

* XYZOrderHandler, XYZOrderRequest, XYZOrderResponse
* XYZOrderService
* OrderRepository, DynamoEnhancedDBOrderRepository (from Layer!)
* EventHandler, EventBridgeEventHandler (form Layer!)

OrderHandler class takes and handles specific OrderRequest.

* Database Handling via DynamoEnhancedDBOrderRepository
* Event Handling via EventBridgeEventHandler

Specific result will be sent back to caller. 