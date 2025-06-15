/*
분화된 연도(YEAR), 분화된 연도별 대장균 크기의 편차(YEAR_DEV), 대장균 개체의 ID(ID) 를 출력하는 SQL 문

연도에 대해 오름차순으로 정렬하고 같은 연도에 대해서는 대장균 크기의 편차에 대해 오름차순
*/
WITH maxDev AS (
    SELECT
        *,
        MAX(SIZE_OF_COLONY) OVER (PARTITION BY YEAR(DIFFERENTIATION_DATE))AS MX_SZ
    FROM ECOLI_DATA
)

SELECT
    YEAR(DIFFERENTIATION_DATE) AS YEAR,
    (MX_SZ - SIZE_OF_COLONY) AS YEAR_DEV,
    ID
FROM maxDev
ORDER BY 1 ASC, 2 ASC