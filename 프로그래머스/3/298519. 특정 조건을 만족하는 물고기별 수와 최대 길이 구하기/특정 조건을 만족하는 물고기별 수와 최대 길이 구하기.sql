/*
FISH_INFO에서 평균 길이가 33cm 이상인 물고기들을 종류별로 분류하여 잡은 수, 최대 길이, 물고기의 종류를 출력

고기 종류에 대해 오름차순으로 정렬
10cm이하의 물고기들은 10cm로 취급하여 평균 길이를
*/

WITH AvgFish AS (
    SELECT
        FISH_TYPE,
        AVG(CASE WHEN
                        LENGTH IS NULL
                        THEN 10
                        ELSE LENGTH
                        END ) AS AvgLth
    FROM
        FISH_INFO
    GROUP BY
        FISH_TYPE
)

SELECT
    COUNT(*) AS FISH_COUNT,
    MAX(LENGTH) AS MAX_LENGTH,
    FISH_TYPE
FROM
    FISH_INFO
WHERE
    FISH_TYPE IN  (
        SELECT FISH_TYPE
        FROM AvgFish
        WHERE AvgLth >= 33
)
GROUP BY
    FISH_TYPE
ORDER BY
    FISH_TYPE ASC
    
