# 🧾 Invoice Renderer

> A modular and extensible invoice PDF generator using Spring Boot and a plugin-based architecture. Built with clean code principles and real-world customization features.

---

## ✨ Features

- 🔌 **Plugin-based architecture** – Easily add/remove invoice data sections (e.g. logo, buyer, tax, order info)
- 🖨️ **HTML → PDF rendering** via [Flying Saucer PDF](https://github.com/flyingsaucerproject/flyingsaucer)
- 🧩 Custom fields – Auto-generate values like order number, date-time, and tax
- 🖼️ Embedded logo/image support (local file path)
- 📤 API-driven (REST endpoint to trigger invoice rendering)
- 💡 Designed with clean separation of concerns for production readiness

---

## 🧱 Folder Structure

```
invoice-renderer/
├── controller/        # REST controller to trigger rendering
├── model/             # Invoice data model
├── plugins/           # Interface for plugin extension,Auto-wired plugin modules to populate invoice fields 
├── service/           # Core business logic to apply plugins and generate PDFs
├── util/              # PDF generation utility (Flying Saucer based)
├── templates/         # HTML (Thymeleaf-compatible) templates
├── static/images/     # Local files like logos
└── README.md          # Project overview
```

---

## 🚀 How to Run

### 1. Build the project
```bash
mvn clean install
```

### 2. Start the Spring Boot app
```bash
mvn spring-boot:run
```

### 3. Send a request
```bash
curl -X POST http://localhost:8080/invoices -H "Content-Type: application/json" 
  -d '{
    "transactionId": "TXN123456",
    "copyType": "ORIGINAL",
    "amount": 1500
  }'
```

🧾 PDF gets saved in the `/output` folder.

---

## 🧠 Sample Plugins

### ✔️ BuyerInfoPlugin
- Populates buyer details.

### ✔️ TaxInfoPlugin
- Calculates tax based on `copyType`

### ✔️ LogoPlugin
- Embeds a local logo from `/static/images/InvoiceLogo.png`

### ✔️ OrderInfoPlugin
- Fills order number, date-time, merchant name, currency, payment mode, delivery status, createdAt

---

## 🧩 Extending the Plugin System
To add a new plugin:

```java
@Component
public class CustomPlugin implements InvoicePlugin {
  public void apply(Invoice invoice) {
    invoice.setCustomField("...");
  }
}
```
Spring automatically discovers and applies it at runtime.

---

## 📁 Output Example

> PDF rendered at `output/invoice-TXN123456.pdf`

Includes:
- Transaction ID
- Copy Type
- Amount & Tax
- Logo
- Order Info (merchant, delivery status, etc.)
- Generated timestamps

---

## 🛠️ Tech Stack

- Java 17
- Spring Boot 3
- Maven
- Flying Saucer PDF renderer (XHTML → PDF)
- Lombok

---

## 🧪 Test Curl
```bash
curl -X POST http://localhost:8080/invoices \
  -H "Content-Type: application/json" \
  -d '{
    "transactionId": "TXN999001",
    "copyType": "ORIGINAL",
    "amount": 2500
  }'
```

---

## 💼 Ideal For
- Demonstrating plugin-based Java design
- Learning how to generate PDFs from HTML in Spring Boot
- Extending business logic with clean architecture

---
Need help adding GitHub Actions or contributing guidelines? Just ask! 💬
