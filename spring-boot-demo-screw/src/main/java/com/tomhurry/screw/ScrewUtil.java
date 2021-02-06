package com.tomhurry.screw;

import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.execute.DocumentationExecute;
import cn.smallbun.screw.core.process.ProcessConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author taozhi
 * @date 2021/2/6
 * @since 1.0.0
 */
@Service
public class ScrewUtil {

    @Resource
    private ApplicationContext applicationContext;

    public void documentGeneration(String outputPath, EngineFileType fileType) {

        documentGeneration("1.0.3", "生成文档信息描述", outputPath, fileType, null);
    }

    public void documentGeneration(String version, String description, String outputPath, EngineFileType fileType, ProcessConfig processConfig) {
        DataSource dataSourceMysql = applicationContext.getBean(DataSource.class);

        // 生成文件配置
        EngineConfig engineConfig = EngineConfig.builder()
                // 生成文件路径，自己mac本地的地址，这里需要自己更换下路径
                .fileOutputDir(outputPath)
                // 打开目录
                .openOutputDir(false)
                // 文件类型
                .fileType(fileType)
                // 生成模板实现
                .produceType(EngineTemplateType.freemarker)
                .build();

        // 生成文档配置（包含以下自定义版本号、描述等配置连接）
        Configuration config = Configuration.builder()
                .version(version)
                .description(description)
                .dataSource(dataSourceMysql)
                .engineConfig(engineConfig)
                .produceConfig(processConfig)
                .build();

        // 执行生成
        new DocumentationExecute(config).execute();
    }


}
