# 191HK-microservices：一九姨管家（居家清潔派工平台）

大數智能教育 Java 後端專題（自訂題目）｜學員：王彥皓

用 Spring Boot 微服務重寫實際營運中的居家清潔與防霉派工系統。架構說明見 [docs/architecture.md](docs/architecture.md)。

## 一鍵啟動

需求：Docker（含 Compose v2）

```bash
docker compose up -d --build
curl -i http://localhost:8081/actuator/health   # booking-service → 200
curl -i http://localhost:8082/actuator/health   # dispatch-service → 200
```

第一次啟動要下載映像檔並編譯，大約需要 3–5 分鐘。

| 服務 | 埠 | 說明 |
|---|---|---|
| booking-service | 8081 | 客戶、地址、預約 |
| dispatch-service | 8082 | 派工單、指派與改派歷程 |
| mysql | 3307 | booking_db、dispatch_db（Volume：`mysql-data`） |

試打測試資料：

```bash
curl http://localhost:8081/api/companies/1/customers
curl http://localhost:8081/api/customers/1/bookings
curl http://localhost:8082/api/companies/1/jobs
curl http://localhost:8082/api/jobs/1/assignments
```

## 設定

`docker-compose.yml` 內的預設密碼只供本機開發。要改密碼就複製 `.env.example` 成 `.env` 再修改；`.env` 不進版控。

## 測試

```bash
cd booking-service && mvn test
cd dispatch-service && mvn test
```

## 里程碑

- m1（架構與骨架）：架構文件 v1、booking／dispatch 健康檢查、Compose 一鍵啟動、Entity 關聯與測試資料
- m2（功能與 AI）：billing、dashboard（CQRS）、ai-assist（Spring AI＋Chroma）、RabbitMQ、Keycloak、Gateway
- m3（最終交付）
