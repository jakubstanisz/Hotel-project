CREATE TABLE rooms (
    id SERIAL PRIMARY KEY,
    room_number INT UNIQUE NOT NULL,
    room_type VARCHAR(50) NOT NULL,
    price_per_night DECIMAL(10, 2) NOT NULL,
    is_available BOOLEAN DEFAULT true
);

CREATE TABLE guests (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    email VARCHAR(100) UNIQUE
);

CREATE TABLE employees (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE,
    password VARCHAR(100),
    job_title VARCHAR(50)
);

CREATE TABLE reservations (
    id SERIAL PRIMARY KEY,
    room_id INT REFERENCES rooms(id),
    guest_id INT REFERENCES guests(id),
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    total_cost DECIMAL(10, 2)
);

INSERT INTO rooms (room_number, room_type, price_per_night, is_available) VALUES 
(101, 'SINGLE', 150.00, true),
(202, 'DOUBLE', 250.00, true),
(303, 'SUITE', 500.00, true);

INSERT INTO guests (first_name, last_name, email) VALUES 
('Jan', 'Kowalski', 'jan.kowalski@email.com'),
('Anna', 'Nowak', 'a.nowak@example.pl'),
('Robert', 'Lewandowski', 'rl9@football.com');

INSERT INTO employees (username, password, job_title) VALUES 
('admin', 'admin123', 'Manager'),
('recepcja1', 'haslo123', 'Receptionist'),
('kasia_h', 'secure_pass', 'Receptionist');

INSERT INTO reservations (room_id, guest_id, start_date, end_date, total_cost) VALUES 
(1, 1, '2026-04-01', '2026-04-05', 600.00),
(2, 2, '2026-05-10', '2026-05-12', 500.00),
(3, 3, '2026-06-20', '2026-06-21', 500.00);

CREATE OR REPLACE VIEW v_reservation_details AS
SELECT 
    r.room_number, 
    g.first_name, 
    g.last_name, 
    res.start_date, 
    res.end_date, 
    res.total_cost
FROM reservations res
JOIN rooms r ON res.room_id = r.id
JOIN guests g ON res.guest_id = g.id;

CREATE OR REPLACE VIEW v_available_rooms AS
SELECT 
    room_number, 
    room_type, 
    price_per_night
FROM rooms
WHERE is_available = true;

CREATE OR REPLACE VIEW v_guest_spending AS
SELECT 
    g.first_name, 
    g.last_name, 
    COUNT(res.id) AS total_reservations, 
    SUM(res.total_cost) AS total_spent
FROM guests g
JOIN reservations res ON g.id = res.guest_id
GROUP BY g.id, g.first_name, g.last_name;