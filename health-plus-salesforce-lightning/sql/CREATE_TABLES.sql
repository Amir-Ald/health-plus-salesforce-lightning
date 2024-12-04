-- 1. Users Table
CREATE TABLE sakila.Users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('Instructor', 'Admin', 'Staff') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. Membership Plans Table
CREATE TABLE sakila.MembershipPlans (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    duration INT NOT NULL, -- Duration in months
    description TEXT
);

-- 3. Members Table
CREATE TABLE sakila.Members (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    contact VARCHAR(15),
    membership_plan_id BIGINT UNSIGNED NOT NULL,
    status VARCHAR(15) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    FOREIGN KEY (membership_plan_id) REFERENCES MembershipPlans(id) ON DELETE CASCADE
);

-- 4. Classes Table
CREATE TABLE sakila.Classes (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    instructor_id BIGINT UNSIGNED,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    capacity INT NOT NULL,
    FOREIGN KEY (instructor_id) REFERENCES Users(id) ON DELETE SET NULL
);

-- 5. Bookings Table
CREATE TABLE sakila.Bookings (
    id SERIAL PRIMARY KEY,
    class_id BIGINT UNSIGNED NOT NULL,
    member_id BIGINT UNSIGNED NOT NULL,
    status ENUM('Booked', 'Cancelled', 'Waitlisted') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (class_id) REFERENCES Classes(id) ON DELETE CASCADE,
    FOREIGN KEY (member_id) REFERENCES Members(id) ON DELETE CASCADE
);