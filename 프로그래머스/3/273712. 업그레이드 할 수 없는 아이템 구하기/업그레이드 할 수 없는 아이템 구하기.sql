/*
더 이상 업그레이드할 수 없는 아이템의 아이템 ID(ITEM_ID), 아이템 명(ITEM_NAME), 아이템의 희귀도(RARITY)를 출력
 아이템 ID를 기준으로 내림차순 정렬
*/

SELECT
    II.ITEM_ID,
    II.ITEM_NAME,
    II.RARITY
FROM
    ITEM_INFO II
RIGHT JOIN
    ITEM_TREE IT
USING (ITEM_ID)
WHERE II.ITEM_ID NOT IN (
    SELECT DISTINCT
        PARENT_ITEM_ID
    FROM
        ITEM_TREE
    WHERE
        PARENT_ITEM_ID IS NOT NULL
)
ORDER BY
    1 DESC
    


