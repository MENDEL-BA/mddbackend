CREATE TABLE IF NOT EXISTS users(
    id INT AUTO_INCREMENT PRIMARY KEY,
    admin bit not null,
    created_at VARCHAR(50),
    email      VARCHAR(50) UNIQUE NOT NULL,
    first_name VARCHAR(20)  not null,
    password   VARCHAR(120),
    updated_at VARCHAR(6)
);

CREATE TABLE IF NOT EXISTS subjects
(
    id  INT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(500),
    title       VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS subscriptions
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    subject_id bigint not null,
    user_id    bigint not null,
    primary key (id, user_id, subject_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (subject_id) REFERENCES subjects (id)
);

CREATE TABLE IF NOT EXISTS posts
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    created_at  VARCHAR(6),
    description VARCHAR(5000),
    title       VARCHAR(200) ,
    updated_at  VARCHAR(6),
    author_id   bigint not null,
    subject_id  bigint not null,
    FOREIGN KEY (author_id) REFERENCES users (id),
    FOREIGN KEY (subject_id) REFERENCES subjects (id)
);

CREATE TABLE IF NOT EXISTS messages
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    created_at VARCHAR(6)  not null,
    message    VARCHAR(500),
    author_id  bigint  not  null,
    post_id    bigint    not   null,
    FOREIGN KEY (post_id) REFERENCES posts (id),
    FOREIGN KEY (author_id) REFERENCES users (id)
);