# Serverless Workshop

## Übung 0: Setup
Bevor wir mit dem Serverless-Workshop und den Workshop-Übungen beginnen, gilt 
es zunächst unser Cloud-Environment vorzubereiten. 

Zu diesem Zweck erstellen wir mit Hilfe eines [CloudFormation](https://aws.amazon.com/de/cloudformation/)-Templates gemeinsam die für unseren Workshop notwendigen Cloud-Komponenten. 

> **Infrastructure as Code**: Für das Anlegen der gewünschten Cloud-Komponenten nutzen wir ein "Zielbild", welches in einem IaC Template hinterlegt ist. 

### Schritt 1: Anmelden an der AWS Konsole
Melde dich mit deinen AWS-Credentials an der [AWS-Konsole](https://signin.aws.amazon.com/signin) an. 

> **Best Practices**: Nutze aus Gründen der Sicherheit zum Anmelden bei AWS einen IAM-Benutzer mit eingeschränkten Zugriffsrechten und nicht den Stammbenutzer. Der Stammbenutzer hat unbegrenzten Zugriff auf den Account.    

### Schritt 2: CloudFormation-Template einspielen

Gehe zum AWS-Service [CloudFormation](https://console.aws.amazon.com/cloudformation/) in der von dir 
ausgewählten Region (z.B. eu-central-1 aka Frankfurt).

Zum Einspielen des CloudFormation-Templates des Serverless-Workshops gehe auf **Stack erstellen** und 
wähle dort den Standard **Mit neuen Ressourcen** aus:  

* _Stack erstellen_ > _Mit neuen Ressourcen (Standard)_

Im Dialog-Fenster **Stack erstellen** wähle unter ... aus:  

* "Voraussetzung – Vorlage vorbereiten" > _Vorlage ist bereit_
* "Vorlage angeben" > _Eine Vorlagedatei hochladen_
* "Datei auswählen" > _00___setup/infrastructureAsCode/serverless-workshop.yaml_
* _weiter_

Im Dialog-Fenster **Stack-Details abgeben** trage unter ... ein: 

* "_Stack-Name_" > _MEIN STACK NAME_
* _weiter_

Im Dialog-Fenster **Stack-Optionen konfigurieren**

* _weiter_

Im Dialog-Fenster **MEIN STACK NAME prüfen** 

* _Berechtigungen für erforderliche Transformationen auswählen_
* _Absenden_

Im Anschluss werden die für den Serverless-Workshop notwendigen Ressourcen erstellt.
 
> **Achtung**: Das Erstellen der Ressourcen kann eine kurze Weile dauern. 

Im Anschluss sollte es drei DynamoDB Tabellen 

- sw-config-table
- sw-order-table
- sw-order-counter table

sowie einen EventBridge Service Bus

- sw-service-bus

geben.

### Schritt 3: Tabellen initial füllen 

Mit Hilfe von CloudFormation lassen sich zwar die benötigten Ressourcen erstellen, deren anschließende Initialisierung - falls notwendig - ist aber mittels CloudFormation alles andere als trivial. 

Wir nutzen daher einen kleinen Trick und werden die Initialisierung via Serverless Function vornehmen. Schließlich ist dies ja ein Serverless Workshop ;-) 

Alle dazu notwendigen Schritte gehen wir gemeinsam durch. 

### Diskutierenswert ... 

Frage an dich selbst: 
> Welchen Vorteil hat das Nutzen eines Infrastructure as Code Templates zum Anlegen der gewünschten Cloud-Komponenten. 
 
Frage an dich selbst:
> Sind mit der Verwendung von IaC im Allgemeinen und CloudFormation im Speziellen auch Nachteile verbunden?




