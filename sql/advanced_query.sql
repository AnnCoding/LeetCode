-- 自链接 + 平均年龄计算

-- Table: Employees
--
-- +-------------+----------+
-- | Column Name | Type     |
-- +-------------+----------+
-- | employee_id | int      |
-- | name        | varchar  |
-- | reports_to  | int      |
-- | age         | int      |
-- +-------------+----------+
-- employee_id 是这个表的主键.
-- 该表包含员工以及需要听取他们汇报的上级经理的ID的信息。 有些员工不需要向任何人汇报（reports_to 为空）。
--
--
-- 对于此问题，我们将至少有一个其他员工需要向他汇报的员工，视为一个经理。
--
-- 编写SQL查询需要听取汇报的所有经理的ID、名称、直接向该经理汇报的员工人数，以及这些员工的平均年龄，其中该平均年龄需要四舍五入到最接近的整数。
--
-- 返回的结果集需要按照 employee_id 进行排序。

select m.employee_id,
       m.name,
       count(*) as reports_count,
       round(avg(e.age),0) as average_age -- 平均年龄计算


from Employees e
         join Employees m
              on e.reports_to = m.employee_id -- 自链接

group by m.employee_id
order by employee_id;


-- uinon 与 union all
-- union 用于将多个 select 语句的结果组合到一个结果集中，该结果集包括属于 union 中 select 语句的所有行。
--
-- select ...
-- union / union all
-- select ...
-- union / union all
-- ...
-- 使用 union 应注意：
--
-- 两个查询中列的数量必须相同。
-- 相应列的数据类型必须相同或兼容。
-- union 运算符删除「重复」行，而 union all 运算符在最终结果集中包含重复行。
--

select employee_id,department_id
from Employee
where primary_flag = 'Y'
union
select employee_id,department_id
from Employee
where employee_id in(
    select employee_id
    from Employee
    group by employee_id
    having count(*) = 1
)


-- case...when
-- if

select employee_id,department_id
from Employee
where primary_flag = 'Y'
union
select employee_id,department_id
from Employee
where employee_id in(
    select employee_id
    from Employee
    group by employee_id
    having count(*) = 1
)

-- 窗口函数lag, lead, row_number

select
    distinct t.num as ConsecutiveNums
from
    (
        select
            num,
            lag(num, 1) over(order by id) as num1,
                lag(num, 2) over(order by id) as num2
        from Logs
    ) t
where t.num = t.num1 and t.num1 = t.num2
;


select
    distinct t.num as ConsecutiveNums
from
    (
        select
            num,
            lag(num, 1, null) over(order by id) as lag_num,
                lead(num, 1, null) over(order by id) as lead_num
        from Logs
    ) t
where t.num = t.lag_num and t.num = t.lead_num
;

select
    distinct t.num as ConsecutiveNums
from
    (
        select
            id,
            num,
            row_number() over(order by id) as rn,
                row_number() over(partition by num order by id) as id_rn
        from Logs
    ) t
group by t.num, (t.rn - t.id_rn)
having count(1) >= 3
;


-- 产品数据表: Products
--
-- +---------------+---------+
-- | Column Name   | Type    |
-- +---------------+---------+
-- | product_id    | int     |
-- | new_price     | int     |
-- | change_date   | date    |
-- +---------------+---------+
-- (product_id, change_date) 是此表的主键（具有唯一值的列组合）。
-- 这张表的每一行分别记录了 某产品 在某个日期 更改后 的新价格。
--
--
-- 编写一个解决方案，找出在 2019-08-16 时全部产品的价格，假设所有产品在修改前的价格都是 10 。

-- ifnull 和 left join 的使用

select
    p1.product_id,ifnull(p2.new_price,10) as price
from (
         select  distinct product_id
         from products
     ) as p1 -- 所有商品

         left join (
    select product_id,new_price
    from products
    where (product_id,change_date) in (
        select product_id, max(change_date)
        from products
        where change_date <= '2019-08-16'
        group by product_id
    )
) as p2 -- 在2019-08-16 之前有修改过的产品和最新的价格
on p1.product_id = p2.product_id;

-- 表: Queue
--
-- +-------------+---------+
-- | Column Name | Type    |
-- +-------------+---------+
-- | person_id   | int     |
-- | person_name | varchar |
-- | weight      | int     |
-- | turn        | int     |
-- +-------------+---------+
-- person_id 是这个表具有唯一值的列。
-- 该表展示了所有候车乘客的信息。
-- 表中 person_id 和 turn 列将包含从 1 到 n 的所有数字，其中 n 是表中的行数。
-- turn 决定了候车乘客上巴士的顺序，其中 turn=1 表示第一个上巴士，turn=n 表示最后一个上巴士。
-- weight 表示候车乘客的体重，以千克为单位。
--
--
-- 有一队乘客在等着上巴士。然而，巴士有1000  千克 的重量限制，所以其中一部分乘客可能无法上巴士。

-- 自连接！！！
-- 将 b 表中的每一条数据和 a 表的每一条数据连接。假设 Queue 表有 3 条记录，那么自连接后将会有 9 条数据，
-- 分别为 (a1 b1),(a1 b2),(a1 b3),(a2 b1),(a2 b2),(a2 b3),(a3 b1),(a3 b2),(a3 b3) 。

select
    a.person_name
from Queue a, Queue b
where a.turn >= b.turn
group by a.person_id having sum(b.weight) <= 1000
order by a.turn desc
    limit 1;



-- union联合查询
-- 表: Accounts
--
-- +-------------+------+
-- | 列名        | 类型  |
-- +-------------+------+
-- | account_id  | int  |
-- | income      | int  |
-- +-------------+------+
-- 在 SQL 中，account_id 是这个表的主键。
-- 每一行都包含一个银行帐户的月收入的信息。
--
--
-- 查询每个工资类别的银行账户数量。 工资类别如下：
--
-- "Low Salary"：所有工资 严格低于 20000 美元。
-- "Average Salary"： 包含 范围内的所有工资 [$20000, $50000] 。
-- "High Salary"：所有工资 严格大于 50000 美元。
--
-- 结果表 必须 包含所有三个类别。 如果某个类别中没有帐户，则报告 0 。
--
-- 按 任意顺序 返回结果表。


select 'Low Salary' as category, count(*) as accounts_count
from accounts
where income < 20000

union

select 'Average Salary' as category, count(*) as accounts_count
from accounts
where income <= 50000 and income >= 20000

union

select 'High Salary' as category, count(*) as accounts_count
from accounts
where income > 50000;











