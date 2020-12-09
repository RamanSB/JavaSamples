USE example_jdbc_db;

CREATE TABLE banks (
	id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(80) DEFAULT NULL,
    no_of_employees INT DEFAULT NULL,
    is_public TINYINT DEFAULT 0,
    assets_managed DECIMAL(12, 1) DEFAULT NULL,
    lei VARCHAR(20) DEFAULT NULL,
    avg_salary INTEGER DEFAULT NULL
);

INSERT INTO banks (id, name, no_of_employees, is_public, assets_managed, lei, avg_salary)
VALUES 
('1', 'Bank Ala Test', '25000', '0', '80000001.0', '78E726FD9V9IW9LRYJNM', '85000'),
('2', 'JP Morgan', '256000', '1', '1900000000.0', '549300ZK53CNGEEI6A29', '53070'),
('3', 'Barclays', '83500', '1', '1400000000.0', 'G5GSEF7VJP5I7OUK5573', '30800'),
('4', 'Investec', '8700', '1', '1280000000.0', '84S0VF8TSMH0T6D4K848', '47000');



-- **select query used in jdbc example**
-- SELECT b.name, b.lei FROM banks b--
-- WHERE b.no_of_employees > 25000;--

-- **insert query used in jdbc example (executeUpdate)
-- INSERT INTO banks (name, no_of_employees, is_public, assets_managed, lei, avg_salary)--
-- VALUES ('NewBank', 200, 0, 50000000.50, '81V0FA7ASNKASD74A8D', 80000);--

