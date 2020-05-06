--create TABLE PUBLIC.host_info
CREATE TABLE PUBLIC.host_info
(
	id			SERIAL PRIMARY KEY,
	hostname		VARCHAR NOT NULL,
	cpu_number		SMALLINT NOT NULL,
	cpu_architecture	VARCHAR NOT NULL,	
	cpu_model		VARCHAR NOT NULL,	
	cpu_mhz			REAL NOT NULL,
	L2_cache		SMALLINT NOT NULL,
	total_mem		REAL NOT NULL,
	"timestamp"		TIMESTAMP default 'now',
	UNIQUE (hostname)
);


--create TABLE PUBLIC.host_usage
CREATE TABLE PUBLIC.host_usage
(
	"timestamp"	TIMESTAMP default 'now',
	host_id		SERIAL NOT NULL,
	memory_free	INTEGER NOT NULL,
	cpu_idel	SMALLINT NOT NULL,
	cpu_kernel	SMALLINT NOT NULL,
	disk_io 	INTEGER NOT NULL,
	dis_available	INTEGER NOT NULL,
	FOREIGN KEY (host_id) REFERENCES host_info(id)
);
