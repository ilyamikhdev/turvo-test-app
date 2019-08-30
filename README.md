# Turvo test task

App use free finance API www.alphavantage.co that has limitation (up to 5 API requests per minute and 500 requests per day).
So in case if you faced some empty graph data, you can switch to Dummy data in gradle *buildConfigField "Boolean", "IS_DUMMY", **"false"***
