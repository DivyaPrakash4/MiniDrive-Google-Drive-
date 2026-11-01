# 🚀 MiniDrive - Secure Cloud Storage Platform

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java 21](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Backend-Spring%20Boot%203-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![React](https://img.shields.io/badge/Frontend-React%2019-blue.svg)](https://reactjs.org/)
[![AWS S3](https://img.shields.io/badge/Storage-AWS%20S3-orange.svg)](https://aws.amazon.com/s3/)
[![AWS EC2](https://img.shields.io/badge/Compute-AWS%20EC2-red.svg)](https://aws.amazon.com/ec2/)
[![AWS RDS](https://img.shields.io/badge/Database-AWS%20RDS%20MySQL-blue.svg)](https://aws.amazon.com/rds/)
[![AWS IAM](https://img.shields.io/badge/Security-AWS%20IAM-purple.svg)](https://aws.amazon.com/iam/)

**MiniDrive** is a cloud-native, full-stack personal storage platform designed for high performance, security, and scalability. It provides seamless file uploading, streaming, and management, backed by the industrial reliability of **AWS S3**, hosted on **AWS EC2**, and managed with an **AWS RDS MySQL** database.

---

## ✨ Key Features

- **🔐 Stateless Authentication**: Secure user registration and login powered by Spring Security, BCrypt hashing, and stateless JWT tokens.
- **☁️ AWS S3 & Pre-Signed URLs**: Direct, secure file downloads using time-delimited AWS S3 pre-signed URLs, eliminating backend server I/O bottlenecks.
- **🛡️ Enterprise IAM Security**: Configured for AWS IAM Instance Profiles—eliminating hardcoded AWS credentials on EC2 instances by leveraging temporary STS role credentials.
- **🗄️ Cloud Database with AWS RDS**: Production-ready data persistence using AWS RDS MySQL with Spring Data JPA and Hibernate.
- **📊 Responsive SPA Dashboard**: Clean React 19 + Vite dashboard featuring client-side file size/type validation guards and real-time state updates.
- **⚙️ Resilient EC2 Deployment**: Includes a `systemd` daemon service configuration for auto-restart and continuous uptime on AWS EC2 micro instances.

---

## 🛠️ Technology Stack

| Layer | Technologies |
| :--- | :--- |
| **Backend** | Java 21, Spring Boot 3.2, Spring Security 6, Spring Data JPA, Hibernate, AWS SDK v2 |
| **Frontend** | React 19, Vite, Axios, Modern CSS |
| **Database** | MySQL 8.0 / AWS RDS (Relational Database Service) |
| **Cloud (AWS)** | Amazon S3, Amazon EC2, Amazon RDS, Amazon IAM |
| **Security** | JWT (JSON Web Tokens), BCrypt Password Hashing, IAM Instance Profiles |
| **Build & DevOps** | Maven, Git, Linux `systemd` |

---

## 📂 Project Structure

```text
MiniDrive/
├── MiniDrive/               # Spring Boot Backend (Maven)
│   ├── src/main/java        # Controllers, Services, Security, Config, Entities
│   └── src/main/resources   # application.properties (Dynamic DB & S3 config)
├── minidrive-frontend/      # React Frontend (Vite)
│   ├── src/                 # Pages (Login, Dashboard), API, Components
│   └── .env.example         # Dynamic API endpoint configuration
├── minidrive.service        # Linux systemd auto-restart service for AWS EC2
└── README.md
```

---

## 🚀 Getting Started

Follow these steps to run MiniDrive locally or deploy to AWS.

### 📋 Prerequisites
- **JDK 21**
- **Node.js** (v18+)
- **MySQL Server** (Local or AWS RDS)
- **AWS Account** (Free Tier eligible for S3, EC2, RDS, and IAM)

---

### 1️⃣ Database Setup

#### Option A: Local MySQL
```sql
CREATE DATABASE minidrive_db;
```

#### Option B: AWS RDS (MySQL)
Create a Free Tier `db.t3.micro` MySQL instance on AWS RDS and obtain your endpoint URL:
`minidrive-db.xxxxxx.ap-south-1.rds.amazonaws.com`

---

### 2️⃣ Backend Configuration

1. Navigate to the backend directory:
   ```bash
   cd MiniDrive/MiniDrive
   ```
2. Open `src/main/resources/application.properties`. You can set credentials in the file or pass them as environment variables:
   ```properties
   # Database (Localhost or AWS RDS)
   spring.datasource.url=jdbc:mysql://localhost:3306/minidrive_db
   spring.datasource.username=root
   spring.datasource.password=YOUR_PASSWORD

   # AWS S3 Settings
   aws.region=ap-south-1
   aws.s3.bucket-name=YOUR_BUCKET_NAME

   # For Local Testing only (leave empty on EC2 when using an IAM Role):
   aws.access-key=YOUR_AWS_ACCESS_KEY
   aws.secret-key=YOUR_AWS_SECRET_KEY
   ```
3. Run the backend server:
   ```bash
   ./mvnw spring-boot:run
   ```

---

### 3️⃣ Frontend Configuration

1. Navigate to the frontend directory:
   ```bash
   cd MiniDrive/minidrive-frontend
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Configure the backend API URL:
   Copy `.env.example` to `.env`:
   ```env
   VITE_API_BASE_URL=http://localhost:8080
   ```
   *(When deploying, point this to your EC2 Public IP: `http://<EC2_PUBLIC_IP>:8080`)*

4. Start the frontend:
   ```bash
   npm run dev
   ```
5. Open `http://localhost:5173` in your browser!

---

### 4️⃣ AWS EC2 Deployment (Production)

1. **Package JAR**:
   ```bash
   cd MiniDrive/MiniDrive
   ./mvnw clean package -DskipTests
   ```
2. **Transfer to EC2**:
   ```bash
   scp -i mini-drive-backend.pem target/MiniDrive-0.0.1-SNAPSHOT.jar ubuntu@<EC2_PUBLIC_IP>:/home/ubuntu/
   ```
3. **Run as a Service with systemd**:
   Update `minidrive.service` with your RDS endpoint and S3 bucket, then enable it:
   ```bash
   sudo cp minidrive.service /etc/systemd/system/
   sudo systemctl daemon-reload
   sudo systemctl enable minidrive
   sudo systemctl start minidrive
   ```

---

## 🔒 Security Highlights

- **Stateless Architecture**: Client authentication handled via secure JWTs.
- **IAM Instance Profiles**: Spring Boot on EC2 uses `DefaultCredentialsProvider` to obtain temporary role credentials from the AWS Instance Metadata Service, removing the need for static API keys.
- **Time-Delimited Access**: File downloads utilize secure S3 Pre-Signed URLs valid for only 10 minutes.
- **Global CORS Config**: Configured to safely permit requests across client origins.

---

## 📄 License
Distributed under the MIT License. See `LICENSE` for more information.

---
Developed with ❤️ by **Divya Prakash**
