--create schema electronic_commerce 
--用户信息表
drop table if exists electronic_commerce.user_information CASCADE;
create table electronic_commerce.user_information
(
  user_id INT PRIMARY KEY UNIQUE,
  user_name VARCHAR(10),
  user_email  VARCHAR(20),
  user_age INT,
  user_gender VARCHAR(2),
  user_phonenumber VARCHAR(11),
  register_time TIMESTAMP
);
-- Add comments to the table 
comment on table electronic_commerce.user_information
  is '用户信息表';
-- Add comments to the columns 
comment on column electronic_commerce.user_information.user_id
  is '用户id';
comment on column electronic_commerce.user_information.user_name
  is '用户名';
comment on column electronic_commerce.user_information.user_email
  is '邮箱地址';
comment on column electronic_commerce.user_information.user_age
  is '用户年龄';
comment on column electronic_commerce.user_information.user_gender
  is '用户性别';
comment on column electronic_commerce.user_information.user_phonenumber
  is '用户手机号';
comment on column electronic_commerce.user_information.register_time
  is '注册时间';
 
--用户登录表
drop table if exists electronic_commerce.user_login CASCADE;
create table electronic_commerce.user_login
(
  user_id INT references electronic_commerce.user_information(user_id),
  login_name VARCHAR(20),
  user_password VARCHAR(20),
  login_status BOOLEAN,
  login_time TIMESTAMP
);
-- Add comments to the table 
comment on table electronic_commerce.user_login 
  is '用户登录表';
-- Add comments to the columns 
comment on column electronic_commerce.user_login.user_id
  is '用户id';
 comment on column electronic_commerce.user_login.login_name
  is '登录名';
comment on column electronic_commerce.user_login.user_password
  is '用户密码';
comment on column electronic_commerce.user_login.login_status
  is '登录状态';
comment on column electronic_commerce.user_login.login_time
  is '登录时间';
 
--商家信息表
drop table if exists electronic_commerce.seller_information CASCADE;
create table electronic_commerce.seller_information
(
  seller_id INT PRIMARY key unique,
  seller_name VARCHAR(10)
);
-- Add comments to the table 
comment on table electronic_commerce.seller_information
  is '商家信息表';
-- Add comments to the columns 
comment on column electronic_commerce.seller_information.seller_id
  is '商家id';
comment on column electronic_commerce.seller_information.seller_name
  is '商家名称';
 
 --商家商品信息表
drop table if exists electronic_commerce.seller_product CASCADE;
create table electronic_commerce.seller_product
(
  seller_id INT references electronic_commerce.seller_information(seller_id),
  product_id int references electronic_commerce.product_information(product_id)
);
-- Add comments to the table 
comment on table electronic_commerce.seller_product
  is '商家商品信息表';
-- Add comments to the columns 
comment on column electronic_commerce.seller_product.seller_id
  is '商家id';
comment on column electronic_commerce.seller_product.product_id
  is '商品id';

 
--商品信息表
drop table if exists electronic_commerce.product_information CASCADE;
create table electronic_commerce.product_information
(
  product_id INT PRIMARY key unique,
  product_name VARCHAR(10),
  product_price INT,
  product_quantity INT,
  product_category INT
);
-- Add comments to the table 
comment on table electronic_commerce.product_information
  is '商品信息表';
-- Add comments to the columns 
comment on column electronic_commerce.product_information.product_id
  is '商品id';
comment on column electronic_commerce.product_information.product_name
  is '商品名称';
comment on column electronic_commerce.product_information.product_price
  is '商品价格';
comment on column electronic_commerce.product_information.product_quantity
  is '商品数量';
comment on column electronic_commerce.product_information.product_category
  is '商品类别编码';

 --订单表
drop table if exists electronic_commerce.order_information CASCADE;
create table electronic_commerce.order_information
(
	order_id INT primary key unique,
	user_id INT references electronic_commerce.user_information(user_id),
	product_id INT references electronic_commerce.product_information(product_id),
	product_amount INT,
	consignee_phonenumber VARCHAR(11),
	consignee_name VARCHAR(10),
	province VARCHAR(5),
	city VARCHAR(5),
	address VARCHAR(20),
	order_money INT,
	create_time TIMESTAMP,
	order_status BOOLEAN
);
-- Add comments to the table 
comment on table electronic_commerce.order_information
  is '订单信息表';
-- Add comments to the columns 
comment on column electronic_commerce.order_information.order_id
  is '订单id';
comment on column electronic_commerce.order_information.user_id
  is '用户id';
comment on column electronic_commerce.order_information.product_id
  is '商品id';
comment on column electronic_commerce.order_information.product_amount
  is '商品数量';
comment on column electronic_commerce.order_information.consignee_name
  is '收货人名';
comment on column electronic_commerce.order_information.consignee_phonenumber
  is '手机号';
comment on column electronic_commerce.order_information.province
  is '省份';
comment on column electronic_commerce.order_information.city
  is '城市';
comment on column electronic_commerce.order_information.address
  is '详细地址';
comment on column electronic_commerce.order_information.order_money
  is '订单金额';
comment on column electronic_commerce.order_information.create_time
  is '创建时间';
comment on column electronic_commerce.order_information.order_status
  is '订单状态';
