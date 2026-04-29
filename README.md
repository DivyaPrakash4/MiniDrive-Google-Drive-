# 🚀 MiniDrive - Secure Cloud Storage

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Spring Boot](https://img.shields.io/badge/Backend-Spring%20Boot-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![React](https://img.shields.io/badge/Frontend-React-blue.svg)](https://reactjs.org/)
[![AWS S3](https://img.shields.io/badge/Storage-AWS%20S3-orange.svg)](https://aws.amazon.com/s3/)

**MiniDrive** is a modern, full-stack cloud storage application designed for security and speed. It provides users with a seamless interface to upload, download, and manage their personal files, all backed by the industrial-grade reliability of AWS S3.

---

## ✨ Key Features

- **🔐 Secure Authentication**: Multi-layered security with JWT (JSON Web Token) for stateless authentication.
- **☁️ AWS S3 Integration**: High-performance file storage with direct cloud integration.
- **📊 Interactive Dashboard**: A sleek, responsive interface to manage your digital assets.
- **📥 One-Click Management**: Effortlessly upload, download, or delete files with real-time updates.
- **⚡ Fast Performance**: Built with Vite and Spring Boot for near-instantaneous load times and API responses.

---

## 🛠️ Technology Stack

| Layer | Technologies |
| :--- | :--- |
| **Frontend** | React 19, Vite, Axios, Modern CSS |
| **Backend** | Java 17+, Spring Boot 3.x, Spring Security, Spring Data JPA |
| **Database** | MySQL 8.0 |
| **Storage** | AWS S3 (Simple Storage Service) |
| **Security** | JWT Authentication, BCrypt Password Hashing |

---

## 📂 Project Structure

```text
MiniDrive/
├── MiniDrive/               # Spring Boot Backend (Maven)
│   ├── src/main/java        # Controllers, Services, Entities, Config
│   └── src/main/resources   # application.properties & Static assets
├── minidrive-frontend/      # React Frontend (Vite)
│   ├── src/                 # Pages (Login, Dashboard), API, Components
│   └── public/              # Static public assets
└── mini-drive-backend.pem   # AWS EC2 Key Pair (Deployment)
```

---

## 🚀 Getting Started

Follow these steps to get your own instance of MiniDrive up and running.

### 📋 Prerequisites
- **JDK 17** or higher
- **Node.js** (v18+)
- **MySQL** Server
- **AWS IAM User** with `AmazonS3FullAccess` permissions

### 1️⃣ Database Setup
Create a new MySQL database for the project:
```sql
CREATE DATABASE minidrive_db;
```

### 2️⃣ Backend Configuration
1. Navigate to the backend directory:
   ```bash
   cd MiniDrive/MiniDrive
   ```
2. Open `src/main/resources/application.properties` and update your credentials:
   ```properties
   spring.datasource.username=YOUR_MYSQL_USER
   spring.datasource.password=YOUR_MYSQL_PASSWORD
   
   aws.access-key=YOUR_AWS_ACCESS_KEY
   aws.secret-key=YOUR_AWS_SECRET_KEY
   aws.s3.bucket-name=YOUR_BUCKET_NAME
   ```
3. Run the backend server:
   ```bash
   ./mvnw spring-boot:run
   ```

### 3️⃣ Frontend Configuration
1. Navigate to the frontend directory:
   ```bash
   cd MiniDrive/minidrive-frontend
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the development server:
   ```bash
   npm run dev
   ```
4. Visit `http://localhost:5173` to start using MiniDrive!

---

## 🔒 Security Features
- **Stateless Auth**: Uses JWT to handle user sessions securely.
- **CORS Configured**: Secure communication between Frontend and Backend.
- **Encrypted Storage**: Files are stored securely on AWS S3 with restricted access.

## 🤝 Contributing
Contributions are what make the open-source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License
Distributed under the MIT License. See `LICENSE` for more information.

---
Developed with ❤️ by [Your Name]
