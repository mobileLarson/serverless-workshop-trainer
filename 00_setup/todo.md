Database: 
  - sw-config-table: 
    - pk: config
    - value column: open = true 

  - sw-order-counter-table: 
    - PK: order-counter 
    - value column: currentValue = 0

Optional: 
  DataBase:
    - sw-config-table:
      - pk: menu
      - value column: entries = dynamodb-menu-config.json einspielen
  EventBridge: 
    - Regel einspielen: 
      - logAllEvents: https://workshop.serverlesscoffee.com/2-events/2-log-all.html
      - new order created