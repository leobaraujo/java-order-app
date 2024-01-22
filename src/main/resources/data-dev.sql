/* CUSTOMER TABLE */
INSERT INTO customer (id, number, status) VALUES ('54fb41b4-919d-44e3-b53b-fe475adfe127', 1, 'AVAILABLE');
INSERT INTO customer (id, number, status) VALUES ('a27f18d7-97d2-4063-b5e5-7f0e57fd7094', 2, 'BUSY');
INSERT INTO customer (id, number, status) VALUES ('92c95fad-1c89-44f5-862a-08e2586dd976', 3, 'MAINTENANCE');

/* PRODUCT TABLE */
INSERT INTO product (id, name, buy_price, sell_price, img_url, category) VALUES ('49677434-1020-43d1-b245-aa20e070156a', 'Bebida A', 5.0, 10.0, 'https://img.freepik.com/vetores-gratis/copo-de-agua-isolado_1368-2666.jpg', 'CERVEJA');

/* ORDER TABLE */
INSERT INTO order_table (id, customer_id, product_id, quantity, is_active) VALUES ('56245543-6b4e-44b9-a213-df1396a975df', 'a27f18d7-97d2-4063-b5e5-7f0e57fd7094', '49677434-1020-43d1-b245-aa20e070156a', 2, 1);

/* PARTIAL PAYMENT TABLE */
INSERT INTO partial_payment (id, customer_id, amount, note) VALUES ('bbbd6289-7c73-4b67-92a4-e80dbebfad71', 'a27f18d7-97d2-4063-b5e5-7f0e57fd7094', 50.0, 'John Doe');