USE `elemental_concept_technical_assessment`;

CREATE USER 'loguser'@'%' IDENTIFIED BY 'logpw';
GRANT SELECT, INSERT ON `log` TO 'loguser'@'%';
FLUSH PRIVILEGES;
