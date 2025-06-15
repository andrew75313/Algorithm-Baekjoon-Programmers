/*
 물고기 종류 별로 가장 큰 물고기의 ID, 물고기 이름, 길이를 출력
 
 결과는 물고기의 ID에 대해 오름차순 정렬
 단, 물고기 종류별 가장 큰 물고기는 1마리만 있으며 10cm 이하의 물고기가 가장 큰 경우는 없습니다.
 */
 WITH maxfish AS (
    SELECT
        FI.*     
     FROM FISH_INFO FI 
     JOIN (
        SELECT
            FISH_TYPE,
            MAX(LENGTH) AS MAX_LENGTH
        FROM FISH_INFO
        GROUP BY FISH_TYPE
         ) MX
     ON FI.FISH_TYPE = MX.FISH_TYPE AND FI.LENGTH = MX.MAX_LENGTH
 )
 
 SELECT
    MX.ID,
    NA.FISH_NAME,
    MX.LENGTH AS LENGTH
 FROM maxfish MX
 LEFT JOIN  FISH_NAME_INFO NA
 USING (FISH_TYPE)
 ORDER BY 1 ASC
    