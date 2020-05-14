
USE TEST01;

DROP TABLE IF EXISTS TBL_PRODUCT;

CREATE TABLE IF NOT EXISTS TBL_PRODUCT (
	ID INT NOT NULL AUTO_INCREMENT, 
	NAME VARCHAR(50) NOT NULL, 
	STOCK INT NOT NULL, 
	CONSTRAINT pk_TBL_PRODUCT PRIMARY KEY (ID)
);

DROP TABLE IF EXISTS TBL_ORDER;

CREATE TABLE IF NOT EXISTS TBL_ORDER (
	ID INT NOT NULL AUTO_INCREMENT, 
	PRODUCT_NAME VARCHAR(50) NOT NULL, 
	AMOUNT INT NOT NULL, 
	ORDER_DATE_TIME TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, 
	CONSTRAINT pk_TBL_ORDER PRIMARY KEY (ID)
);
