 -- 交叉连接 (cross join)
 -- timestampdiff(时间类型，日期1，日期2);
 -- timestampdiff(day,'2019-01-01','2019-01-02') as x1,
 -- timestampdiff(hour,'2019-01-01 12:00:00','2019-01-02 18:00:00') as y1
 -- timestampdiff(second,'2019-01-01 12:00:00','2019-01-02 18:00:12') as y1

 -- datediff(日期1,日期2)

 --  表： Weather
 -- +---------------+---------+
 -- | Column Name   | Type    |
 -- +---------------+---------+
 -- | id            | int     |
 -- | recordDate    | date    |
 -- | temperature   | int     |
 -- +---------------+---------+
 -- id 是该表具有唯一值的列。
 -- 该表包含特定日期的温度信息
 --
 --
 -- 编写解决方案，找出与之前（昨天的）日期相比温度更高的所有日期的 id 。

 select a.id from Weather a cross join Weather b
     on datediff(a.recordDate,b.recordDate) = 1
 where a.temperature > b.temperature;

-- 自连接、avg、round
-- 表: Activity
--
-- +----------------+---------+
-- | Column Name    | Type    |
-- +----------------+---------+
-- | machine_id     | int     |
-- | process_id     | int     |
-- | activity_type  | enum    |
-- | timestamp      | float   |
-- +----------------+---------+
-- 该表展示了一家工厂网站的用户活动。
-- (machine_id, process_id, activity_type) 是当前表的主键（具有唯一值的列的组合）。
-- machine_id 是一台机器的ID号。
-- process_id 是运行在各机器上的进程ID号。
-- activity_type 是枚举类型 ('start', 'end')。
-- timestamp 是浮点类型,代表当前时间(以秒为单位)。
-- 'start' 代表该进程在这台机器上的开始运行时间戳 , 'end' 代表该进程在这台机器上的终止运行时间戳。
-- 同一台机器，同一个进程都有一对开始时间戳和结束时间戳，而且开始时间戳永远在结束时间戳前面。
--
--
-- 现在有一个工厂网站由几台机器运行，每台机器上运行着 相同数量的进程 。编写解决方案，计算每台机器各自完成一个进程任务的平均耗时。
--
-- 完成一个进程任务的时间指进程的'end' 时间戳 减去 'start' 时间戳。平均耗时通过计算每台机器上所有进程任务的总耗费时间除以机器上的总进程数量获得。
--
-- 结果表必须包含machine_id（机器ID） 和对应的 average time（平均耗时） 别名 processing_time，且四舍五入保留3位小数。
--
-- 以 任意顺序 返回表。

 select a3.machine_id,ROUND(AVG(a3.process_time),3) as processing_time
 from
     (select a1.machine_id, (a2.timestamp - a1.timestamp) as process_time
      from Activity a1 join Activity a2
      where a1.machine_id = a2.machine_id
        and a1.activity_type = 'start'
        and a2.activity_type = 'end'
     ) as a3
 group by a3.machine_id

/**
首先，在内部子查询 a3 中，我们使用自联接 (self-join) 来将同一台机器上相同处理过程的 "start" 和 "end" 活动进行匹配。
这是通过 a1.machine_id = a2.machine_id、a1.process_id = a2.process_id、a1.activity_type = 'start' 和 a2.activity_type = 'end' 条件来完成的。
在子查询中，我们计算了每对匹配的 "start" 和 "end" 活动之间的时间差，这表示了每个处理过程的处理时间，使用 (a2.timestamp - a1.timestamp) 来计算。
子查询 a3 返回了两列数据：machine_id 表示机器的标识符和 process_time 表示处理时间。
现在，外部查询对子查询的结果进行了分组。它使用 GROUP BY a3.machine_id，这意味着我们将结果按照 machine_id 分组，以便计算每台机器的平均处理时间。
在分组后，我们使用 ROUND(AVG(a3.process_time), 3) 来计算每台机器的平均处理时间，其中 AVG(a3.process_time) 计算了每个机器的处理时间的平均值，而 ROUND(..., 3) 用于将结果四舍五入到三位小数。
 */


-- 学生们参加各科测试的次数
-- 学生表: Students
--
-- +---------------+---------+
-- | Column Name   | Type    |
-- +---------------+---------+
-- | student_id    | int     |
-- | student_name  | varchar |
-- +---------------+---------+
-- 在 SQL 中，主键为 student_id（学生ID）。
-- 该表内的每一行都记录有学校一名学生的信息。
--
--
-- 科目表: Subjects
--
-- +--------------+---------+
-- | Column Name  | Type    |
-- +--------------+---------+
-- | subject_name | varchar |
-- +--------------+---------+
-- 在 SQL 中，主键为 subject_name（科目名称）。
-- 每一行记录学校的一门科目名称。
--
--
-- 考试表: Examinations
--
-- +--------------+---------+
-- | Column Name  | Type    |
-- +--------------+---------+
-- | student_id   | int     |
-- | subject_name | varchar |
-- +--------------+---------+
-- 这个表可能包含重复数据（换句话说，在 SQL 中，这个表没有主键）。
-- 学生表里的一个学生修读科目表里的每一门科目。
-- 这张考试表的每一行记录就表示学生表里的某个学生参加了一次科目表里某门科目的测试。
--
--
-- 查询出每个学生参加每一门科目测试的次数，结果按 student_id 和 subject_name 排序。

-- 注意看select 的内容和su这张表存在的必要性
 select a.student_id,a.student_name,su.subject_name,count(b.student_id) as attended_exams
 from Students a
          join Subjects su
          left join Examinations b
                    on a.student_id = b.student_id
                        and b.subject_name = su.subject_name
 group by a.student_id , su.subject_name
 order by a.student_id , su.subject_name

 -- 确认率
 -- 表: Signups
 -- +----------------+----------+
 -- | Column Name    | Type     |
 -- +----------------+----------+
 -- | user_id        | int      |
 -- | time_stamp     | datetime |
 -- +----------------+----------+
 -- User_id是该表的主键。
 -- 每一行都包含ID为user_id的用户的注册时间信息。
 --
 --
 -- 表: Confirmations
 -- +----------------+----------+
 -- | Column Name    | Type     |
 -- +----------------+----------+
 -- | user_id        | int      |
 -- | time_stamp     | datetime |
 -- | action         | ENUM     |
 -- +----------------+----------+
 -- (user_id, time_stamp)是该表的主键。
 -- user_id是一个引用到注册表的外键。
 -- action是类型为('confirmed'， 'timeout')的ENUM
 -- 该表的每一行都表示ID为user_id的用户在time_stamp请求了一条确认消息，该确认消息要么被确认('confirmed')，要么被过期('timeout')。
 --
 --
 -- 用户的 确认率 是 'confirmed' 消息的数量除以请求的确认消息的总数。没有请求任何确认消息的用户的确认率为 0 。确认率四舍五入到 小数点后两位 。
 --
 -- 编写一个SQL查询来查找每个用户的 确认率 。
 --
 -- 以 任意顺序 返回结果表。

 -- 使用聚合函数 avg;使用数值函数 round； 使用流程控制函数 if
 select s.user_id,round(avg(if(c.action = 'confirmed',1,0)),2) as confirmation_rate from Signups s left join Confirmations c
 on s.user_id = c.user_id
 group by s.user_id






