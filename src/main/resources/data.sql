INSERT INTO addresses (street, city, country, postal_code) VALUES
('Long Street', 'Auckland', 'New Zealand', '1010'),
('Short Street', 'Melbourne', 'Australia', '2020');

INSERT INTO passengers (address_id, first_name, last_name, middle_name, date_of_birth) VALUES
(1, 'John', 'Ben', 'Smith', '1984-01-02'),
(2, 'Jane', 'Sarah', 'Doe', '1969-09-06');

INSERT INTO airports (name, city, country, iata, icao) VALUES
('Auckland Airport', 'Auckland', 'New Zealand', 'AKL', 'NZAA'),
('Melbourne Airports', 'Melbourne', 'Australia', 'MEL', 'YMML');

INSERT INTO flights (number, origin_id, destination_id) VALUES
(10, 1, 2);

INSERT INTO bookings (flight_id, passenger_id, cabin_class, seat) VALUES
(1, 1, 'Economy', '2A'),
(1, 2, 'Business', '16F');
