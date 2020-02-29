/*
        3.3
*/

-- 1. Рефлексивное соединение. Представление отношений родитель-потомок
-- Требуется представить имя каждого сотрудника таблицы EMP
-- а также имя его руководителя: 
SELECT Worker.EMPNAME || ' works for ' || Manager.EMPNAME as Emp_Manager
FROM EMP Worker, EMP Manager
WHERE Worker.MANAGER_ID = Manager.EMPNO;

-- START WITH говорит Ораклу с чего начинать цикл, т.е. какая строка (или строки) будет корневой. 
-- CONNECT BY - как долго продолжать цикл
-- PRIOR - “Позвоните родителям” – говорит он, заставляя Оракл обратиться к предыдущей записи. 
-- С его помощью можно написать правило pid = PRIOR id 
 
 /*Оракл находит первую запись, удовлетворяющую условию в START WITH, 
 и принимается искать следующую. При этом к той первой записи можно обратиться через PRIOR. 
 Если мы все сделали правильно, то Оракл будет искать записи, в которых в поле 
 для хранения информации о родителе (pid) будет содержаться значение, 
 равное идентификатору id нашей первой записи. Таким образом будут найдены все потомки корневой записи. 
 А так как процесс рекурсивный, аналогичный поиск будет продолжаться с каждой найденной строкой, пока не отыщутся все потомки. 
*/

-- 2. Иерархический запрос
-- Требуется представить имя каждого сотрудника таблицы EMP 
-- (даже сотрудника, которому не назначен руководитель) и имя его руководителя.
SELECT EMPNAME || ' reports to ' || PRIOR EMPNAME AS "Walk Top Down"
FROM EMP
START WITH MANAGER_ID IS NULL
CONNECT BY MANAGER_ID = PRIOR EMPNO;


-- LEVEL - в нем записывается уровень записи по отношению к корневой. 
-- Так, 1-ая запись будет иметь уровень 1, ее потомки уровень 2, потомки потомков — 3 и т.д.

-- 3. Представление отношений потомок-родитель-прародитель
-- Требуется показать иерархию от CLARK до JOHN KLINTON:
-- Функция SYS_CONNECT_BY_PATH возвращает одну строку, 
-- содержащую все значения столбцов, встречающиеся в пути от корня к узлу.

/*
Ключевое слово PRIOR обеспечивает возможность доступа к значениям
предыдущей записи иерархии. Таким образом, для любого данного
EMPNO с помощью PRIOR MGR можно обратиться к номеру руководителя этого служащего. 
Конструкцию CONNECT BY PRIOR MGR = EMPNO можно рассматривать как представляющую объединение между,
в данном случае, родителем и потомком.
*/

-- SYS_CONNECT_BY_PATH -это функция, которая используется в иерархических запросах 
-- для получения пути для текущего узла, начиная с корневого узла. 

SELECT LTRIM(SYS_CONNECT_BY_PATH(EMPNAME, '-->'), '-->') AS LEAF__BRANCH__ROOT
FROM EMP
-- WHERE LEVEL = 3
WHERE MANAGER_ID IS NULL
START WITH EMPNAME = 'CLARK'
CONNECT BY PRIOR MANAGER_ID = EMPNO;

-- 4. Иерархическое представление таблицы
-- Требуется получить результирующее множество, описывающее иерархию всей таблицы
SELECT LTRIM(SYS_CONNECT_BY_PATH(EMPNAME, '-->'), '-->') AS EMP_TREE
FROM EMP
START WITH EMPNAME = 'JOHN KLINTON'
CONNECT BY PRIOR EMPNO = MANAGER_ID;

SELECT LTRIM(SYS_CONNECT_BY_PATH(EMPNAME, '-->'), '-->') AS EMP_TREE
FROM EMP
START WITH MANAGER_ID IS NULL
CONNECT BY PRIOR EMPNO = MANAGER_ID;

-- 5. Представление уровня иерархии
-- Требуется показать уровень иерархии каждого сотрудника
/*
Предложение ORDER SIBLINGS BY используется при запросах к иерархическим таблицам с предложением CONNECT BY. 
Предложение ORDER SIBLINGS BY заставляет Oracle сохранять сортировку, определяемую предложением CONNECT BY, 
и применять ее только к одноранговым строкам (то есть строкам, находящимся на одном уровне иерархии)
*/
SELECT LPAD(' ', LEVEL, '-') || EMPNAME AS Org_Chart
FROM EMP
START WITH MANAGER_ID IS NULL
CONNECT BY PRIOR EMPNO = MANAGER_ID
ORDER SIBLINGS BY EMPNAME;  

-- 6. Выбор всех дочерних строк для заданной строки
-- Требуется найти всех служащих, которые явно или  неявно подчиняются ALLEN:
SELECT EMPNAME
FROM EMP
START WITH EMPNAME = 'ALLEN'
CONNECT BY PRIOR EMPNO = MANAGER_ID;






-- 5

-- 1. Создайте представление, содержащее данные о сотрудниках пенсионного возраста.
-- Чтобы измерить CREATE OR REPLACE VIEW
CREATE VIEW PENSIONERS AS
SELECT * FROM EMP
WHERE MONTHS_BETWEEN(SYSDATE, BIRTHDATE) / 12 >= 60;

SELECT * FROM PENSIONERS;

DROP VIEW PENSIONERS;

-- 2. Создайте представление, содержащее данные об уволенных сотрудниках: 
-- имя сотрудника, дата увольнения, отдел, должность.
CREATE VIEW DISMISSED AS
SELECT EMP.EMPNAME, CAREER.ENDDATE, DEPT.DEPTNAME, JOB.JOBNAME
FROM EMP NATURAL JOIN CAREER NATURAL JOIN DEPT NATURAL JOIN JOB
WHERE CAREER.ENDDATE IS NOT NULL;

SELECT * FROM DISMISSED;

/*
3. Создайте представление, содержащее имя сотрудника, должность, занимаемую сотрудником в данный момент, 
суммарную заработную плату сотрудника за третий квартал 2010 года. 
Первый столбец назвать Sotrudnik, второй – Dolzhnost, третий – Itogo_3_kv.
*/
CREATE VIEW KVARTAL (SOTRUDNIK, DOLZHNOST, ITOGO_3_KV) AS
SELECT E.EMPNAME, J.JOBNAME, SUM(S.SALVALUE)
FROM JOB J NATURAL JOIN CAREER NATURAL JOIN EMP E NATURAL JOIN SALARY S
WHERE S.YEAR = 2010 AND S.MONTH BETWEEN 7 AND 9
GROUP BY E.EMPNAME, J.JOBNAME;

SELECT * FROM KVARTAL;

/*
4. На основе представления из задания 2 и таблицы SALARY создайте представление, 
содержащее данные об уволенных сотрудниках, которым зарплата начислялась более 2 раз. 
В созданном представлении месяц начисления зарплаты и сумма зарплаты вывести в одном столбце, 
в качестве разделителя использовать запятую.
*/
-- view_name (column_names) - задает имена полей представления.
CREATE VIEW DISMISSED2 (EMPNAME, ENDATE, DEPTNAME, JOBNAME, MONTH_AND_SUM) AS
SELECT D.EMPNAME, D.ENDDATE, D.DEPTNAME, D.JOBNAME, S.MONTH || ', ' || S.SALVALUE
    FROM DISMISSED D NATURAL JOIN SALARY S
    WHERE S.EMPNO IN (  SELECT EMPNO
                        FROM DISMISSED NATURAL JOIN SALARY
                        GROUP BY EMPNO
                        HAVING COUNT(SALVALUE) > 2);