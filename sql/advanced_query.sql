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







