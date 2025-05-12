-- 코드를 입력하세요

/*
자동차 종류가 '세단' 또는 'SUV' 인 자동차 중
2022년 11월 1일부터 2022년 11월 30일까지 대여 가능하고
30일간의 대여 금액이 50만원 이상 200만원 미만인 자동차에 대해서
자동차 ID, 자동차 종류, 대여 금액(컬럼명: FEE) 리스트를 출력

대여 금액을 기준으로 내림차순 정렬하고, 대여 금액이 같은 경우 자동차 종류를 기준으로 오름차순 정렬, 자동차 종류까지 같은 경우 자동차 ID를 기준으로 내림차순 정렬
*/

SELECT 
    C.CAR_ID,	
    C.CAR_TYPE,
    FLOOR((C.DAILY_FEE*(1- D.DISCOUNT_RATE/100)) * 30) AS FEE
FROM
    CAR_RENTAL_COMPANY_CAR C
LEFT JOIN
    CAR_RENTAL_COMPANY_DISCOUNT_PLAN D
    ON C.CAR_TYPE = D.CAR_TYPE
    AND D.DURATION_TYPE = '30일 이상'
WHERE
    C.CAR_TYPE  IN ('세단', 'SUV')
    -- 2022년 11월 1일부터 2022년 11월 30일까지 대여 가능 - > HISTORY에서 대여 기록없는 기록의 차 ID
       AND C.CAR_ID NOT IN (
           SELECT CAR_ID
           FROM CAR_RENTAL_COMPANY_RENTAL_HISTORY
           WHERE NOT (
               END_DATE < '2022-11-01' OR START_DATE > '2022-11-30')
       )
       -- 30일간 대여금액이 500000~2000000
       AND (C.DAILY_FEE*(1- D.DISCOUNT_RATE/100)) * 30 BETWEEN  500000 AND 1999999
ORDER BY
    FEE DESC,
    C.CAR_TYPE ASC,
    C.CAR_ID DESC
;
    