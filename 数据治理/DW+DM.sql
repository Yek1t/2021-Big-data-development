--订单业务汇总
drop table if exists electronic_commerce.order_summary;
create table electronic_commerce.order_summary
(
  date_month VARCHAR(6),
  city VARCHAR(5),
  order_gender VARCHAR(2),
  order_category INT,
  total_amount INT,
  total_sales_volume INT,
  sort_id INT
);
-- Add comments to the table 
comment on table electronic_commerce.order_summary
  is '订单业务汇总';
-- Add comments to the columns 
comment on column electronic_commerce.order_summary.date_month
  is '月份';
comment on column electronic_commerce.order_summary.city
  is '地市名称';
comment on column electronic_commerce.order_summary.order_gender
  is '性别';
comment on column electronic_commerce.order_summary.order_category
  is '商品类别';
comment on column electronic_commerce.order_summary.total_amount
  is '订单总数';
comment on column electronic_commerce.order_summary.total_sales_volume
  is '订单总额';
comment on column electronic_commerce.order_summary.sort_id
  is '排序';
 
--性别订单汇总
drop table if exists electronic_commerce.gender_summary;
create table electronic_commerce.gender_summary
(
  date_month VARCHAR(6),
  gender VARCHAR(2),
  total_amount INT,
  total_sales_volume INT,
  sort_id INT
);
-- Add comments to the table 
comment on table electronic_commerce.gender_summary
  is '性别订单汇总';
-- Add comments to the columns 
comment on column electronic_commerce.gender_summary.date_month
  is '月份';
comment on column electronic_commerce.gender_summary.gender
  is '性别';
comment on column electronic_commerce.gender_summary.total_amount
  is '订单总数';
 comment on column electronic_commerce.gender_summary.total_sales_volume
  is '订单总额';
 comment on column electronic_commerce.gender_summary.sort_id
  is '排序';
 
 
--商品类型订单汇总
drop table if exists electronic_commerce.category_summary;
create table electronic_commerce.category_summary
(
  date_month VARCHAR(6),
  category_name VARCHAR(10),
  total_amount INT,
  total_sales_volume INT,
  sort_id INT
);
-- Add comments to the table 
comment on table electronic_commerce.category_summary
  is '商品类型订单汇总';
-- Add comments to the columns 
comment on column electronic_commerce.category_summary.date_month
  is '月份';
comment on column electronic_commerce.category_summary.category_name
  is '商品类型名称';
comment on column electronic_commerce.category_summary.total_amount
  is '订单总数';
 comment on column electronic_commerce.category_summary.total_sales_volume
  is '订单总额';
 comment on column electronic_commerce.category_summary.sort_id
  is '排序';
 
--地市订单汇总
drop table if exists electronic_commerce.city_summary;
create table electronic_commerce.city_summary
(
  date_month VARCHAR(6),
  city VARCHAR(5),
  total_amount INT,
  total_sales_volume INT,
  sort_id INT
);
-- Add comments to the table 
comment on table electronic_commerce.city_summary
  is '地市订单汇总';
-- Add comments to the columns 
comment on column electronic_commerce.city_summary.date_month
  is '月份';
comment on column electronic_commerce.city_summary.city
  is '地市名称';
comment on column electronic_commerce.city_summary.total_amount
  is '订单总数';
 comment on column electronic_commerce.city_summary.total_sales_volume
  is '订单总额';
 comment on column electronic_commerce.city_summary.sort_id
  is '排序';
 
--商品类型
drop table if exists electronic_commerce.category;
create table electronic_commerce.category
(
  category_id INT,
  category_name VARCHAR(10),
  sort_id INT
);
-- Add comments to the table 
comment on table electronic_commerce.category
  is '商品类型';
-- Add comments to the columns 
comment on column electronic_commerce.category.category_id
  is '商品类型编码';
comment on column electronic_commerce.category.category_name
  is '商品类型名称';
comment on column electronic_commerce.category.sort_id
  is '排序';
 
 
 --用户汇总
drop table if exists electronic_commerce.user_summary;
create table electronic_commerce.user_summary
(
  date_month VARCHAR(6),
  city VARCHAR(5),
  total_amount INT,
  new_total INT,
  sort_id INT
);
-- Add comments to the table 
comment on table electronic_commerce.user_summary
  is '用户汇总';
-- Add comments to the columns 
comment on column electronic_commerce.user_summary.date_month
  is '月份';
comment on column electronic_commerce.user_summary.city
  is '地市名称';
comment on column electronic_commerce.user_summary.total_amount
  is '用户总数';
comment on column electronic_commerce.user_summary.new_total
  is '当月新增用户总数';
comment on column electronic_commerce.user_summary.sort_id
  is '排序';
 

 --商家汇总
drop table if exists electronic_commerce.seller_summary;
create table electronic_commerce.seller_summary
(
  seller_month VARCHAR(6),
  city VARCHAR(5),
  total_amount INT,
  sort_id INT
);
-- Add comments to the table 
comment on table electronic_commerce.seller_summary
  is '商家汇总';
-- Add comments to the columns 
comment on column electronic_commerce.seller_summary.seller_month
  is '月份';
comment on column electronic_commerce.seller_summary.city
  is '地市名称';
comment on column electronic_commerce.seller_summary.total_amount
  is '商家总数';
comment on column electronic_commerce.seller_summary.sort_id
  is '排序';
 

