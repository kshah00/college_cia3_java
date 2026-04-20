CREATE TABLE IF NOT EXISTS students (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    roll_number TEXT NOT NULL UNIQUE,
    full_name TEXT NOT NULL,
    course TEXT NOT NULL,
    email TEXT
);
