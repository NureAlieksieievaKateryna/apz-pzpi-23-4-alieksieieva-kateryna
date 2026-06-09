package org.example.reminder.admin.facade;

import org.example.reminder.admin.facade.dto.ScalingConfigRequest;
import org.example.reminder.admin.facade.dto.ScalingConfigResponse;
import org.springframework.stereotype.Component;

@Component
public class AdminScalingFacade {

    public ScalingConfigResponse generate(ScalingConfigRequest r) {
        return new ScalingConfigResponse(
                generateKubernetes(r),
                generateDockerCompose(r),
                generateK6(r)
        );
    }

    private String generateKubernetes(ScalingConfigRequest r) {
        String hpa = "";

        if (Boolean.TRUE.equals(r.hpaEnabled())) {
            hpa = """
                    
                    ---
                    apiVersion: autoscaling/v2
                    kind: HorizontalPodAutoscaler
                    metadata:
                      name: subscription-backend-hpa
                    spec:
                      scaleTargetRef:
                        apiVersion: apps/v1
                        kind: Deployment
                        name: subscription-backend
                      minReplicas: %d
                      maxReplicas: %d
                      metrics:
                        - type: Resource
                          resource:
                            name: cpu
                            target:
                              type: Utilization
                              averageUtilization: %d
                    """.formatted(r.minReplicas(), r.maxReplicas(), r.targetCpu());
        }

        return """
                apiVersion: apps/v1
                kind: Deployment
                metadata:
                  name: subscription-backend
                spec:
                  replicas: %d
                  selector:
                    matchLabels:
                      app: subscription-backend
                  template:
                    metadata:
                      labels:
                        app: subscription-backend
                    spec:
                      containers:
                        - name: subscription-backend
                          image: subscription-platform:latest
                          ports:
                            - containerPort: 8080
                          resources:
                            limits:
                              cpu: "%d"
                              memory: "%dMi"
                
                ---
                apiVersion: v1
                kind: Service
                metadata:
                  name: subscription-backend-service
                spec:
                  selector:
                    app: subscription-backend
                  ports:
                    - port: 8080
                      targetPort: 8080
                  type: LoadBalancer
                %s
                """.formatted(r.replicas(), r.cpu(), r.memory(), hpa);
    }

    private String generateDockerCompose(ScalingConfigRequest r) {
        return """
                version: "3.9"
                
                services:
                  postgres:
                    image: postgres:17
                    environment:
                      POSTGRES_DB: db
                      POSTGRES_USER: test
                      POSTGRES_PASSWORD: 123
                    ports:
                      - "5432:5432"
                
                  backend:
                    image: subscription-platform:latest
                    deploy:
                      replicas: %d
                      resources:
                        limits:
                          cpus: "%d"
                          memory: %dM
                    environment:
                      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/db
                      SPRING_DATASOURCE_USERNAME: test
                      SPRING_DATASOURCE_PASSWORD: 123
                    ports:
                      - "8080:8080"
                    depends_on:
                      - postgres
                """.formatted(r.replicas(), r.cpu(), r.memory());
    }

    private String generateK6(ScalingConfigRequest r) {
        return """
                import http from 'k6/http';
                import { sleep } from 'k6';
                
                export const options = {
                  vus: %d,
                  duration: '%ds',
                };
                
                export default function () {
                  http.get('http://localhost:8080/admin/business/1/dashboard');
                  sleep(1);
                }
                """.formatted(r.users(), r.duration());
    }
}
