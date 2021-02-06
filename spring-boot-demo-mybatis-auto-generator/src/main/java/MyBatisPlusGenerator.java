import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.sql.SQLException;

/**
 * MyBatisPlus自动生成器
 * 需要修改的地方：
 * 1.作者名
 * 2.输出路径
 * 3.数据源配置
 * 4.包名：pkConfig.setParent
 *
 * @author taozhi
 * @date 2021/1/14
 * @since 1.0.0
 */
public class MyBatisPlusGenerator {

    public static void main(String[] args) throws SQLException {
        // 选择 freemarker 引擎，默认 Veloctiy
        //1. 全局配置
        GlobalConfig config = new GlobalConfig();
        config.setActiveRecord(true) // 是否支持AR模式
                .setAuthor("taozhi") // 作者
                .setEnableCache(false)// XML 二级缓存
                .setOutputDir("F:\\Workspaces\\Github-Repositories\\spring-boot-demo\\spring-boot-demo-mybatis-auto-generator\\src\\main\\java") // 生成路径
                .setFileOverride(true)  // 文件覆盖
                .setBaseResultMap(true)//生成基本的resultMap
//                .setBaseColumnList(true)//生成基本的SQL片段
                /* 自定义文件命名，注意 %s 会自动填充表实体属性！ */
                .setMapperName("%sMapper")
                .setXmlName("%sMapper")
                .setServiceName("%sService")
                .setServiceImplName("%sServiceImpl")
                .setControllerName("%sController");

        //2. 数据源配置
        DataSourceConfig dsConfig = new DataSourceConfig();
        dsConfig.setDbType(DbType.MYSQL)  // 设置数据库类型
                .setDriverName("com.mysql.cj.jdbc.Driver")
                .setUrl("jdbc:mysql://127.0.0.1:3306/cdmone_tester?useUnicode=true&useSSL=false&characterEncoding=utf8")
                .setUsername("root")
                .setPassword("123456");

        //4. 包名策略配置
        PackageConfig pkConfig = new PackageConfig();
        pkConfig.setParent("com.tomhurry.sniffer")
                .setMapper("dao")
                .setService("service")
                .setController("controller")
                .setEntity("model")
                .setXml("dao/mapper");

        //3. 策略配置globalConfiguration中
        StrategyConfig stConfig = new StrategyConfig();
        stConfig.setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setEntityLombokModel(true);


        //5. 整合配置
        AutoGenerator ag = new AutoGenerator();
        ag.setGlobalConfig(config)
                .setDataSource(dsConfig)
                .setStrategy(stConfig)
                .setPackageInfo(pkConfig);

        //6. 执行
        ag.execute();
    }
}
