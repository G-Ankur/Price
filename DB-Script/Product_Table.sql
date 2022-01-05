CREATE TABLE product
(
    productid    INT AUTO_INCREMENT NOT NULL,
    currenttime  datetime NULL,
    responsetime BIGINT NULL,
    userid       VARCHAR(255) NULL,
    productcode  VARCHAR(255) NULL,
    price        INT NULL,
    source       VARCHAR(255) NULL,
    CONSTRAINT pk_product PRIMARY KEY (productid)
);