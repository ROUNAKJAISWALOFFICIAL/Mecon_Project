# 🏢 Asset Management System

A full-stack web application to manage organizational assets efficiently, built during an internship at **MECON Limited, India**.

This system supports both **Admin** and **Employee** panels, providing features like asset tracking, assignment, employee management, request history, and more — all from a single interface.

---

## 🚀 Live Demo

🔗 **Frontend + Backend (Hosted on Render)**  
👉 [https://mecon-project.onrender.com](https://mecon-project.onrender.com)  
> ⚠️ *May take 30–50 seconds to load due to free-tier cold starts on Render.*

---

## 🧪 Test Credentials

| Role     | Email                 | Password  |
|----------|-----------------------|-----------|
| Admin    | `admin`               |  `pass`   |
| Employee | `emp1 `               |  `pass`   |

---

## 🧩 Features

### 👨‍💼 Admin Panel
- 📊 Dashboard with real-time KPIs
- 🧾 Manage Assets (Add, Edit, Delete, View)
- 🧑‍💼 Manage Employees
- 🔄 Assign only *available* assets
- 📄 View employee asset reports
- 🔐 Secure logout

### 👷 Employee Panel
- 👁️ View assigned assets
- ✍️ Request new assets
- 🔁 Return assets (change status)
- 📜 Request history log
- 🔐 Secure logout
---

## ⚙️ Tech Stack

- **Backend:** Spring Boot, Spring Data JPA
- **Frontend:** HTML, CSS, JavaScript (served from `/static`)
- **Database:** PostgreSQL (via [Neon](https://neon.tech))
- **Hosting:** Render

---

## 🛠️ Project Setup

### 📁 Clone the Repository

```bash
git clone https://github.com/your-username/asset-management-system.git
cd asset-management-system

## 📦 Build & Run Locally

Make sure **Java 17+** and **Maven** are installed.

```bash
./mvnw clean install
java -jar target/*.jar
The app will run at:
👉 http://localhost:8080

---

👨‍💻 Contributors
Rounak Jaiswal

Ranbeer Singh

Shubham Kumar

Built as part of our internship at MECON Limited, India 🇮🇳
---
🏁 License
This project is open-source and available for learning and educational use under the MIT License.
