--按月查看总体销售额
select date_month, sum(total_sales_volume)
from electronic_commerce.order_summary
group by date_month;

--按月查看新增注册人数
select date_month, new_total 
from electronic_commerce.user_summary
order by date_month;

--按月查看城市的销售额
select date_month, city, total_sales_volume
from electronic_commerce.city_summary;

--按月查看城市、商品类别的销售额
select date_month, city, category_name, total_sales_volume
from electronic_commerce.city_summary and electronic_commerce.category_summary
where electronic_commerce.city_summary.date_month=electronic_commerce.category_summary.date_month;

--按月查看性别、商品类别的销售额
select date_month, gender, category_name, total_sales_volume
from electronic_commerce.gender_summary and electronic_commerce.category_summary
where electronic_commerce.gender_summary.date_month=electronic_commerce.category_summary.date_month;