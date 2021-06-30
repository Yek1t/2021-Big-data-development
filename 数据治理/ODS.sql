--create schema electronic_commerce 
--�û���Ϣ��
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
  is '�û���Ϣ��';
-- Add comments to the columns 
comment on column electronic_commerce.user_information.user_id
  is '�û�id';
comment on column electronic_commerce.user_information.user_name
  is '�û���';
comment on column electronic_commerce.user_information.user_email
  is '�����ַ';
comment on column electronic_commerce.user_information.user_age
  is '�û�����';
comment on column electronic_commerce.user_information.user_gender
  is '�û��Ա�';
comment on column electronic_commerce.user_information.user_phonenumber
  is '�û��ֻ���';
comment on column electronic_commerce.user_information.register_time
  is 'ע��ʱ��';
 
--�û���¼��
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
  is '�û���¼��';
-- Add comments to the columns 
comment on column electronic_commerce.user_login.user_id
  is '�û�id';
 comment on column electronic_commerce.user_login.login_name
  is '��¼��';
comment on column electronic_commerce.user_login.user_password
  is '�û�����';
comment on column electronic_commerce.user_login.login_status
  is '��¼״̬';
comment on column electronic_commerce.user_login.login_time
  is '��¼ʱ��';
 
--�̼���Ϣ��
drop table if exists electronic_commerce.seller_information CASCADE;
create table electronic_commerce.seller_information
(
  seller_id INT PRIMARY key unique,
  seller_name VARCHAR(10)
);
-- Add comments to the table 
comment on table electronic_commerce.seller_information
  is '�̼���Ϣ��';
-- Add comments to the columns 
comment on column electronic_commerce.seller_information.seller_id
  is '�̼�id';
comment on column electronic_commerce.seller_information.seller_name
  is '�̼�����';
 
 --�̼���Ʒ��Ϣ��
drop table if exists electronic_commerce.seller_product CASCADE;
create table electronic_commerce.seller_product
(
  seller_id INT references electronic_commerce.seller_information(seller_id),
  product_id int references electronic_commerce.product_information(product_id)
);
-- Add comments to the table 
comment on table electronic_commerce.seller_product
  is '�̼���Ʒ��Ϣ��';
-- Add comments to the columns 
comment on column electronic_commerce.seller_product.seller_id
  is '�̼�id';
comment on column electronic_commerce.seller_product.product_id
  is '��Ʒid';

 
--��Ʒ��Ϣ��
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
  is '��Ʒ��Ϣ��';
-- Add comments to the columns 
comment on column electronic_commerce.product_information.product_id
  is '��Ʒid';
comment on column electronic_commerce.product_information.product_name
  is '��Ʒ����';
comment on column electronic_commerce.product_information.product_price
  is '��Ʒ�۸�';
comment on column electronic_commerce.product_information.product_quantity
  is '��Ʒ����';
comment on column electronic_commerce.product_information.product_category
  is '��Ʒ������';

 --������
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
  is '������Ϣ��';
-- Add comments to the columns 
comment on column electronic_commerce.order_information.order_id
  is '����id';
comment on column electronic_commerce.order_information.user_id
  is '�û�id';
comment on column electronic_commerce.order_information.product_id
  is '��Ʒid';
comment on column electronic_commerce.order_information.product_amount
  is '��Ʒ����';
comment on column electronic_commerce.order_information.consignee_name
  is '�ջ�����';
comment on column electronic_commerce.order_information.consignee_phonenumber
  is '�ֻ���';
comment on column electronic_commerce.order_information.province
  is 'ʡ��';
comment on column electronic_commerce.order_information.city
  is '����';
comment on column electronic_commerce.order_information.address
  is '��ϸ��ַ';
comment on column electronic_commerce.order_information.order_money
  is '�������';
comment on column electronic_commerce.order_information.create_time
  is '����ʱ��';
comment on column electronic_commerce.order_information.order_status
  is '����״̬';
