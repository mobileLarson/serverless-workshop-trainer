# Serverless Workshop 

## Übung 2: AWS Lambda meets ... 


Serverless Functions stehen in der Regel nicht alleine da, sondern interagieren mit anderen Cloud-Komponenten. In dieser Übung schauen wir uns das Zusammenspiel zwischen Serverless Functions und weiteren AWS Cloud-Komponenten, wie der NoSQL Datenbank DynamoDB und dem Event-Service EventBridge an. 

Ziel dieser Übung ist es, ... 
> ein Verständnis für das Zusammenspiel der Serverless Functions mit den verschiedenen Cloud-Komponenten via dedizierter APIs zu vermitteln.  

### Step 01: Order Handler (simple)

Im ersten Teil der Übung schauen wir uns an, wie wir mittels Serverless Function eine Coffee Order in einer DynamoDB Tabelle abspeichern können. 

Das Beispiel befindet sich im Verzeichnis: 

```
02_order-manager/01_simple-order-manager

```

Der eingehende Request mit Informationen zu dem Kunden (Attribut: userId) und dem bestellten Getränk (Attribut: drink) landet zunächst in einer Serverless Function namens OrderHandler. 

Der Request Handler verarbeitet den eingehenden Request und ruft im Zuge der Verarbeitung einen Service auf, der die eigentliche Business-Logik ausführt. Dies beinhaltet u.a. das Speichern der eingegangenen Order via Repository. 


```
OrderHandler -> OrderService -> DynamoDBOrderRepository
```

#### Aufgaben: 

In den drei oben genannten Klassen finden sich To-dos, deren Umsetzung dabei helfen sollen, das Zusammenspiel der verschiedenen Komponenten zu verstehen. Am besten gehst du bei der Abarbeitung in der oben beschriebenen Reihenfolge vor. 

Sobald die Übung implementiert und via maven kompiliert wurde


```
mvn clean package 

```

kann das fertige Artefakt in die AWS Cloud hochgeladen und als Lambda Funktion bereitgestellt werden. 

> **TIPP**: Mit der Hilfe der Umgebungsvariable AUDIT_ENABLED = true werden Informationen zum eingehenden Request und ausgehendem Response ausgegeben. Die kann ggf bei der Fehlersuche helfen. 


Das Testing kann mittels folgender Payload erfolgen: 


```
{
	"userId" : "lars.roewekamp",
	"drink"  : "Americano grande"		
}
```

Als Ergebnis sollte die vollständige Bestellung im Status "confirmed" angezeigt werden: 

```
{
  "order": {
    "orderId": "979dba70-5de4-436f-a1b4-571f2a9edd5c",
    "orderNo": 170,
    "orderStatus": "confirmed",
    "userId": "lars.roewekamp",
    "drink": "Americano grande"
  }
}
```

> **ACHTUNG**: Da die Serverless Function auf eine DynamoDB Tabelle zugreift, muss die zugehörige Rolle mit den entsprechenden Rechten ausgestattet sein! Eine entsprechende Rolle namens "...-swOrderManagementRole-..." steht zur Auswahl zur Verfügung. 

### Step 02: Order CRUD Handler (advanced)

Für den zweiten Teil der Übung wollen wir unseren Order Manager in der Art erweitern, dass er nicht nur die CREATE-Funktion für eine Coffee Order ausführen kann, sondern alle CRUD-Operationen. 

Als Basis für die Übung dient das Verzeichnis: 

```
02_order-manager/02_advanced-order-manager

```

Die für die Übung relevante Serverless Function zur Entgegennahme der eingehenden CRUD-Requests findet sich, wie schon in der Übung zuvor, in der Klasse 

```
OrderHandler 
```

Anders als in der vorangegangenen Übung, führt die Serverless Function die gefragte Operation nicht direkt aus, sondern dient lediglich als Dispatcher für die verschiedenen CRUD-Operationen auf der Order-Entität. 

Das eigentliche Handling der jeweiligen CRUD-Anfragen findet in den dedizierten Order Handler Klassen (create, read, update und delete) statt. 

Auch in diesem Beispiel trennen wir wieder den Code für Infrastruktur und Business-Logik. D.h. die Handler-Klassen nehmen den Request entgegen und geben den Response zurück. Die eigentlich Abarbeitung der Business-Logik erfolgt wie gehabt innerhalb eines Services namens OrderService.  

#### Aufgaben: 

**Use-Case Flow:** Schaue dir für den Use-Case "create order" den konkreten Ablauf vom OrderHandler bis zum Datenzugriff (via Repository) an. 

> Wirf auch einen Blick in die Klasse OrderCounterService, in der via UpdateExpression eine fortlaufende OrderNo direkt auf der DynamoDB erzeugt wird.  

**Use-Case "Update Order":** 
Implementiere die fehlende Funktionalität für den Use-Case "update order". Schaue dir dazu die To-dos in den Klassen

* OrderHandler 
* UpdateOrderHandler
* OrderService
* DynamoDBOrderRepository

an. 

Falls du die AWS-CLI installiert und lokal entsprechende Credentials hinterlegt hast, kannst du deine Implementierung direkt mithilfe der Klasse 

* TestUpdateOrder

testen. 

Alternativ kannst du das OrderManagement-Artefakt auch in die AWS-Cloud hochladen und dort die Serverless Function des Order Handlers mit den zwei Test-Events aus dem Verzeichniss 

	/02_order-manager/02_advanced-order-manager

ausführen : 

Test-Event 1: "Neue Order erstellen": 

```
{
	"action" : "createOrder", 				  
	"order": {                    
		"userId" : "lars.roewekamp",
		"drink"  : "Americano grande"		
	}
}
```

Test-Event 2: Order ändern (MY-ORDER-ID ersetzen): 

```
{
	"action" : "updateOrder", 				  
	"order": {                    
	  "orderId": MY_ORDER_ID,
	  "drink"  : "Americano grande with EXTRA MILK"		
	}
}
```

> ACHTUNG: Auch für diese Übung gilt, dass die zugehörige Rolle mit den zugehörigen Rechten zum Zugriff auf DynamoDB ausgestattet sein muss! 

### Step 03: Order CRUD Handler (enhanced)

Die bisherige Version unseres Order Management Services birgt noch einiges an Verbesserungspotential in sich: 

* Mapping der Order-Attribute auf DB-Columns ist umständlich
* OrderHandler als Dispatcher != Single Responsibility Pattern
* Deploy-Artefakt ist sehr groß (ca 20 MB)


Als Basis für die Übung dient weiterhin das Verzeichnis: 

```
02_order-manager/02_advanced-order-manager

```

#### Mapping der Order-Attribute

Schauen wir uns zunächst das Problem des Mappings an. 

Im bisherigen Verlauf haben wir zum Mapping der Order-Attribute auf die Spalten der zugehörigen DynamoDB Tabelle den klassischen _DynamoDBClient_ verwendet. Leider ist dieser in seiner Handhabung alles andere als intuitiv. Insbesondere dann nicht, wenn man die Funktionalität eines annotationsbasierten OR-Mappers gewohnt ist. 

AWS bietet daher zusätzlich eine flexible High-Level Bibliothek zum direkten Mappen von Entitäten auf DynamoDB Tabellen an. 

Während in dem _AWS SDK for Java V1_ diese Aufgabe noch durch einen speziellen Mapper namens _DynamoDBMapper_ übernommen wurde, ist die Funktionalität in _AWS SDK for Java V2_ direkt in einen Enhanced DynamoDB Client eingeflossen (siehe auch [DynamoDBEnhancedClient](https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/dynamodb-enhanced-client.html)). 


Die Angabe des Mappings erfolgt durch Annotation der Entität  

* Order 

mit deren Hilfe via DynamoDBEnhancedClient ein typisierter Tabellenzugriff ermöglicht wird. 

#### Aufgabe 

Wie schon in der Übung zuvor wollen wir die fehlende Funktionalität für den Use-Case "update order" implementieren. Eine Herausforderung dabei ist, dass wir mit dem Request nur die zu ändernden Daten bekommen und somit die bestehende Order in der Datenbank nicht einfach durch die neue Order ersetzen können. 

Schaue dazu zunächst die Annotationen in der Klasse 

* Order 

an und implementiere im Anschluss die To-dos in der Klasse 

* DynamoDBEnhancedOrderRepository   

Das Testing der Änderungen kann wie im vorherigen Beispiel via 

* TestUpdateOrder

oder aber durch Hochladen des OrderManagement-Artefakts in die AWS Cloud erfolgen. 
Zum Testen können wieder die beiden oben aufgezeigten Test-Events verwendet werden: 


Test-Event 1: "Neue Order erstellen": 

```
{
	"action" : "createOrder", 				  
	"order": {                    
		"userId" : "lars.roewekamp",
		"drink"  : "Americano grande"		
	}
}
```

Test-Event 2: Order ändern (MY-ORDER-ID ersetzen): 

```
{
	"action" : "updateOrder", 				  
	"order": {                    
	  "orderId": MY_ORDER_ID,
	  "drink"  : "Americano grande with EXTRA MILK"		
	}
}
```


> **ACHTUNG**: Damit deine Änderungen zur Laufzeit zum Tragen kommen, musst du in der Klasse _OrderService_ das entsprechende Repository _DynamoDBEnhancedRepository_ anstelle von _DynamoDBRepository_ heranziehen. 


### Step 04: Order CRUD Handler (layered)

Nachdem das Problem des Mappings der Order-Attribute auf DB-Columns erledigt ist, bleiben nach wie vor zwei weitere Herausforderungen bestehen: 

* OrderHandler als Dispatcher != Single Responsibility Pattern
* Deploy-Artefakt ist sehr groß (ca 20 MB)

Für diesen Teil der Übung wechseln wir zu dem Order Manager (layered Version) im Verzeichnis: 

```
02_order-manager/03_layered-order-manager

```

#### Single Responsibility Pattern 

Serverless Functions sollten möglichst klein gehalten werden, um 

* geringe Startup-Zeiten und 
* geringen Speicherbedarf 

zu erreichen. Beides wirkt sich direkt auf die Latenz (aka UX) und die Kosten aus. Es ist daher Best Practice, jeder der vier CRUD-Operationen eine eigene Serverless Function zu spendieren. 

> **Least-Privilege-Principle**: Das Aufteilen der verschiedenen CRUD-Operationen in getrennt Serverless Functions hat den Vorteil, dass den einzelnen Funktionen unterschiedliche Zugriffsrechte / Rollen zugeordnet werden können. 

#### Aufgabe: 

**Use-Case Flow:** Schaue dir für den Use-Case "create order" den konkreten Ablauf vom CreateOrderHandler bis zum Datenzugriff (via Repository) an. Was fällt dir auf? 

> **Profi-Tipp**: Ein Blick in die pom.xml trägt eventuell zum besseren Verständnis bei ;-)

#### Layerd Deployment Artefact

Richtig! Die Klasse 

* CreateOrderHandler 

greift zwar auf ein Order-Repository und einen Event-Handler zu, diese sind aber im Projekt selbst nirgendwo zu finden. Und auch das via maven erstellte Artefakt ist deutlich kleiner als in den Beispielen zuvor. Wie funktioniert das? 

Ein Blick in die pom.xml verrät, dass eine Abhängigkeit des Projektes zu einem Artefakt namens 

* serverless-workshop-base-layer

besteht. Hierbei handelt es sich um einen sogenannten Lambda-Layer, also einer Art Bibliothek, welche zur Laufzeit separat und somit auch für andere Serverless Functions zur Verfügung steht. Du findest den zugehörigen Layer unter 

```
00_setup/lambda-layer/serverlessWorkshopBaseLayer

```

#### Aufgabe:

Erstelle via AWS Web Console einen Lambda-Layer auf Basis des ZIP-Files 

* serverless-workshop-base-layer.zip

im Verzeichnis 

```
00_setup/lambda-layer/_distribution

```

Nutze diesen Layer für die vier Serverless Functions 

* oms-create-order
* oms-read-order
* oms-update-order
* oms-delete-order

aus den gleichnamigen Projekten und teste dein neues Setup mit folgender Payload: 

Test-Event für **oms-create-order**: 

```
{                    
	"userId" : "lars.roewekamp",
	"drink"  : "Americano grande"		
}
```

Test-Event für **oms-read-order**: 

Lesen einer einzelnen Bestellung (via orderId):

```
{                    
	"orderId" : "MY_ORDER_ID"
}
```

Lesen aller Bestellungen, die einem bestimmten Filterkriterium entsprechen (via filter und filter-attributes): 

```
{                    
	"filter" : "MY_FILTER", 
	"filter-attributes": [
	    "filter 1", 
		"filter 2" 
	]
}
```

Info: 
> In der aktuellen Implementierung werden die angegeben Filter ignoriert. Der Call ohne orderId kann aber verwendet werden, um alle Bestellungen zu lesen. 

Test-Event für **oms-update-order**: 

```
{                    
	"orderId" : "MY_ORDER_ID"
	"drink" : "Americano grande with EXTRA MILK"
}
```


Test-Event für **oms-delete-order**: 

```
{                    
	"orderId" : "MY_ORDER_ID"
}
```

Ob die Tests deiner AWS Lambda Funktionen funktioniert haben oder nicht kannst du entweder direkt am Output erkennen oder aber durch einen Blick in die DynamoDB Datenbank. 

### Diskutierenswert ... 

Frage an dich selbst:
> Warum gibt es nach wie vor das umständliche Mapping zwischen den Attributen einer Entität und der zugehörigen DynamoDB Tabelle, wenn der gleiche Effekt via Annotationen erreicht werden kann? 

Frage an dich selbst:
> Welche Vorteile bringt die Trennung der einzelnen CRUD-Funktionen in mehrere Serverless Functions (noch) mit sich?

Frage an dich selbst: 
> Welche weiteren Layer könnten in unserer Anwendung sinnvoll sein? 

Frage an dich selbst: 
> Welchen Zweck verfolgen wir mit dem Event-Hanlder? 



