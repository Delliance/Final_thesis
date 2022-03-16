FROM postgres
ENV POSTGRES_PASSWORD 12345
ENV POSTGRES_DB final
# COPY world.sql /docker-entrypoint-initdb.d/