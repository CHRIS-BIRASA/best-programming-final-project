# 🚛 Cargo Truck Fuel Tracker

A comprehensive fuel management system for cargo truck operations with role-based access control and real-time tracking capabilities.

## 📋 Problem Statement

Manual fuel tracking in cargo truck operations leads to:
- Inefficient fuel management
- Cost overruns and budget issues
- Poor accountability
- Lack of real-time data
- Difficulty in generating reports

## 🎯 Solution

Digital fuel tracking system with role-based access for drivers, managers, and admins providing automated cost calculations and comprehensive reporting.

## ✨ Features

- **Role-Based Access Control**: Admin, Manager, and Driver roles
- **Real-time Fuel Logging**: Track fuel consumption, costs, and mileage
- **Automated Calculations**: Automatic fuel cost computation
- **Truck Management**: Assign drivers to trucks
- **User Management**: Admin can manage users and roles
- **Modern GUI**: Java Swing interface with gradient design
- **Database Integration**: MySQL backend with proper indexing

## 🛠️ Technology Stack

- **Frontend**: Java Swing
- **Backend**: Java with MVC Architecture
- **Database**: MySQL 8.0
- **Design Patterns**: DAO, Singleton, MVC
- **Deployment**: Docker containers

## 🏗️ Architecture

```
┌─────────────┐    ┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│    View     │───▶│ Controller  │───▶│     DAO     │───▶│  Database   │
│ (Swing GUI) │    │   (Logic)   │    │  (Data)     │    │   (MySQL)   │
└─────────────┘    └─────────────┘    └─────────────┘    └─────────────┘
```

## 🚀 Quick Start

### Prerequisites
- Java 11 or higher
- MySQL 8.0
- Docker (optional)

### Local Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd CargoTruckFuelTracker
   ```

2. **Setup MySQL Database**
   ```bash
   mysql -u root -p < database_setup.sql
   mysql -u root -p < sample_data.sql
   ```

3. **Compile and Run**
   ```bash
   javac -cp "lib/mysql-connector-j-8.2.0.jar" -d . src/*/*.java
   java -cp ".:lib/mysql-connector-j-8.2.0.jar" view.Main
   ```

### Docker Setup

1. **Install XQuartz (macOS only)**
   ```bash
   brew install --cask xquartz
   ```

2. **Run with Docker**
   ```bash
   ./run-docker-gui.sh
   ```

## 👥 User Roles

### Admin
- Manage users (create, edit, delete)
- Manage trucks
- View all fuel logs
- Generate reports

### Manager
- View fuel logs
- Generate reports
- Monitor truck performance

### Driver
- Log fuel entries
- View own fuel history
- Update truck mileage

## 📊 Database Schema

### Users Table
```sql
- user_id (Primary Key)
- name, role, username, password
- created_at, updated_at
```

### Trucks Table
```sql
- truck_id (Primary Key)
- truck_code, model, license_plate
- assigned_driver_id (Foreign Key)
```

### Fuel Logs Table
```sql
- log_id (Primary Key)
- truck_id, user_id (Foreign Keys)
- date, fuel_amount, fuel_price, mileage
- fuel_cost (Calculated field)
```

## 🔐 Default Login

```
Username: admin
Password: admin123
Role: Admin
```

## 📁 Project Structure

```
CargoTruckFuelTracker/
├── src/
│   ├── dao/           # Data Access Objects
│   ├── model/         # Entity classes
│   └── view/          # GUI components
├── lib/               # Dependencies
├── docker-compose.yml # Docker configuration
├── Dockerfile         # Container setup
└── database_setup.sql # Database schema
```

## 🐳 Docker Commands

```bash
# Build and run
docker-compose up --build

# Run in background
docker-compose up -d

# Stop containers
docker-compose down

# View logs
docker-compose logs app
```

## 🔧 Configuration

Update database connection in `DatabaseConnection.java`:
```java
private static final String URL = "jdbc:mysql://localhost:3306/cargo_fuel_tracker_db";
private static final String USERNAME = "root";
private static final String PASSWORD = "your_password";
```

## 🤝 Contributing

1. Fork the repository
2. Create feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🐛 Troubleshooting

### GUI not showing in Docker
- Ensure XQuartz is installed and running
- Run `xhost +localhost` in XQuartz terminal
- Check DISPLAY environment variable

### Database connection issues
- Verify MySQL is running
- Check connection credentials
- Ensure database exists

### Compilation errors
- Verify Java version (11+)
- Check MySQL connector JAR path
- Ensure all dependencies are present

## 📞 Support

For support and questions, please open an issue in the GitHub repository.