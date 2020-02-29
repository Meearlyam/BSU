-- 1. Выдать информацию о местоположении отдела продаж (SALES) компании.
SELECT DEPTADDR 
    FROM DEPT 
    WHERE DEPTNAME = 'SALES';

-- 2. Выдать информацию об отделах, расположенных в Chicago и New York.  
SELECT * 
    FROM DEPT 
    WHERE DEPTADDR IN ('NEW YORK', 'CHICAGO');
    
--  3. Найти минимальную заработную плату, начисленную в 2009 году.
SELECT MIN(SALVALUE) 
    FROM SALARY 
    WHERE YEAR = 2009;
    
--  4. Выдать информацию обо всех работниках, родившихся не позднее 1 января 1960 года.
SELECT *
    FROM EMP 
    WHERE BIRTHDATE <= to_date('01.01.1960','dd.mm.yyyy');
    
--  5. Подсчитать число работников, сведения о которых имеются в базе данных .
SELECT COUNT(*) 
    FROM EMP;
    
--  6. Найти работников, чьё имя состоит из одного слова. Имена выдать на нижнем регистре, с удалением стоящей справа буквы t.
SELECT REGEXP_REPLACE(LOWER(EMPNAME), '(^.*)t$', '\1')
    FROM EMP 
    WHERE EMPNAME NOT LIKE('% %');
    
--  7. Выдать информацию о работниках, указав дату рождения в формате день(число), месяц(название), год(название). 
SELECT EMPNO, EMPNAME, TO_CHAR(BIRTHDATE, 'DD-MONTH-YEAR') 
    FROM EMP

--  То же, но год числом.
SELECT EMPNO, EMPNAME, TO_CHAR(BIRTHDATE, 'DD-MONTH-YYYY') 
    FROM EMP
 
--  8. Выдать информацию о должностях, изменив названия должности “CLERK” и “DRIVER” на “WORKER”.   
SELECT DECODE(JOBNAME, 'DRIVER', 'WORKER', 'CLERK', 'WORKER', JOBNAME) 
    FROM JOB;

--  9. Определите среднюю зарплату за годы, в которые были начисления не менее чем за три месяца.
SELECT YEAR, AVG(SALVALUE) 
    FROM SALARY 
    GROUP BY YEAR 
    HAVING COUNT(MONTH) >= 3;

--  10. Выведете ведомость получения зарплаты с указанием имен служащих.
SELECT EMP.EMPNAME, SALARY.SALVALUE, SALARY.MONTH
    FROM EMP, SALARY 
    WHERE EMP.EMPNO = SALARY.EMPNO;

--  11. Укажите  сведения о начислении сотрудникам зарплаты, попадающей в вилку: минимальный оклад по должности - минимальный оклад по должности плюс пятьсот. Укажите соответствующую вилке  должность.
SELECT EMP.EMPNAME, JOB.JOBNAME, SALARY.SALVALUE
    FROM SALARY 
        INNER JOIN EMP 
            ON SALARY.EMPNO = EMP.EMPNO 
        INNER JOIN CAREER 
            ON CAREER.EMPNO = EMP.EMPNO 
        INNER JOIN JOB 
            ON JOB.JOBNO = CAREER.JOBNO
    WHERE SALARY.SALVALUE >= JOB.MINSALARY 
        AND SALARY.SALVALUE <= JOB.MINSALARY + 500;

--   12. Укажите сведения о заработной плате, совпадающей с минимальными окладами по должностям (с указанием этих должностей).
SELECT EMP.EMPNAME, JOB.JOBNAME, SALARY.SALVALUE 
    FROM SALARY 
        INNER JOIN EMP
            ON (SALARY.EMPNO = EMP.EMPNO) 
        INNER JOIN CAREER
            ON (EMP.EMPNO = CAREER.EMPNO) 
        INNER JOIN JOB
            ON (CAREER.JOBNO = JOB.JOBNO) 
    WHERE SALARY.SALVALUE = JOB.MINSALARY;
     
--   13. Найдите сведения о карьере сотрудников с указанием вместо номера сотрудника его имени.
SELECT EMP.EMPNAME, CAREER.STARTDATE, CAREER.ENDDATE 
    FROM EMP
        NATURAL JOIN CAREER;

--   14. Найдите  сведения о карьере сотрудников с указанием вместо номера сотрудника его имени.
SELECT EMP.EMPNAME, CAREER.STARTDATE, CAREER.ENDDATE
    FROM EMP 
        INNER JOIN CAREER
            ON (EMP.EMPNO = CAREER.EMPNO);

--   15. Выдайте сведения о карьере сотрудников с указанием их имён, наименования должности, и названия отдела.
SELECT EMP.EMPNAME, JOB.JOBNAME, DEPT.DEPTNAME, CAREER.STARTDATE, CAREER.ENDDATE
    FROM EMP
        NATURAL JOIN CAREER
        NATURAL JOIN DEPT
        NATURAL JOIN JOB
    ORDER BY EMP.EMPNAME, CAREER.STARTDATE;

--   16. Выдайте сведения о карьере сотрудников с указанием их имён.
SELECT EMP.EMPNAME, CAREER.STARTDATE, CAREER.ENDDATE
    FROM EMP
        FULL OUTER JOIN CAREER
            ON (EMP.EMPNO = CAREER.EMPNO)
    ORDER BY EMP.EMPNAME, CAREER.STARTDATE;