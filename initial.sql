CREATE TABLE IF NOT EXISTS books
(
    id     SERIAL PRIMARY KEY,
    title  VARCHAR(255),
    author VARCHAR(255),
    year   INTEGER
);

CREATE TABLE IF NOT EXISTS book_info
(
    id           SERIAL PRIMARY KEY,
    is_available BOOLEAN DEFAULT TRUE,
    is_updated   BOOLEAN DEFAULT FALSE,
    genre        VARCHAR(255),
    book_id      INT NOT NULL,
    FOREIGN KEY (book_id) REFERENCES books (id) ON DELETE CASCADE
)