package com.tomhurry.screw;

import cn.smallbun.screw.core.engine.EngineFileType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.swing.filechooser.FileSystemView;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
class ScrewUtilTest {

    @Resource
    private ScrewUtil screwUtil;

    @Test
    void documentGeneration() {
        String desktopPath = FileSystemView.getFileSystemView().getHomeDirectory().getPath();
        screwUtil.documentGeneration(desktopPath, EngineFileType.HTML);
    }
}