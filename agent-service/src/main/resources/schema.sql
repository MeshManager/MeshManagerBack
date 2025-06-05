CREATE TABLE clusters (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cluster_name VARCHAR(255) NOT NULL,
    prometheus_url VARCHAR(255) NOT NULL,
    token VARCHAR(255) NOT NULL
); 