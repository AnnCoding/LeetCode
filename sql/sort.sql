-- DATE_ADD()函数是MySQL中，用于指定日期添加时间间隔的函数。
-- 它的语法如下: DATE_ADD(date, INTERVAL expr unit) 其中,
-- date是需要增加（减少）时间的日期，
-- expr是需要增加（减少）的时间数量，
-- unit是需要增加（减少）的时间单位（如天、小时、分钟等）。


-- 题目：表：Activity
--
-- +---------------+---------+
-- | Column Name   | Type    |
-- +---------------+---------+
-- | user_id       | int     |
-- | session_id    | int     |
-- | activity_date | date    |
-- | activity_type | enum    |
-- +---------------+---------+
-- 该表没有包含重复数据。
-- activity_type 列是 ENUM(category) 类型， 从 ('open_session'， 'end_session'， 'scroll_down'， 'send_message') 取值。
-- 该表记录社交媒体网站的用户活动。
-- 注意，每个会话只属于一个用户。
--
--
-- 编写解决方案，统计截至 2019-07-27（包含2019-07-27），近 30 天的每日活跃用户数（当天只要有一条活动记录，即为活跃用户）。
--
-- 以 任意顺序 返回结果表

select
    activity_date as day,
    count(distinct user_id) as active_users
from
    Activity
where
    activity_date between DATE_ADD('2019-07-27',interval -29 day) and '2019-07-27'
group by
    activity_date;


-- 仅第一季度的写法！！！

SELECT
    s.product_id, p.product_name
FROM
    sales s
        LEFT JOIN
    product p
    ON
            s.product_id = p.product_id
GROUP BY
    s.product_id
HAVING MIN(sale_date) >= '2019-01-01'     --【重点】
   AND MAX(sale_date) <= '2019-03-31';    --【重点】


-- Customer 表：
--
-- +-------------+---------+
-- | Column Name | Type    |
-- +-------------+---------+
-- | customer_id | int     |
-- | product_key | int     |
-- +-------------+---------+
-- 该表可能包含重复的行。
-- customer_id 不为 NULL。
-- product_key 是 Product 表的外键(reference 列)。
-- Product 表：
--
-- +-------------+---------+
-- | Column Name | Type    |
-- +-------------+---------+
-- | product_key | int     |
-- +-------------+---------+
-- product_key 是这张表的主键（具有唯一值的列）。
--
--
-- 编写解决方案，报告 Customer 表中购买了 Product 表中所有产品的客户的 id。
--
-- 返回结果表 无顺序要求 。

select customer_id from customer group by customer_id
having count(distinct product_key) =(
    select count(distinct product_key) from product -- 这就是product 表存在的意义了？？
)
