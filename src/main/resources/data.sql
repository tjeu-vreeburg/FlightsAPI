INSERT INTO airports (id, name, city, country, iata, icao) VALUES
(1, 'Auckland Airport', 'Auckland', 'New Zealand', 'AKL', 'NZAA'),
(2, 'Melbourne Airports', 'Melbourne', 'Australia', 'MEL', 'YMML');

INSERT INTO flights (id, number, origin_id, destination_id) VALUES
(1, 10, 1, 2);

INSERT INTO bookings (id, flight_id, first_name, last_name, cabin_class, seat) VALUES
(1, 1, 'John', 'Smith', 'Economy', '2A'),
(2, 1, 'Jane', 'Doe', 'Business', '16F');