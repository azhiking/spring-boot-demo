CREATE TABLE IF NOT EXISTS easy_lock (
    id SERIAL PRIMARY KEY,
    key VARCHAR(255) NOT NULL,
    owner VARCHAR(255) NOT NULL,
    reentrant_count INT,
    timeout BIGINT,
    create_time BIGINT,
    update_time BIGINT
);

COMMENT ON TABLE easy_lock IS '分布式锁表';
COMMENT ON COLUMN easy_lock.key IS '锁的key';
COMMENT ON COLUMN easy_lock.owner IS '锁的拥有者';
COMMENT ON COLUMN easy_lock.reentrant_count IS '重入次数';
COMMENT ON COLUMN easy_lock.timeout IS '锁的超时时间, 单位毫秒';
COMMENT ON COLUMN easy_lock.create_time IS '创建时间';
COMMENT ON COLUMN easy_lock.update_time IS '更新时间';

CREATE INDEX IF NOT EXISTS idx_key ON easy_lock(key);