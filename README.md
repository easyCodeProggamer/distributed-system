# OpenTelemetry Documents



'''

https://github.com/open-telemetry/opentelemetry-java-instrumentation

https://github.com/open-telemetry/opentelemetry-java-instrumentation/blob/main/docs/logger-mdc-instrumentation.md

'''



Run OTEL agent(windows command)

'''

java -javaagent:C:/Users/Mriganka/Downloads/opentelemetry-javaagent.jar -Dotel.service.name=demoOpenTelemetry -Dotel.exporter.otlp.protocol=grpc -Dotel.exporter.otlp.endpoint=http://0.0.0.0:4317 -Dotel.instrumentation.jvm-metrics.enabled=true -jar C:/Users/Mriganka/Downloads/demoOpenTelemetry/demoOpenTelemetry/build/libs/demoOpenTelemetry-0.0.1-SNAPSHOT.jar

'''



'''

java -javaagent:C:/Users/Mriganka/Downloads/opentelemetry-javaagent.jar -Dotel.service.name=externalOpenTelemetry -Dotel.exporter.otlp.protocol=grpc -Dotel.exporter.otlp.endpoint=http://0.0.0.0:4317 -Dotel.instrumentation.jvm-metrics.enabled=true -jar C:/Users/Mriganka/Downloads/externalOpenTelemetry/externalOpenTelemetry/build/libs/externalOpenTelemetry-0.0.1-SNAPSHOT.jar

'''

Jaeger-UI: 

'''

http://localhost:16686/search

'''



premitheus-UI: 



'''

http://localhost:9090/query

'''

premitheus sample query: 



'''

jvm\_memory\_used\_bytes

'''

