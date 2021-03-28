package com.tomhurry.dynamic.datasource.core;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.stat.DruidDataSourceStatManager;
import com.alibaba.fastjson.JSON;
import com.tomhurry.dynamic.datasource.model.DataSourceConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

/**
 * 动态路由数据源
 *
 * @author taozhi
 * @date 2020/12/21
 * @since 1.0.0
 */
@Slf4j
@Service
public class DynamicDataSource extends AbstractRoutingDataSource {

    private boolean debug = true;
    private Map<Object, Object> dynamicTargetDataSources;
    private Object dynamicDefaultTargetDataSource;

    @Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);
        this.dynamicTargetDataSources = targetDataSources;
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDataSource();
    }

    @Override
    public void setDefaultTargetDataSource(Object defaultTargetDataSource) {
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        this.dynamicDefaultTargetDataSource = defaultTargetDataSource;
    }

    /**
     * 新建数据源连接
     *
     * @param key
     * @param driverClass
     * @param url
     * @param username
     * @param password
     * @param datasourceType
     * @return
     */
    public boolean createDataSource(String key, String driverClass, String url, String username, String password, String datasourceType) {
        try {
            if (!testDataSource(driverClass, url, username, password)) {
                log.info("数据源{}连接不上【{},{},{}】", key, driverClass, username, password);
                return false;
            }

            DruidDataSource druidDataSource = new DruidDataSource();
            druidDataSource.setName(key);
            druidDataSource.setDriverClassName(driverClass);
            druidDataSource.setUrl(url);
            druidDataSource.setUsername(username);
            druidDataSource.setPassword(password);
            //初始化时建立物理连接的个数。初始化发生在显示调用init方法，或者第一次getConnection时
            druidDataSource.setInitialSize(1);
            //最大连接池数量
            druidDataSource.setMaxActive(20);
            //获取连接时最大等待时间，单位毫秒。当链接数已经达到了最大链接数的时候，应用如果还要获取链接就会出现等待的现象，等待链接释放并回到链接池，如果等待的时间过长就应该踢掉这个等待，不然应用很可能出现雪崩现象
            druidDataSource.setMaxWait(60000);
            //最小连接池数量
            druidDataSource.setMinIdle(5);
            String validationQuery = "select 1";
            //申请连接时执行validationQuery检测连接是否有效，这里建议配置为TRUE，防止取到的连接不可用
            druidDataSource.setTestOnBorrow(true);
            //建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
            druidDataSource.setTestWhileIdle(true);
            //用来检测连接是否有效的sql，要求是一个查询语句。如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会起作用。
            druidDataSource.setValidationQuery(validationQuery);
            //属性类型是字符串，通过别名的方式配置扩展插件，常用的插件有：监控统计用的filter:stat日志用的filter:log4j防御sql注入的filter:wall
            druidDataSource.setFilters("stat");
            //配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
            druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
            //配置一个连接在池中最小生存的时间，单位是毫秒，这里配置为3分钟180000
            druidDataSource.setMinEvictableIdleTimeMillis(180000);
            //打开druid.keepAlive之后，当连接池空闲时，池中的minIdle数量以内的连接，空闲时间超过minEvictableIdleTimeMillis，则会执行keepAlive操作，即执行druid.validationQuery指定的查询SQL，一般为select * from dual，只要minEvictableIdleTimeMillis设置的小于防火墙切断连接时间，就可以保证当连接空闲时自动做保活检测，不会被防火墙切断
            druidDataSource.setKeepAlive(true);
            //是否移除泄露的连接/超过时间限制是否回收。
            druidDataSource.setRemoveAbandoned(true);
            //泄露连接的定义时间(要超过最大事务的处理时间)；单位为秒。这里配置为1小时
            druidDataSource.setRemoveAbandonedTimeout(3600);
            //移除泄露连接发生是是否记录日志
            druidDataSource.setLogAbandoned(true);
            druidDataSource.init();
            this.dynamicTargetDataSources.put(key, druidDataSource);
            // 将map赋值给父类的TargetDataSources
            setTargetDataSources(this.dynamicTargetDataSources);
            // 将TargetDataSources中的连接信息放入resolvedDataSources管理
            super.afterPropertiesSet();
            log.debug(key + "数据源初始化成功");
            return true;
        } catch (Exception e) {
            log.error(e + "");
            return false;
        }
    }

    public boolean exist(String key) {
        return this.dynamicTargetDataSources.containsKey(key);
    }

    public void createDataSourceWithCheck(DataSourceConfig dataSourceConfig) throws Exception {
        String datasourceId = dataSourceConfig.getId();
        log.debug("开始检查数据源：{}", datasourceId);
        Map<Object, Object> tempDynamicDataSources = this.dynamicTargetDataSources;
        log.debug("当前数据源有：{}", JSON.toJSONString(tempDynamicDataSources.keySet()));
        if (tempDynamicDataSources.containsKey(datasourceId)) {
            log.debug("数据源" + datasourceId + "之前已经创建，准备测试数据源是否正常...");
            DruidDataSource druidDataSource = (DruidDataSource) tempDynamicDataSources.get(datasourceId);
            boolean rightFlag = true;
            Connection connection = null;
            try {
                log.debug(datasourceId + "数据源的概况->当前闲置连接数：" + druidDataSource.getPoolingCount());
                long activeCount = druidDataSource.getActiveCount();
                log.debug(datasourceId + "数据源的概况->当前活动连接数：" + activeCount);
                if (activeCount > 0) {
                    log.debug(datasourceId + "数据源的概况->活跃连接堆栈信息：" + druidDataSource.getActiveConnectionStackTrace());
                }
                log.debug("准备获取数据库连接...");
                connection = druidDataSource.getConnection();
                log.debug("数据源" + datasourceId + "正常");
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                rightFlag = false;
                log.debug("缓存数据源" + datasourceId + "已失效，准备删除...");
                if (deleteDataSource(datasourceId)) {
                    log.debug("缓存数据源删除成功");
                } else {
                    log.debug("缓存数据源删除失败");
                }
            } finally {
                if (null != connection) {
                    connection.close();
                }
            }
            if (rightFlag) {
                log.debug("不需要重新创建数据源");
            } else {
                log.debug("准备重新创建数据源...");
                createDataSource(dataSourceConfig);
                log.debug("重新创建数据源完成");
            }
        } else {
            createDataSource(dataSourceConfig);
        }
    }

    private void createDataSource(DataSourceConfig dataSourceConfig) throws Exception {
        String datasourceId = dataSourceConfig.getId();
        String dataSourceType = dataSourceConfig.getDataSourceType();
        String username = dataSourceConfig.getUserName();
        String password = dataSourceConfig.getPassword();
        String url = dataSourceConfig.getUrl();
        String driveClass = dataSourceConfig.getDriverClassName();
        if (testDataSource(driveClass, url, username, password)) {
            boolean result = this.createDataSource(datasourceId, driveClass, url, username, password, dataSourceType);
            if (!result) {
                log.error("数据源" + datasourceId + "创建失败");
            }
        } else {
            log.error("数据源配置有错误");
        }
    }


    /**
     * 删除数据源
     *
     * @param datasourceId
     * @return
     */
    public boolean deleteDataSource(String datasourceId) {
        Map<Object, Object> tempDynamicTargetDataSources = this.dynamicTargetDataSources;
        if (tempDynamicTargetDataSources.containsKey(datasourceId)) {
            Set<DruidDataSource> druidDataSourceInstances = DruidDataSourceStatManager.getDruidDataSourceInstances();
            for (DruidDataSource dataSource : druidDataSourceInstances) {
                if (datasourceId.equals(dataSource.getName())) {
                    DruidDataSourceStatManager.removeDataSource(tempDynamicTargetDataSources.get(datasourceId));
                    tempDynamicTargetDataSources.remove(datasourceId);
                    setTargetDataSources(tempDynamicTargetDataSources);
                    super.afterPropertiesSet();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 测试连接
     *
     * @param driverClass
     * @param url
     * @param username
     * @param password
     * @return
     */
    public boolean testDataSource(String driverClass, String url, String username, String password) throws SQLException {
        Connection connection = null;
        try {
            Class.forName(driverClass);
            connection = DriverManager.getConnection(url, username, password);
            return true;
        } catch (Exception e) {
            log.error(e + "");
            return false;
        } finally {
            if (!ObjectUtils.isEmpty(connection)) {
                connection.close();
            }
        }
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public Map<Object, Object> getDynamicTargetDataSources() {
        return dynamicTargetDataSources;
    }

    public void setDynamicTargetDataSources(Map<Object, Object> dynamicTargetDataSources) {
        this.dynamicTargetDataSources = dynamicTargetDataSources;
    }

    public Object getDynamicDefaultTargetDataSource() {
        return dynamicDefaultTargetDataSource;
    }

    public void setDynamicDefaultTargetDataSource(Object dynamicDefaultTargetDataSource) {
        this.dynamicDefaultTargetDataSource = dynamicDefaultTargetDataSource;
    }
}
