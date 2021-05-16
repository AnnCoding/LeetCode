

-- 查询第二高的薪水
-- 正确✅
SELECT max(Salary) SecondHighestSalary
 FROM Employee
where Salary != (select max(Salary) from Employee );
-- 错误❎  输不出null
select distinct salary as SecondHighestSalary from Employee order by salary desc limit 1,1;
-- 修正能够输出null的
select IFNULL(
(select distinct(Salary)from Employee order by Salary desclimit 1,1),null) as SecondHighestSalary;

-- 获取employee 表中第N高的薪水
CREATE FUNCTION getNthHighestSalary(N INT) RETURNS INT
BEGIN
  SET N = N-1;
  IF N < 0 THEN
  RETURN NULL;
  ELSE
  RETURN (
      # Write your MySQL query statement below.
      SELECT IFNULL(
          (
              SELECT
              DISTINCT Salary
              FROM Employee
              ORDER BY Salary desc
              LIMIT N,1
          ),NULL
      ) AS getNthHighestSalary
  );
  END IF;
END
