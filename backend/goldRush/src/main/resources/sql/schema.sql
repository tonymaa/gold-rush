
CREATE TABLE user_tbl (
                          id INTEGER PRIMARY KEY AUTOINCREMENT, -- 假设 BaseEntity 包含一个 ID 属性
                          name TEXT,
                          email TEXT,
                          gold_weight REAL,
                          gold_cost REAL
);
CREATE TABLE gold_price_tbl (
                            id INTEGER PRIMARY KEY AUTOINCREMENT, -- 假设 BaseEntity 包含一个 ID 属性
                            update_date TEXT, -- SQLite中没有直接的Date类型，可以使用TEXT保存日期时间
                            bid REAL,
                            sell REAL
);
CREATE TABLE gold_price_alert_tbl (
    id INTEGER PRIMARY KEY AUTOINCREMENT, -- 假设 BaseEntity 包含一个 ID 属性
    description TEXT,
    status TEXT,
    left_point REAL,
    right_point REAL,
    type TEXT,
    user_name TEXT -- 假设 userId 是字符串类型，作为外键或其他用途
);
