/*
INSERT INTO customers (customer_id, customerFName, customerLName, number,email, customer_preferred_sneaker,customer_preferred_brand,customer_preferred_size,customer_status)
VALUES ('001', 'John', 'Doe', '313-321-312', 'email@gmail.com', 'nike air max plus', 'nike', '11','AVAILABLE');

INSERT INTO customers (customer_id, customerFName, customerLName, number,email, customer_preferred_sneaker,customer_preferred_brand,customer_preferred_size,customer_status)
VALUES ('002', 'Zako', 'Ivanov', '313-321-405', 'email@gmail.com', 'adidas gazelles', 'adidas', '12','AVAILABLE');

INSERT INTO customers (customer_id, customerFName, customerLName, number,email, customer_preferred_sneaker,customer_preferred_brand,customer_preferred_size,customer_status)
VALUES ('003', 'Jared', 'Dalson', '313-321-601', 'email@gmail.com', 'boston birkinstock', 'birkinstock', '11','RESTRICTED');

INSERT INTO customers (customer_id, customerFName, customerLName, number,email, customer_preferred_sneaker,customer_preferred_brand,customer_preferred_size,customer_status)
VALUES ('004', 'Stacy', 'Petkova', '313-321-900', 'email@gmail.com', 'nike air force', 'nike', '11','AVAILABLE');

INSERT INTO customers (customer_id, customerFName, customerLName, number,email, customer_preferred_sneaker,customer_preferred_brand,customer_preferred_size,customer_status)
VALUES ('005', 'Hristo', 'Ivanov', '313-321-123', 'email@gmail.com', 'adidas campus', 'adidas', '13','AVAILABLE');

INSERT INTO customers (customer_id, customerFName, customerLName, number,email, customer_preferred_sneaker,customer_preferred_brand,customer_preferred_size,customer_status)
VALUES ('006', 'Ivan', 'Ivanv', '313-321-321', 'email@gmail.com', 'new balance 990v4', 'new balance', '10','RESTRICTED');

INSERT INTO customers (customer_id, customerFName, customerLName, number,email, customer_preferred_sneaker,customer_preferred_brand,customer_preferred_size,customer_status)
VALUES ('007', 'Elvis', 'Ivanov', '313-321-543', 'email@gmail.com', 'timberland', 'timberland', '10','AVAILABLE');

INSERT INTO customers (customer_id,customerFName, customerLName, number,email, customer_preferred_sneaker,customer_preferred_brand,customer_preferred_size,customer_status)
VALUES ('008', 'Jack', 'Gradle', '313-321-786', 'email@gmail.com', 'nike air max 90', 'nike', '8','AVAILABLE');

INSERT INTO customers (customer_id, customerFName, customerLName, number,email, customer_preferred_sneaker,customer_preferred_brand,customer_preferred_size,customer_status)
VALUES ('009', 'John', 'Van', '313-321-988', 'email@gmail.com', 'puma', 'puma', '9','AVAILABLE');

INSERT INTO customers (customer_id, customerFName, customerLName, number,email, customer_preferred_sneaker,customer_preferred_brand,customer_preferred_size,customer_status)
VALUES ('010', 'Georgi', 'Dankov', '313-321-100', 'email@gmail.com', 'nike 270', 'nike', '12','RESTRICTED');

INSERT INTO inventories (inventory_id,available_level, restock_level)
VALUES ('011','3', '10');

INSERT INTO inventories (inventory_id,available_level, restock_level)
VALUES ('012', '5', '10');

INSERT INTO inventories (inventory_id,available_level, restock_level)
VALUES ('013','10', '10');

INSERT INTO inventories (inventory_id,available_level, restock_level)
VALUES ('014','2', '10');

INSERT INTO inventories (inventory_id,available_level, restock_level)
VALUES ('015','1', '4');

INSERT INTO inventories (inventory_id,available_level, restock_level)
VALUES ('016','6', '11');

INSERT INTO inventories (inventory_id,available_level, restock_level)
VALUES ('017','7', '12');

INSERT INTO inventories (inventory_id,available_level, restock_level)
VALUES ('018','8', '15');

INSERT INTO inventories (inventory_id,available_level, restock_level)
VALUES ('019','3', '20');

INSERT INTO inventories (inventory_id,available_level, restock_level)
VALUES ('020','1', '5');

INSERT INTO brands (brand_id, name, associated_celebrity,location_of_main_headquarters,description)
VALUES ('021','Nike','CR7','USA','Since 1950');

INSERT INTO brands (brand_id, name, associated_celebrity,location_of_main_headquarters,description)
VALUES ('022','Adidas','Lionel Messi','Germany','Since 1923');

INSERT INTO brands (brand_id, name, associated_celebrity,location_of_main_headquarters,description)
VALUES ('023','New Balance','Jack Harrow','USA','Since 1944');

INSERT INTO brands (brand_id, name, associated_celebrity,location_of_main_headquarters,description)
VALUES ('024','Puma','Neymar Jr','USA','Since 1954');

INSERT INTO brands (brand_id, name, associated_celebrity,location_of_main_headquarters,description)
VALUES ('025','Birkinstock','Hristo Ivanov','Montreal','Since 1951');

INSERT INTO brands (brand_id, name, associated_celebrity,location_of_main_headquarters,description)
VALUES ('026','Timberland','Hristo Ivanov','USA','Since 1950');

INSERT INTO brands (brand_id, name, associated_celebrity,location_of_main_headquarters,description)
VALUES ('027','Vans','Hristo Ivanov','USA','Since 1960');

INSERT INTO brands (brand_id, name, associated_celebrity,location_of_main_headquarters,description)
VALUES ('028','Lacoste','Hristo Ivanov','Montreal','Since 1959');

INSERT INTO brands (brand_id, name, associated_celebrity,location_of_main_headquarters,description)
VALUES ('029','Jordan','Michal Jordan','USA','Since 1920');

INSERT INTO brands (brand_id, name, associated_celebrity,location_of_main_headquarters,description)
VALUES ('030','Uggs','John Cena','USA','Since 1966');

INSERT INTO brand_founders(brand_id,f_name,l_name,dob,country)
VALUES (1, 'Hristo', 'Ivanov','1900-11-03','Bulgaria');

INSERT INTO brand_founders(brand_id,f_name,l_name,dob,country)
VALUES (2, 'Jackup', 'Presli','1900-11-01','USA');

INSERT INTO brand_founders(brand_id,f_name,l_name,dob,country)
VALUES (3, 'Johny', 'Ivanov','1900-11-03','Congo');

INSERT INTO brand_founders(brand_id,f_name,l_name,dob,country)
VALUES (4, 'Hristo', 'Dankov','2001-12-03','Canada');

INSERT INTO brand_founders(brand_id,f_name,l_name,dob,country)
VALUES (5, 'Jessy', 'Presli','2003-11-03','Germany');

INSERT INTO brand_founders(brand_id,f_name,l_name,dob,country)
VALUES (6, 'Elvis', 'Ivanov','2006-02-11','Rwanda');

INSERT INTO brand_founders(brand_id,f_name,l_name,dob,country)
VALUES (7, 'Zako', 'Ivanov','2004-05-03','Algeria');

INSERT INTO brand_founders(brand_id,f_name,l_name,dob,country)
VALUES (8, 'Ivan', 'Perski','2009-11-03','Brazil');

INSERT INTO brand_founders(brand_id,f_name,l_name,dob,country)
VALUES (9, 'Dante', 'Brasnov','1900-11-03','Bulgaria');

INSERT INTO brand_founders(brand_id,f_name,l_name,dob,country)
VALUES (10, 'Yeanko', 'Yankov','2000-12-03','Canada');

INSERT INTO sneakers (sneaker_id,model,price,size,color,release_year,available_store,description,type)
VALUES ('031','Nike air max plus','250',10,'black','2018','Central','build with high quality materials','sneaker');

INSERT INTO sneakers (sneaker_id,model,price,size,color,release_year,available_store,description,type)
VALUES ('032','Adidas gazelle','180',12,'white','1990','Central,Oak Shop,Adidas','build with high quality materials','boots');

INSERT INTO sneakers (sneaker_id,model,price,size,color,release_year,available_store,description,type)
VALUES ('033','Birkenstock','210',8,'black','2018','Central,Birkinstock','build with high quality materials','boots');

INSERT INTO sneakers (sneaker_id,model,price,size,color,release_year,available_store,description,type)
VALUES ('034','Nike air force','240',9,'white','2010','Central,Oak Shop,Nike','build with high quality materials','boots');

INSERT INTO sneakers (sneaker_id,model,price,size,color,release_year,available_store,description,type)
VALUES ('035','Adidas campus','180',7,'multicolor','2012','Adidas','build with high quality materials','boots');

INSERT INTO sneakers (sneaker_id,model,price,size,color,release_year,available_store,description,type)
VALUES ('036','New Balance990v4','260',10,'black','2005','Central,New Balance','build with high quality materials','boots');

INSERT INTO sneakers (sneaker_id,model,price,size,color,release_year,available_store,description,type)
VALUES ('037','Timberland','200',10,'brown','2005','Central,Timberland','build with high quality materials','boots');

INSERT INTO sneakers (sneaker_id,model,price,size,color,release_year,available_store,description,type)
VALUES ('038','Nike air max 90','190',8,'black','2009','Central,Nike','build with high quality materials','boots');

INSERT INTO sneakers (sneaker_id,model,price,size,color,release_year,available_store,description,type)
VALUES ('039','Puma','120',10,'black and yellow','2010','Puma','build with high quality materials','boots');

INSERT INTO sneakers (sneaker_id,model,price,size,color,release_year,available_store,description,type)
VALUES ('040','Nike 270','100',8,'white and blue','2019','Nike','build with high quality materials','sneaker');

INSERT INTO orders (order_id,inventory_id,customer_id,sneaker_id,order_status,quantity_bought)
VALUES ('041','011','001','031','PAID',5);

INSERT INTO orders (order_id,inventory_id,customer_id,sneaker_id,order_status,quantity_bought)
VALUES ('042','012','002','032','PAID',5);

INSERT INTO orders (order_id,inventory_id,customer_id,sneaker_id,order_status,quantity_bought)
VALUES ('043','013','003','033','PAID',5);

INSERT INTO orders (order_id,inventory_id,customer_id,sneaker_id,order_status,quantity_bought)
VALUES ('044','014','004','034','PAID',5);

INSERT INTO orders (order_id,inventory_id,customer_id,sneaker_id,order_status,quantity_bought)
VALUES ('045','015','005','035','PAID',5);

INSERT INTO orders (order_id,inventory_id,customer_id,sneaker_id,order_status,quantity_bought)
VALUES ('046','016','006','036','PAID',5);

INSERT INTO orders (order_id,inventory_id,customer_id,sneaker_id,order_status,quantity_bought)
VALUES ('047','017','007','037','PAID',5);

INSERT INTO orders (order_id,inventory_id,customer_id,sneaker_id,order_status,quantity_bought)
VALUES ('048','018','008','038','PAID',5);

INSERT INTO orders (order_id,inventory_id,customer_id,sneaker_id,order_status,quantity_bought)
VALUES ('049','019','009','039','PAID',5);

INSERT INTO orders (order_id,inventory_id,customer_id,sneaker_id,order_status,quantity_bought)
VALUES ('050','020','010','040','PAID',5);

INSERT INTO shipping_address(order_id,street,city,state,country,postal_code)
VALUES (1,'1180 RUE DOLLARD','Longueil','QC','CANADA','J4K 4M7');

INSERT INTO shipping_address(order_id,street,city,state,country,postal_code)
VALUES (2,'1180 RUE DOLLARD','Longueil','QC','CANADA','J4K 4M7');

INSERT INTO shipping_address(order_id,street,city,state,country,postal_code)
VALUES (3,'1180 RUE DOLLARD','Longueil','QC','CANADA','J4K 4M7');

INSERT INTO shipping_address(order_id,street,city,state,country,postal_code)
VALUES (4,'1180 RUE DOLLARD','Longueil','QC','CANADA','J4K 4M7');

INSERT INTO shipping_address(order_id,street,city,state,country,postal_code)
VALUES (5,'1180 RUE DOLLARD','Longueil','QC','CANADA','J4K 4M7');

INSERT INTO shipping_address(order_id,street,city,state,country,postal_code)
VALUES (6,'1180 RUE DOLLARD','Longueil','QC','CANADA','J4K 4M7');

INSERT INTO shipping_address(order_id,street,city,state,country,postal_code)
VALUES (7,'1180 RUE DOLLARD','Longueil','QC','CANADA','J4K 4M7');

INSERT INTO shipping_address(order_id,street,city,state,country,postal_code)
VALUES (8,'1180 RUE DOLLARD','Longueil','QC','CANADA','J4K 4M7');

INSERT INTO shipping_address(order_id,street,city,state,country,postal_code)
VALUES (9,'1180 RUE DOLLARD','Longueil','QC','CANADA','J4K 4M7');

INSERT INTO shipping_address(order_id,street,city,state,country,postal_code)
VALUES (10,'1180 RUE DOLLARD','Longueil','QC','CANADA','J4K 4M7');

 */