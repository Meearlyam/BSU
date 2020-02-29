DROP TABLE TMP_EMP;
DROP TABLE TMP_SALARY;
DROP TABLE TMP_CAREER;
DROP TABLE TMP_JOB;

UPDATE JOB 
SET MINSALARY = 1000;

UPDATE JOB 
SET MINSALARY = MINSALARY * 1.1 
WHERE JOBNAME != 'FINANCIAL DIRECTOR';

UPDATE JOB 
SET MINSALARY = 
     CASE 
         WHEN JOBNAME = 'CLERK' 
            THEN MINSALARY * 1.1 
         WHEN JOBNAME = 'FINANCIAL DIRECTOR' 
            THEN MINSALARY * 1.2 
      END 
WHERE JOBNAME = 'CLERK' OR JOBNAME = 'FINANCIAL DIRECTOR';

UPDATE JOB 
SET MINSALARY = 0.9 * (SELECT MINSALARY  
                       FROM JOB 
                       WHERE JOBNAME = 'EXECUTIVE DIRECTOR') 
WHERE JOBNAME = 'FINANCIAL DIRECTOR';

UPDATE EMP 
SET EMPNAME = LOWER(EMPNAME) 
WHERE EMPNAME LIKE 'J%';

UPDATE EMP 
SET EMPNAME = INITCAP(EMPNAME) 
WHERE EMPNAME LIKE '% %';

UPDATE EMP 
SET EMPNAME = UPPER(EMPNAME);

UPDATE DEPT 
SET DEPTADDR = (SELECT DEPTADDR FROM DEPT WHERE DEPTNAME = 'SALES') 
WHERE DEPTNAME = 'RESEARCH';

INSERT INTO EMP VALUES ( (SELECT MAX(EMPNO) FROM EMP)+1, 'MIKITA ISHCHANKA', TO_DATE('10-03-2000','dd-mm-yyyy'), 7790);

INSERT INTO CAREER VALUES ( 
          (SELECT JOBNO FROM JOB WHERE JOBNAME = 'CLERK'), 
          (SELECT MAX(EMPNO) FROM EMP WHERE EMPNAME = 'MIKITA ISHCHANKA'), 
          (SELECT DEPTNO FROM DEPT WHERE DEPTNAME = 'ACCOUNTING'), 
          TO_DATE(SYSDATE,'dd-mm-yyyy'), 
          NULL);

CREATE TABLE TMP_EMP 
       (EMPNO NUMBER(4) 
             PRIMARY KEY, 
	EMPNAME VARCHAR2(30) 
             NOT NULL, 
        BIRTHDATE DATE, 
        MANAGER_ID NUMBER(4) 
	     REFERENCES EMP(EMPNO));



INSERT INTO TMP_EMP 
SELECT * FROM EMP 
WHERE EMPNO IN (SELECT EMPNO FROM CAREER 
                NATURAL JOIN JOB 
                WHERE JOBNAME = 'CLERK' AND ENDDATE IS NULL);
                
DROP TABLE TMP_EMP;

CREATE TABLE TMP_EMP 
       (EMPNO NUMBER(4) 
             PRIMARY KEY, 
	EMPNAME VARCHAR2(30) 
             NOT NULL, 
        BIRTHDATE DATE, 
        MANAGER_ID NUMBER(4) 
	     REFERENCES EMP(EMPNO));

INSERT INTO TMP_EMP 
SELECT * FROM EMP 
WHERE EMPNO IN (SELECT EMPNO  
                       FROM CAREER first_c 
                       WHERE ENDDATE IS NOT NULL AND (SELECT COUNT(*) FROM CAREER second_c  
                                                             WHERE ENDDATE IS NOT NULL AND first_c.EMPNO = second_c.EMPNO) = 1);

INSERT INTO TMP_EMP 
SELECT * FROM EMP 
WHERE EMPNO NOT IN (SELECT EMPNO 
                       FROM CAREER);

CREATE TABLE TMP_JOB
       (JOBNO NUMBER(4) 
             PRIMARY KEY, 
	JOBNAME VARCHAR2(30) 
             NOT NULL, 
        MINSALARY NUMBER(6));
        
INSERT INTO TMP_JOB 
SELECT * FROM JOB 
WHERE JOBNO NOT IN (SELECT JOBNO FROM CAREER );

CREATE TABLE TMP_SALARY 
       (EMPNO NUMBER(4) 
             REFERENCES EMP(EMPNO), 
        MONTH NUMBER(2) 
             CHECK(MONTH>0 AND MONTH<13), 
        YEAR NUMBER(4) 
             CHECK(YEAR>1900 AND YEAR<2020), 
	SALVALUE NUMBER(6));
    
INSERT INTO TMP_SALARY 
(SELECT EMPNO, 
        EXTRACT(MONTH FROM ADD_MONTHS(SYSDATE, -1)) AS MONTH, 
        EXTRACT(YEAR FROM ADD_MONTHS(SYSDATE, -1)) AS YEAR, 
        MINSALARY * 1.2 AS SALVALUE 
FROM CAREER 
NATURAL JOIN JOB 
WHERE ENDDATE IS NULL);


DELETE FROM TMP_SALARY  
WHERE YEAR = EXTRACT(YEAR FROM SYSDATE) - 1;

CREATE TABLE TMP_CAREER 
       (JOBNO NUMBER(4) 
             REFERENCES JOB(JOBNO) NOT NULL, 
        EMPNO NUMBER(4) 
             REFERENCES EMP(EMPNO) NOT NULL, 
        DEPTNO NUMBER(4) 
             REFERENCES DEPT(DEPTNO), 
	STARTDATE DATE 
             NOT NULL, 
	ENDDATE DATE);

DELETE FROM TMP_CAREER 
WHERE ENDDATE IS NOT NULL;

DELETE FROM TMP_SALARY 
WHERE EMPNO IN (SELECT EMPNO FROM CAREER WHERE ENDDATE IS NOT NULL);

DELETE FROM TMP_EMP 
WHERE EMPNO NOT IN (SELECT EMPNO FROM CAREER);




                       



