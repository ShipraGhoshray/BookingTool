# Booking Service 

This is a Spring Boot 3.2.0 application that uses H2 as its database.
A Booking Reservation can be generated with Start Time, End Time and No of people attending.

Other related services: 
* Maintenance service
* Room service

#### Postman Collection: https://github.com/ShipraGhoshray/BookingTool/blob/master/postman/BookingTool.postman_collection.json

## Prerequisites
- Java 17 or later
- Maven 3.9.6 or later

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/ShipraGhoshray/BookingTool.git
cd booking-tool
```

### Building the Project
```bash
mvn clean install
```
### Running the Application
```bash
mvn spring-boot:run
```
The application will start and be accessible at http://localhost:8080.

This system allows users to create conference rooms and create reservations for booking conference rooms for an organisation.


## REST APIs

### POST /api/v1/events
#### Request
```json
{
    "startTime": "2024-08-27 11:15:00",
    "endTime": "2024-08-27 11:30:00",
    "noOfPeople": 6
}
```
#### Response
```json
{
    "id": 1,
    "date": "2024-08-27",
    "startTime": "2024-08-27 21:27:35",
    "endTime": "2024-08-27 21:27:35",
    "noOfPeople": 0,
    "room": {
        "roomId": 2,
        "roomName": "Beauty",
        "availability": true,
        "roomCapacity": 7
    }
}
```

### GET /api/v1/maintainence
#### Response
```json
[
    {
        "slotId": 1,
        "startTime": "2024-08-27T09:00:00",
        "endTime": "2024-08-27T09:15:00"
    },
    {
        "slotId": 2,
        "startTime": "2024-08-27T13:00:00",
        "endTime": "2024-08-27T13:15:00"
    },
    {
        "slotId": 3,
        "startTime": "2024-08-27T17:00:00",
        "endTime": "2024-08-27T17:15:00"
    }
]
```
### POST /api/v1/rooms
#### Request
```json
{
    "roomName": "TestRoom",
    "roomCapacity": 5
}
```
#### Response
```json
{
    "roomId": 5,
    "roomName": "TestRoom",
    "availability": true,
    "roomCapacity": 5
}
```
### GET /api/v1/rooms
#### Response
```json
[
    {
        "roomId": 1,
        "roomName": "Amaze",
        "availability": true,
        "roomCapacity": 3
    },
    {
        "roomId": 2,
        "roomName": "Beauty",
        "availability": true,
        "roomCapacity": 7
    },
    {
        "roomId": 3,
        "roomName": "Inspire",
        "availability": true,
        "roomCapacity": 12
    },
    {
        "roomId": 4,
        "roomName": "Strive",
        "availability": true,
        "roomCapacity": 20
    },
    {
        "roomId": 5,
        "roomName": "TestRoom",
        "availability": true,
        "roomCapacity": 5
    }
]
```

### GET /api/v1/rooms/Inspire
#### Response
```json
[
    {
        "roomId": 3,
        "roomName": "Inspire",
        "availability": true,
        "roomCapacity": 12
    }
]
```

### DELETE /api/v1/rooms/5
#### Response
```json
200 OK
```