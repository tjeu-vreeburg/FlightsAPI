# Flight API

A Spring Boot REST API for managing airports, flights and bookings.

- **Airports, flights, bookings** - Create, delete update and search each entity type
- **Validation and Error Handling** - Built in responses for handling all http requests
- **Filtering with Specifications** - Easily search for flights based on origin and destination filter

# Technologies
These are all of the different libaries, languages and frameworks used for developing this API.
- **Backend:** Sprint Boot 3
- **Database:** JPA (with Hibernate)
- **Build Tools**: Gradle
- **Testing:** JUnit, Mockito
- **Language:** Java 21
# Getting Started

#### Clone Repository
```
git clone https://github.com/tjeu-vreeburg/FlightsAPI.git
```
#### Configure Database
Update database configuration in `src/main/resources/application.properties`
```
spring.datasource.url=jdbc:sqlite:flightapi.db
spring.datasource.driver-class-name=org.sqlite.JDBC
spring.jpa.hibernate.ddl-auto=update
```
#### Build and Run application
```
./gradlew bootRun
```

# Testing
```
./gradlew test
```

# API Endpoints
### Flight  
| Method | Endpoint                    | Description                                    |
|--------|-----------------------------|------------------------------------------------|
| GET    | `/api/flights/search`       | Get all flights (supports pagination filters)  |
| GET    | `/api/flights/details/{id}` | Get a flight by ID                             |
| POST   | `/api/flights/create/`      | Create a new flight                            |
| PUT    | `/api/flights/update/{id}`  | Update an existing flight                      |
| DELETE | `/api/flights/cancel/{id}`  | Cancel a flight by ID                          |

Parameters for `GET /api/flights/search`
- `page`: The page number
- `size`: The amount of entries per page
- `origin`: City of origin
- `destination`: City of destination

### Airports 
| Method | Endpoint                     | Description                                    |
|--------|------------------------------|------------------------------------------------|
| GET    | `/api/airports`              | Get all airports (supports pagination filters) |
| GET    | `/api/airports/details/{id}` | Get an airport by ID                           |
| POST   | `/api/airports/create`       | Create a new airport                           |
| PUT    | `/api/airports/update/{id}`  | Update an airport                              |
| DELETE | `/api/airports/delete/{id}`  | Delete an airport by ID                        |

Parameters for `GET /api/airports`
- `page`: The page number
- `size`: The amount of entries per page

### Bookings
| Method | Endpoint                     | Description                                    |
|--------|------------------------------|------------------------------------------------|
| GET    | `/api/bookings`              | Get all bookings (supports pagination filters) |
| GET    | `/api/bookings/details/{id}` | Get a booking by ID                            |
| POST   | `/api/bookings/create`       | Create a new booking                           |
| DELETE | `/api/bookings/cancel/{id}`  | Cancel a booking                               |

Parameters for `GET /api/bookings`
- `page`: The page number
- `size`: The amount of entries per page

