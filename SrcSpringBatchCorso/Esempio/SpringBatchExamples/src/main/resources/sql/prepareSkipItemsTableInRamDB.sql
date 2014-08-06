CREATE TABLE SKIP_ITEMS (
    id INT IDENTITY PRIMARY KEY,
    jobExecutionId INT,
    stepExecutionId INT,
    jobName VARCHAR(100),
    type VARCHAR(100),
    item VARCHAR(100),
    msg VARCHAR(1000),
    runId INT
)


DELETE FROM SKIP_ITEMS;