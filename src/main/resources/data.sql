INSERT INTO airports (name, city, country, iata, icao) VALUES
('Auckland Airport', 'Auckland', 'New Zealand', 'AKL', 'NZAA'),
('Melbourne Airports', 'Melbourne', 'Australia', 'MEL', 'YMML');

INSERT INTO flights (number, origin_id, destination_id) VALUES
(10, 1, 2);

INSERT INTO bookings (flight_id, first_name, last_name, cabin_class, seat) VALUES
(1, 'John', 'Smith', 'Economy', '2A'),
(1, 'Jane', 'Doe', 'Business', '16F');