--���²鿴�������۶�
select date_month, sum(total_sales_volume)
from electronic_commerce.order_summary
group by date_month;

--���²鿴����ע������
select date_month, new_total 
from electronic_commerce.user_summary
order by date_month;

--���²鿴���е����۶�
select date_month, city, total_sales_volume
from electronic_commerce.city_summary;

--���²鿴���С���Ʒ�������۶�
select date_month, city, category_name, total_sales_volume
from electronic_commerce.city_summary and electronic_commerce.category_summary
where electronic_commerce.city_summary.date_month=electronic_commerce.category_summary.date_month;

--���²鿴�Ա���Ʒ�������۶�
select date_month, gender, category_name, total_sales_volume
from electronic_commerce.gender_summary and electronic_commerce.category_summary
where electronic_commerce.gender_summary.date_month=electronic_commerce.category_summary.date_month;