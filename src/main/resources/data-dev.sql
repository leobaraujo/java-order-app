/* CUSTOMER TABLE */
INSERT INTO customer (id, number, status) VALUES ('a27f18d7-97d2-4063-b5e5-7f0e57fd7094', 1, 'BUSY');

/* PRODUCT TABLE */
INSERT INTO product (id, name, buy_price, sell_price, img_url, category) VALUES ('49677434-1020-43d1-b245-aa20e070156a', 'Bebida A', 5.0, 10.0, 'https://img.freepik.com/vetores-gratis/copo-de-agua-isolado_1368-2666.jpg', 'CERVEJA');

/* ORDER TABLE */
INSERT INTO order_table (id, customer_id, product_id, quantity, is_active) VALUES ('56245543-6b4e-44b9-a213-df1396a975df', 'a27f18d7-97d2-4063-b5e5-7f0e57fd7094', '49677434-1020-43d1-b245-aa20e070156a', 2, 1)
