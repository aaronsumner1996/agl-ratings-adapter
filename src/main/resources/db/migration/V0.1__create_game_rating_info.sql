-- -- ==============================================================
-- -- Flyway Migration -
-- -- ==============================================================
-- --
-- -- Creates initial PostgreSQL database tables.
-- --
-- -- ==============================================================
--
-- -- Create tables
--

create table ratings
(
    id varchar(255) not null primary key,
    game_slug varchar(255),
    user_id varchar(255),
    rating DECIMAL
);
