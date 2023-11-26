-- 表: Employees
--
-- +-------------+----------+
-- | Column Name | Type     |
-- +-------------+----------+
-- | employee_id | int      |
-- | name        | varchar  |
-- | manager_id  | int      |
-- | salary      | int      |
-- +-------------+----------+
-- 在 SQL 中，employee_id 是这个表的主键。
-- 这个表包含了员工，他们的薪水和上级经理的id。
-- 有一些员工没有上级经理（其 manager_id 是空值）。
--
--
-- 查找这些员工的id，他们的薪水严格少于$30000 并且他们的上级经理已离职。当一个经理离开公司时，他们的信息需要从员工表中删除掉，但是表中的员工的manager_id  这一列还是设置的离职经理的id 。
--
-- 返回的结果按照employee_id 从小到大排序。

select employee_id
from Employees
where salary < 30000
  and manager_id not in (select employee_id from Employees)
order by employee_id;

-- 换座位
-- 对于所有座位 id 是奇数的学生，修改其 id 为 id+1，如果最后一个座位 id 也是奇数，则最后一个座位 id 不修改。对于所有座位 id 是偶数的学生，修改其 id 为 id-1。
-- 然后使用 CASE 条件和 MOD 函数修改每个学生的座位 id。

-- 表: Seat
--
-- +-------------+---------+
-- | Column Name | Type    |
-- +-------------+---------+
-- | id          | int     |
-- | student     | varchar |
-- +-------------+---------+
-- id 是该表的主键（唯一值）列。
-- 该表的每一行都表示学生的姓名和 ID。
-- id 是一个连续的增量。
--
-- 编写解决方案来交换每两个连续的学生的座位号。如果学生的数量是奇数，则最后一个学生的id不交换。
--
-- 按 id 升序 返回结果表。
--

select (
    case
    when mod(id,2) != 0 and counts != id then id+1
    when mod(id,2) != 0 and counts = id then id
    else id -1
    end
    )  as id, student
from seat,(
    select count(*) as counts
    from seat
) as seat_counts
order by id asc;

-- 电影评分
-- 表：Movies
--
-- +---------------+---------+
-- | Column Name   | Type    |
-- +---------------+---------+
-- | movie_id      | int     |
-- | title         | varchar |
-- +---------------+---------+
-- movie_id 是这个表的主键(具有唯一值的列)。
-- title 是电影的名字。
-- 表：Users
--
-- +---------------+---------+
-- | Column Name   | Type    |
-- +---------------+---------+
-- | user_id       | int     |
-- | name          | varchar |
-- +---------------+---------+
-- user_id 是表的主键(具有唯一值的列)。
-- 表：MovieRating
--
-- +---------------+---------+
-- | Column Name   | Type    |
-- +---------------+---------+
-- | movie_id      | int     |
-- | user_id       | int     |
-- | rating        | int     |
-- | created_at    | date    |
-- +---------------+---------+
-- (movie_id, user_id) 是这个表的主键(具有唯一值的列的组合)。
-- 这个表包含用户在其评论中对电影的评分 rating 。
-- created_at 是用户的点评日期。
--
--
-- 请你编写一个解决方案：
--
-- 查找评论电影数量最多的用户名。如果出现平局，返回字典序较小的用户名。
-- 查找在 February 2020 平均评分最高 的电影名称。如果出现平局，返回字典序较小的电影名称。
-- 字典序 ，即按字母在字典中出现顺序对字符串排序，字典序较小则意味着排序靠前。
--
-- 返回结果格式如下例所示。

-- 思路：
-- 1. 评价电影数量最多的用户；
-- 2. 在2020年2月份平均评分最高的电影。

select u.name
from Users u join MovieRating mr
on u.user_id = mr.user_id
group by 1
order by count(*) desc,1
limit 1;


select title
from Movies m
join MovieRating mr
on m.movie_id = mr.movie_id
and create_at between '2020-02-01' and '2020-02-29'
group by 1
order by avg(rating) desc,1
limit 1;

-- 窗口函数；滑动sum
-- 表: Customer
--
-- +---------------+---------+
-- | Column Name   | Type    |
-- +---------------+---------+
-- | customer_id   | int     |
-- | name          | varchar |
-- | visited_on    | date    |
-- | amount        | int     |
-- +---------------+---------+
-- 在 SQL 中，(customer_id, visited_on) 是该表的主键。
-- 该表包含一家餐馆的顾客交易数据。
-- visited_on 表示 (customer_id) 的顾客在 visited_on 那天访问了餐馆。
-- amount 是一个顾客某一天的消费总额。
--
--
-- 你是餐馆的老板，现在你想分析一下可能的营业额变化增长（每天至少有一位顾客）。
--
-- 计算以 7 天（某日期 + 该日期前的 6 天）为一个时间段的顾客消费平均值。average_amount 要 保留两位小数。
--
-- 结果按 visited_on 升序排序。

-- 解：这里就要介绍窗口函数的移动求和，移动平均了
select distinct visited_on,
                sum_amount as amount,
                round(sum_amount/7,2) as average_amount

-- 以下是解决前六天包括当前的总和（有可能某一天不存在客人）
from (
    select visited_on,
           sum(amount) over (order by visited_on range between interval '6' day preceding and current row) as sum_amount
    -- 以下是计算每天的金额总量
    from (
        select visited_on,
               sum(amount) as amount
        from Customer
        group by visited_on
         ) a
     ) b

-- 最后手动只要覆盖完整7天的数据
where DATEDIFF(visited_on, (select min(visited_on) from Customer)) >= 6;


-- RequestAccepted 表：
--
-- +----------------+---------+
-- | Column Name    | Type    |
-- +----------------+---------+
-- | requester_id   | int     |
-- | accepter_id    | int     |
-- | accept_date    | date    |
-- +----------------+---------+
-- (requester_id, accepter_id) 是这张表的主键(具有唯一值的列的组合)。
-- 这张表包含发送好友请求的人的 ID ，接收好友请求的人的 ID ，以及好友请求通过的日期。
--
--
-- 编写解决方案，找出拥有最多的好友的人和他拥有的好友数目。
--
-- 生成的测试用例保证拥有最多好友数目的只有 1 个人。


select convert( decimal(8, 2), sum(tiv_2016 * 1.0)) tiv_2016
from
    (
        select tiv_2016
             , count(tiv_2015) over(partition by tiv_2015) group15
		, count(pid) over(partition by concat(lat, ',', lon)) groupLoc
        from Insurance
    ) t
where group15 > 1 and t.groupLoc = 1
