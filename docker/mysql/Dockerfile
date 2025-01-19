FROM mysql:latest

ENV MYSQL_RANDOM_ROOT_PASSWORD true
COPY initdb.sql /docker-entrypoint-initdb.d/initdb.sql
COPY inituser.sql /docker-entrypoint-initdb.d/inituser.sql
