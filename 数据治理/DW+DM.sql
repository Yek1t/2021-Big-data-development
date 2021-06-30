--����ҵ�����
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
  is '����ҵ�����';
-- Add comments to the columns 
comment on column electronic_commerce.order_summary.date_month
  is '�·�';
comment on column electronic_commerce.order_summary.city
  is '��������';
comment on column electronic_commerce.order_summary.order_gender
  is '�Ա�';
comment on column electronic_commerce.order_summary.order_category
  is '��Ʒ���';
comment on column electronic_commerce.order_summary.total_amount
  is '��������';
comment on column electronic_commerce.order_summary.total_sales_volume
  is '�����ܶ�';
comment on column electronic_commerce.order_summary.sort_id
  is '����';
 
--�Ա𶩵�����
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
  is '�Ա𶩵�����';
-- Add comments to the columns 
comment on column electronic_commerce.gender_summary.date_month
  is '�·�';
comment on column electronic_commerce.gender_summary.gender
  is '�Ա�';
comment on column electronic_commerce.gender_summary.total_amount
  is '��������';
 comment on column electronic_commerce.gender_summary.total_sales_volume
  is '�����ܶ�';
 comment on column electronic_commerce.gender_summary.sort_id
  is '����';
 
 
--��Ʒ���Ͷ�������
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
  is '��Ʒ���Ͷ�������';
-- Add comments to the columns 
comment on column electronic_commerce.category_summary.date_month
  is '�·�';
comment on column electronic_commerce.category_summary.category_name
  is '��Ʒ��������';
comment on column electronic_commerce.category_summary.total_amount
  is '��������';
 comment on column electronic_commerce.category_summary.total_sales_volume
  is '�����ܶ�';
 comment on column electronic_commerce.category_summary.sort_id
  is '����';
 
--���ж�������
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
  is '���ж�������';
-- Add comments to the columns 
comment on column electronic_commerce.city_summary.date_month
  is '�·�';
comment on column electronic_commerce.city_summary.city
  is '��������';
comment on column electronic_commerce.city_summary.total_amount
  is '��������';
 comment on column electronic_commerce.city_summary.total_sales_volume
  is '�����ܶ�';
 comment on column electronic_commerce.city_summary.sort_id
  is '����';
 
--��Ʒ����
drop table if exists electronic_commerce.category;
create table electronic_commerce.category
(
  category_id INT,
  category_name VARCHAR(10),
  sort_id INT
);
-- Add comments to the table 
comment on table electronic_commerce.category
  is '��Ʒ����';
-- Add comments to the columns 
comment on column electronic_commerce.category.category_id
  is '��Ʒ���ͱ���';
comment on column electronic_commerce.category.category_name
  is '��Ʒ��������';
comment on column electronic_commerce.category.sort_id
  is '����';
 
 
 --�û�����
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
  is '�û�����';
-- Add comments to the columns 
comment on column electronic_commerce.user_summary.date_month
  is '�·�';
comment on column electronic_commerce.user_summary.city
  is '��������';
comment on column electronic_commerce.user_summary.total_amount
  is '�û�����';
comment on column electronic_commerce.user_summary.new_total
  is '���������û�����';
comment on column electronic_commerce.user_summary.sort_id
  is '����';
 

 --�̼һ���
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
  is '�̼һ���';
-- Add comments to the columns 
comment on column electronic_commerce.seller_summary.seller_month
  is '�·�';
comment on column electronic_commerce.seller_summary.city
  is '��������';
comment on column electronic_commerce.seller_summary.total_amount
  is '�̼�����';
comment on column electronic_commerce.seller_summary.sort_id
  is '����';
 

