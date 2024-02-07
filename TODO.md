1) Region auch via ENV setzen, um so den Default in AwsClient Provider zu überschreiben! 
- DONE: AwsRegionProvider
- TODO: ordentliche Exceptions in den ResouceProvidern (DynamoDB), fall Fehler wegen falscher REGION


2) ANLEITUNG FÜR UI SETUP: 
Setup display APP 
- region auswählen: us-east-1
- cloudFormation Stack Ausgabe anzeigen für Dispplay APP UI
  - fehlende Werte via CloudShell
- cloudshell aufrufen: 
  - Pool ID? aws cognito-identity list-identity-pools --max-results 10
  - Host? aws iot describe-endpoint --endpoint-type iot:Data-ATS

UserID: lars.roewekamp@openknowledge.de
Password: sw-serverless