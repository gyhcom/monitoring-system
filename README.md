# 🚀 모놀리식 + 레이어드 아키텍처 기반 실시간 장애 감지 시스템

> **Kafka + Redis + Spring Boot + Lambda를 활용한 실시간 장애 감지 시스템**  
> 장애 감지를 Kafka 메시징으로 처리하고, Redis Pub/Sub을 활용한 실시간 모니터링 기능을 제공합니다.  
> **Slack 알람 연동**을 통해 장애 발생 시 즉시 알림을 받을 수 있습니다. 📢

---

## 🏗 **프로젝트 개요**
✔ **모놀리식 아키텍처이지만 레이어드 구조 적용 (유지보수 용이)**  
✔ **Kafka를 이용한 장애 이벤트 처리 (Producer / Consumer)**  
✔ **Redis Pub/Sub을 활용한 실시간 장애 모니터링**  
✔ **Lambda를 활용한 데이터 변환 및 필터링 최적화**  
✔ **Slack 연동을 통한 장애 발생 시 알림 발송**  
✔ **Spring Boot Actuator를 활용한 헬스 체크 API**  
✔ **Prometheus + Grafana 연동 가능 (옵션, 미구현)**

---

## 🛠 **기술 스택**
| 기술 | 사용 목적 |  
|------|----------|  
| **Java 17** ☕ | 최신 Java 기능 활용 (Record, Lambda) |  
| **Spring Boot 3.4.2** 🌱 | 전체 백엔드 프레임워크 |  
| **Spring Kafka** 🎯 | 장애 이벤트 메시징 (Producer/Consumer) |  
| **Spring Data Redis** 🔥 | 장애 상태 저장 및 실시간 Pub/Sub |  
| **Spring Boot Actuator** 📊 | 헬스 체크 API (`/actuator/health`) |  
| **Spring WebFlux (WebClient)** 🌐 | Slack API 연동 |  
| **Docker** 🐳 | Kafka, Redis 컨테이너 실행 |  
| **Gradle (Groovy DSL)** 📦 | 프로젝트 빌드 및 관리 |  

---

## 📂 **프로젝트 구조 (레이어드 아키텍처)**

📦 monitoring-system   
┣ 📂 src/main/java/com/example/monitoring   
┃ ┣ 📂 config         # Kafka, Redis 설정   
┃ ┣ 📂 controller     # REST API 제공 (헬스체크 API)   
┃ ┣ 📂 service        # Kafka Producer, Consumer, Redis Pub/Sub 로직   
┃ ┣ 📂 repository     # Redis 연동 (장애 상태 저장)   
┃ ┣ 📂 domain         # DTO, 데이터 모델   
┃ ┣ 📂 listener       # Kafka & Redis 이벤트 리스너   
┃ ┣ 📂 utils          # Slack 알람 유틸리티   
┃ ┣ 📜 MonitoringApplication.java  # 메인 실행 파일   
┣ 📂 resources   
┃ ┣ 📜 application.yml  # Kafka & Redis 환경 설정   
┣ 📜 build.gradle   # Gradle 빌드 파일   
┣ 📜 README.md      # 프로젝트 설명   
┣ 📜 Dockerfile     # Docker 환경 구성   
┗ 📜 docker-compose.yml # Kafka, Redis 실행용 Docker Compose 설정   

---

## 🔥 **주요 기능**
### ✅ **헬스 체크 API (`/health`)**
- `/actuator/health` 엔드포인트를 활용하여 서버 상태 확인
- `/health/simulate-down` 호출 시 장애 발생 이벤트 Kafka로 전송

### ✅ **Kafka Producer (장애 이벤트 전송)**
- 서버 장애 발생 시 `health-alerts` 토픽으로 이벤트 발행
- 장애 감지 후 Redis에 상태 저장

### ✅ **Kafka Consumer (Lambda 적용)**
- 장애 이벤트 수신 후 **Lambda를 활용하여 데이터 변환 & 필터링**
- 특정 조건 충족 시 **Slack 알람 발송**

### ✅ **Redis Pub/Sub (실시간 장애 모니터링)**
- Kafka Consumer에서 Redis Pub/Sub으로 장애 상태 전송
- Redis Subscriber가 장애 상태를 구독하여 **실시간 대시보드 업데이트 가능**

---

## 📦 **환경 설정**
### 📌 **`application.yml` (Kafka & Redis 설정)**
```yaml
server:
  port: 8080

spring:
  kafka:
    bootstrap-servers: localhost:9092
    consumer:
      group-id: "monitoring-group"

  data:
    redis:
      host: localhost
      port: 6379