/*
HR_DEPARTMENT, HR_EMPLOYEES, HR_GRADE 테이블을 이용해 사원별 성과금 정보를 조회

평가 점수별 등급과 등급에 따른 성과금 정보가 아래와 같을 때, 사번, 성명, 평가 등급, 성과금을 조회하는 SQL문을 작성

평가등급의 컬럼명은 GRADE로, 성과금의 컬럼명은 BONUS

결과는 사번 기준으로 오름차순 정렬
*/

-- HR_GRADE 에서 각 사원별 최근 기록만 가지고오기
-- 위의 테이블 기준으로 사원당 등급 매기기
WITH
LATEST_SCORE AS (
    SELECT
        EMP_NO,
        AVG(SCORE) AS SCORE
    FROM HR_GRADE
    GROUP BY EMP_NO
),
EMP_GRADE AS (
    SELECT
        E.EMP_NO,
        CASE
            WHEN (S.SCORE >= 96) THEN 'S'
            WHEN (S.SCORE >= 90) THEN 'A'
            WHEN (S.SCORE >= 80) THEN 'B'
            ELSE 'C'
        END AS GRADE
    FROM HR_EMPLOYEES E
    JOIN LATEST_SCORE S
    ON E.EMP_NO = S.EMP_NO
)

-- 본문
SELECT DISTINCT
    E.EMP_NO,
    E.EMP_NAME,
    G.GRADE,
    CASE
        WHEN G.GRADE = 'S' THEN E.SAL * 0.2
        WHEN G.GRADE = 'A' THEN E.SAL * 0.15
        WHEN G.GRADE = 'B' THEN E.SAL * 0.1
        WHEN G.GRADE = 'C' THEN 0   
    END AS BONUS
FROM HR_EMPLOYEES E
JOIN EMP_GRADE G
ON E.EMP_NO = G.EMP_NO
ORDER BY 1 ASC
;