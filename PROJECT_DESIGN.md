# Cargo Truck Fuel Tracker - Project Design

## Project Overview
**Problem**: Manual fuel tracking leads to inefficient fuel management, cost overruns, and poor accountability in cargo truck operations.

**Solution**: Digital fuel tracking system with role-based access for drivers, managers, and admins.

## System Architecture Diagrams

### 1. Activity Diagram - Fuel Log Entry Process
```
[Driver Login] → [Select Truck] → [Enter Fuel Data] → [Submit Log] → [Database Update] → [Confirmation]
```

### 2. Data Flow Diagram - System Overview
```
Driver Input → GUI Layer → DAO Layer → Database → Reports → Admin Dashboard
```

### 3. Sequence Diagram - User Authentication
```
User → LoginFrame → UserDAO → Database → Authentication Result → Dashboard
```

## Design Patterns Used
- **DAO Pattern**: Data Access Objects for database operations
- **MVC Pattern**: Model-View-Controller architecture
- **Singleton Pattern**: DatabaseConnection class

## Technology Stack
- **Frontend**: Java Swing
- **Backend**: Java
- **Database**: MySQL
- **Architecture**: MVC with DAO Pattern