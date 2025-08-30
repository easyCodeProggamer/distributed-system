package com.openTelemetryMdc.openTelemetryMdc.service;

import io.opentelemetry.api.trace.Span;

public class Service {

    public void mySpanAndTrace(){
        String traceId = Span.current().getSpanContext().getTraceId();
        String spanId = Span.current().getSpanContext().getSpanId();
    }
}
