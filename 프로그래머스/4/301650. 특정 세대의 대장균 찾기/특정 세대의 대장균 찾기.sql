WITH RECURSIVE generation_count AS (
    SELECT
        ID,
        1 AS GENERATION
    FROM ECOLI_DATA 
    WHERE PARENT_ID	IS NULL
    
    UNION ALL
    
    SELECT
        E.ID,
        G.GENERATION +1 AS GENERATION    
    FROM ECOLI_DATA  E
    JOIN generation_count G
    ON E.PARENT_ID = G.ID
)

SELECT
    ID
FROM generation_count
WHERE GENERATION = 3
ORDER BY 1 ASC
