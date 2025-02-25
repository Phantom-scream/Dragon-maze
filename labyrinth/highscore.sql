-- Create the highscore database
CREATE DATABASE highscore;

-- Use the correct database
USE highscore;

-- Create the leaderboard table
CREATE TABLE leaderboard (
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    name VARCHAR(255),
    solved_labyrinths INT
);

-- Display tables and describe the leaderboard table
SHOW TABLES;
DESCRIBE leaderboard;

-- Check the data
select * from leaderboard;
