/*
각 세대별 자식이 없는 개체의 수(COUNT)와
세대(GENERATION)를 출력
단, 모든 세대에는 자식이 없는 개체가 적어도 1개체는 존재합니다.

이때 결과는 세대에 대해 오름차순 정렬
*/
 
 /*SQL 재귀*/
 WITH RECURSIVE GENERATION AS (
     -- 1세대 INIT
     SELECT
        ID,
        1 AS GENERATION
     FROM ECOLI_DATA
     WHERE PARENT_ID IS NULL
     
     UNION ALL

     -- 다음 세대 재귀
     SELECT
        E.ID,
        G. GENERATION + 1
     FROM ECOLI_DATA E
     JOIN GENERATION G
     ON E.PARENT_ID = G.ID
 )
 
 -- 이제 자식이 없는 샘플들만 GENERATION 값과 함께 조회
SELECT
    COUNT(*) AS COUNT,
    G.GENERATION
FROM ECOLI_DATA P
LEFT JOIN ECOLI_DATA C
ON C.PARENT_ID = P.ID
JOIN GENERATION G
ON P.ID  = G.ID
WHERE C.ID IS NULL
GROUP BY G.GENERATION
ORDER BY 2 ASC
;