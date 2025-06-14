/*
HR_DEPARTMENT와 HR_EMPLOYEES 테이블을 이용해 부서별 평균 연봉을 조회하려 합니다. 부서별로 부서 ID, 영문 부서명, 평균 연봉을 조회하는 SQL문

평균연봉은 소수점 첫째 자리에서 반올림하고 컬럼명은 AVG_SAL

 부서별 평균 연봉을 기준으로 내림차순 정렬
*/

SELECT
    DE.DEPT_ID,
    DE.DEPT_NAME_EN,
    ROUND(AVG(EM.SAL)) AS AVG_SAL
FROM
    HR_EMPLOYEES EM
LEFT JOIN
    HR_DEPARTMENT DE
USING (DEPT_ID)
GROUP BY
    DE.DEPT_ID
ORDER BY
    3 DESC
