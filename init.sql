-- Create the database
CREATE DATABASE movies_db;

-- Connect to the newly created database
\c movies_db;

CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

INSERT INTO users (email, password, role) VALUES
('admin@example.com', 'password', 'admin'),
('user@example.com', 'password', 'user');

CREATE TABLE IF NOT EXISTS accounts (
    id SERIAL PRIMARY KEY,
    user_id INTEGER UNIQUE NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    preferred_name VARCHAR(255),
    date_of_birth DATE,
    phone VARCHAR(20),
    gender VARCHAR(10),
    photo_url VARCHAR(1024),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE CASCADE
);

INSERT INTO accounts (user_id, first_name, last_name, preferred_name, date_of_birth, phone, gender, photo_url) VALUES
(1, 'Paul', 'McCartney', 'Super Paul', '1990-05-15', '123456789', 'M', 'http://example.com/photo1.jpg'),
(2, 'John', 'Lennon', 'Super John', '1990-05-05', '987654321', 'M', 'http://example.com/photo2.jpg');
--
CREATE TABLE IF NOT EXISTS genres (
    id SERIAL PRIMARY KEY,
    tmdb_id INTEGER NOT NULL,
    name VARCHAR(255) NOT NULL
);

INSERT INTO genres (tmdb_id, name) VALUES
(11, 'Action'),
(22, 'Comedy'),
(33, 'Drama'),
(44, 'Horror');

CREATE TABLE IF NOT EXISTS movies (
    id SERIAL PRIMARY KEY,
    tmdb_id INTEGER NOT NULL,
    name VARCHAR(255) NOT NULL,
    genre_id INTEGER[] NOT NULL,
    release_date DATE NOT NULL,
    rating INTEGER NOT NULL,
    overview VARCHAR(1000),
    original_language VARCHAR(10)
);

INSERT INTO movies (tmdb_id, name, genre_id, release_date, rating, overview, original_language) VALUES
(101, 'Movie 1', ARRAY[1], '2024-01-01', 7, 'Overview 1', 'en'),
(202, 'Movie 2', ARRAY[1,2], '2024-02-15', 8, 'Overview 2', 'fr'),
(303, 'Movie 3', ARRAY[2,3], '2024-03-30', 6, 'Overview 13', 'pl');

CREATE TABLE IF NOT EXISTS account_movie (
    account_id INTEGER NOT NULL,
    movie_id INTEGER NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_user_account
        FOREIGN KEY (account_id)
            REFERENCES accounts (id)
            ON DELETE CASCADE,
    CONSTRAINT fk_movie_user
        FOREIGN KEY (movie_id)
            REFERENCES movies (id)
            ON DELETE CASCADE,
    PRIMARY KEY (account_id, movie_id)
);

INSERT INTO account_movie (account_id, movie_id, status) VALUES (1, 2, 'watched'), (2, 3, 'to_watch');

CREATE TABLE IF NOT EXISTS comments (
    id SERIAL PRIMARY KEY,
    account_id INTEGER NULL,
    movie_id INTEGER NOT NULL,
    note TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE,
    CONSTRAINT fk_movie
        FOREIGN KEY (movie_id)
            REFERENCES movies (id)
            ON DELETE CASCADE
);

INSERT INTO comments (account_id, movie_id, note, is_active) VALUES
(1, 2, 'Comment 1', TRUE),
(2, 3, 'Comment 2', FALSE);