-- SQL BETWEEN 语法
-- BETWEEN 操作符用于选取介于两个值之间的数据范围内的值。
SELECT column1, column2, ...
    FROM table_name
WHERE column BETWEEN value1 AND value2;


-- SQL ROUND() 函数
-- ROUND() 函数
-- ROUND() 函数用于把数值字段舍入为指定的小数位数。

SELECT ROUND(column_name,decimals) FROM TABLE_NAME;

-- SUM() 函数
-- SUM() 函数返回数值列的总数。

SQL SUM() 语法
SELECT SUM(column_name) FROM table_name;


-- 不想用连接就用嵌套
-- 用户表： Users
--
-- +-------------+---------+
-- | Column Name | Type    |
-- +-------------+---------+
-- | user_id     | int     |
-- | user_name   | varchar |
-- +-------------+---------+
-- user_id 是该表的主键(具有唯一值的列)。
-- 该表中的每行包括用户 ID 和用户名。
--
--
-- 注册表： Register
--
-- +-------------+---------+
-- | Column Name | Type    |
-- +-------------+---------+
-- | contest_id  | int     |
-- | user_id     | int     |
-- +-------------+---------+
-- (contest_id, user_id) 是该表的主键(具有唯一值的列的组合)。
-- 该表中的每行包含用户的 ID 和他们注册的赛事。
--
--
-- 编写解决方案统计出各赛事的用户注册百分率，保留两位小数。
--
-- 返回的结果表按 percentage 的 降序 排序，若相同则按 contest_id 的 升序 排序。
--
-- 返回结果如下示例所示。

select contest_id,round(count(user_id) * 100/ (select count(*) from users),2) as percentage
from Register
group by contest_id
order by percentage desc,contest_id



-- AVG() 函数
-- AVG() 函数返回数值列的平均值。
--
-- SQL AVG() 语法
-- SELECT AVG(column_name) FROM table_name

SELECT
    query_name,
    ROUND(AVG(rating/position), 2) quality,
    ROUND(SUM(IF(rating < 3, 1, 0)) * 100 / COUNT(*), 2) poor_query_percentage
FROM Queries
GROUP BY query_name


-- MySQL Date 函数
--
-- 函数	描述
-- NOW()	    返回当前的日期和时间
-- CURDATE()	返回当前的日期
-- CURTIME()	返回当前的时间
-- DATE()	    提取日期或日期/时间表达式的日期部分
-- EXTRACT()	返回日期/时间的单独部分
-- DATE_ADD()	向日期添加指定的时间间隔
-- DATE_SUB()	从日期减去指定的时间间隔
-- DATEDIFF()	返回两个日期之间的天数
-- DATE_FORMAT()	用不同的格式显示日期/时间

SELECT DATE_FORMAT(trans_date, '%Y-%m') AS month,
    country,
    COUNT(*) AS trans_count,
    COUNT(IF(state = 'approved', 1, NULL)) AS approved_count,
    SUM(amount) AS trans_total_amount,
    SUM(IF(state = 'approved', amount, 0)) AS approved_total_amount
FROM Transactions
GROUP BY month, country

-- 配送表: Delivery
--
-- +-----------------------------+---------+
-- | Column Name                 | Type    |
-- +-----------------------------+---------+
-- | delivery_id                 | int     |
-- | customer_id                 | int     |
-- | order_date                  | date    |
-- | customer_pref_delivery_date | date    |
-- +-----------------------------+---------+
-- delivery_id 是该表中具有唯一值的列。
-- 该表保存着顾客的食物配送信息，顾客在某个日期下了订单，并指定了一个期望的配送日期（和下单日期相同或者在那之后）。
--
--
-- 如果顾客期望的配送日期和下单日期相同，则该订单称为 「即时订单」，否则称为「计划订单」。
--
-- 「首次订单」是顾客最早创建的订单。我们保证一个顾客只会有一个「首次订单」。
--
-- 编写解决方案以获取即时订单在所有用户的首次订单中的比例。保留两位小数。


select round(sum(order_date = customer_pref_delivery_date) * 100 / count(*),2) as immediate_percentage
from Delivery
where (customer_id, order_date) in (
    select customer_id,min(order_date)
    from Delivery
    group by customer_id
)

-- 首次订单！为即使订单！的比例！


-- Table: Activity
--
-- +--------------+---------+
-- | Column Name  | Type    |
-- +--------------+---------+
-- | player_id    | int     |
-- | device_id    | int     |
-- | event_date   | date    |
-- | games_played | int     |
-- +--------------+---------+
-- （player_id，event_date）是此表的主键（具有唯一值的列的组合）。
-- 这张表显示了某些游戏的玩家的活动情况。
-- 每一行是一个玩家的记录，他在某一天使用某个设备注销之前登录并玩了很多游戏（可能是 0）。
--
--
-- 编写解决方案，报告在首次登录的第二天再次登录的玩家的 比率，四舍五入到小数点后两位。换句话说，你需要计算从首次登录日期开始至少连续两天登录的玩家的数量，然后除以玩家总数。

select round(avg(a.event_date is not null), 2) fraction
from
    (select player_id, min(event_date) as login
     from activity
     group by player_id) p
        left join activity a
                  on p.player_id=a.player_id and datediff(a.event_date, p.login)=1






