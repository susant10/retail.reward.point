customer's transactions

Date        amount
20230225    120
20230226    80
20230310    60
20230311    40
20230401    140


Here is the url to access API : http://localhost:8080/reward/point

API doesn't take any argument. All these customer transactions are hard coded.

To add and remove please see the mockxx methods in RewardCalculatingService inside package com.tekntime.retailer.reward.service.



API response will look like as shown below.
point is monthly point.
totalPoint is sum of monthly points.

<TotalReward>
<rewards>
<monthYear>1232</monthYear>
<transactions>
<transactions>
<date>2023-03-10T05:00:00.000+0000</date>
<amount>60.0</amount>
</transactions>
<transactions>
<date>2023-03-11T05:00:00.000+0000</date>
<amount>40.0</amount>
</transactions>
</transactions>
<point>10.0</point>
</rewards>

<totalPoint>260.0</totalPoint>
</TotalReward>
==========================


API Health check - Actuator is enabled for health check.

URL for health check :  http://localhost:8080/actuator/health
Responese : {"status":"UP"}
