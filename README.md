# Turvo test task

There are 3 screens
1) Ticker symbols
2) List of the entered stocks with the price and mini-chart
3) Chart

App use free finance API www.alphavantage.co that has limitation (up to 5 API requests per minute and 500 requests per day).
So in case if you faced some empty graph data, you can switch to Dummy data in gradle
```buildConfigField "Boolean", "IS_DUMMY", "false"``` or you can wait 1 minute before try new tickers.

Unfortunately, API allows getting only one ticker data per request, so App call API for every ticker in the list(limit tickers 5 by task)
