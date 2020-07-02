#!/bin/sh

export PG_CONF="/var/lib/postgresql/data/pg_hba.conf"

echo "host    hello_service       hello_service        0.0.0.0/0               md5" | tee -a "$PG_CONF"
echo "host    hello_service       hello_service        ::/0                    md5" | tee -a "$PG_CONF"
