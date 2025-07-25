/*
2021년에 가입한 전체 회원들 중 
상품을 구매한 회원수와
상품을 구매한 회원의 비율(=2021년에 가입한 회원 중 상품을 구매한 회원수 / 2021년에 가입한 전체 회원 수)을 
년, 월 별로 출력

상품을 구매한 회원의 비율은 소수점 두번째자리에서 반올림

전체 결과는 년을 기준으로 오름차순 정렬
년이 같다면 월을 기준으로 오름차순 정렬
*/
SELECT
    YEAR(O.SALES_DATE) AS YEAR,
    MONTH(O.SALES_DATE) AS MONTH,
    COUNT(DISTINCT O.USER_ID) AS PURCHASED_USERS,
    ROUND((COUNT(DISTINCT O.USER_ID) / TOTAL.TOTAL),1)AS PUCHASED_RATIO
FROM ONLINE_SALE O
LEFT JOIN USER_INFO U
ON O.USER_ID = U.USER_ID
JOIN (
    SELECT COUNT(*) AS TOTAL
    FROM USER_INFO
    WHERE YEAR(JOINED) = 2021
) AS TOTAL
WHERE YEAR(U.JOINED) = 2021
GROUP BY YEAR(O.SALES_DATE), MONTH(O.SALES_DATE)
ORDER BY YEAR ASC , MONTH ASC
;