# 🍽️ Restaurant System

## 📌 Overview

Restaurant System is a Java-based application designed to facilitate restaurant order management. The system consists of two applications that communicate via TCP/IP:

- **Host Application** 🖥️: Manages the menu, creates orders, tracks their status.
- **Client Application** 📱: Displays real-time updates on orders and tracks product delivery.

The system was developed as a freelance solution for a company to streamline restaurant workflow.

## ✨ Features

### 🔹 Host Application

- 📜 Retrieves the menu from a database (or from a local `.txt` file if offline).
- 🧾 Creates and manages orders (bills/receipts).
- ✅ Tracks the status of each product in an order (e.g., delivered or pending).
- 💳 Monitors payment status.
- ⏳ Records product delivery timestamps.
- 🔄 Supports multiple client applications simultaneously and updates them with current orders.

### 🔹 Client Application

- 📡 Receives real-time updates on active orders.
- 📋 Displays the status of each order and its products.

## 🛠️ Technologies Used

- **Programming Language**: Java ☕
- **Networking**: TCP/IP communication 🌐
- **Database**: SQL-based (with local `.txt` backup option) 🗄️
- **UI**: Java Swing 🎨

## 🚀 Installation & Running the Application

1. Clone the repository:
   ```sh
   git clone https://github.com/Vincenzo0611/Restaurant_System.git
   ```
2. Navigate to the project directory:
   ```sh
   cd Restaurant_System
   ```
3. Compile the Java files:
   ```sh
   javac -d bin src/**/*.java
   ```
4. Run the Host Application:
   ```sh
   java -cp bin com.yourpackage.HostApplication
   ```
5. Run the Client Application:
   ```sh
   java -cp bin com.yourpackage.ClientApplication
   ```
6. Ensure that both applications are connected via the same network for TCP/IP communication.

## 📸 Screenshots
![Host](https://github.com/user-attachments/assets/724ec8cb-76bc-40aa-bd45-c1331936034c)
![Client](https://github.com/user-attachments/assets/d13dfde8-b3a0-449e-8040-1fea1c6d617e)


## 🔮 Future Enhancements

- 🎨 Improve UI design.
- 📴 Enhance offline functionality.
- 📲 Add a tablet interface for waiters to take orders.

