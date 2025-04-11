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

alter table gold_price_alert_tbl add last_notify_date TEXT;
alter table gold_price_alert_tbl add notify_interval_s REAL;

-- 新增攒金记录表
CREATE TABLE gold_record (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    weight DECIMAL(10,2) NOT NULL,           -- 重量（克）
    total_price DECIMAL(10,2) NOT NULL,      -- 成交总价
    purchase_channel VARCHAR(100),           -- 购买渠道
    remarks TEXT,                            -- 备注
    purchase_date TEXT NOT NULL,             -- 购买日期
    photo_url VARCHAR(200),                  -- 照片URL
    is_summary INTEGER NOT NULL,             -- 是否是汇总模式（1为汇总模式，0为明细模式）
    create_time TEXT NOT NULL                -- 创建时间
);

alter table gold_record drop column is_summary