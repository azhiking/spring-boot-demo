package com.tomhurry.dynamic.datasource.controller;

import com.tomhurry.api.ApiResponse;
import com.tomhurry.dynamic.datasource.service.DataSourceConfigService;
import com.tomhurry.dynamic.datasource.dto.result.SnifferResult;
import com.tomhurry.dynamic.datasource.sniffer.director.model.TaskInf;
import com.tomhurry.dynamic.datasource.sniffer.director.service.TaskInfService;
import com.tomhurry.dynamic.datasource.sniffer.engine.model.Job;
import com.tomhurry.dynamic.datasource.sniffer.engine.model.ServiceReceive;
import com.tomhurry.dynamic.datasource.sniffer.engine.service.JobService;
import com.tomhurry.dynamic.datasource.sniffer.engine.service.ServiceReceiveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author taozhi
 * @date 2021/3/27
 * @since 1.0.0
 */
@RestController
@Service
@Slf4j
public class SnifferController {

    @Resource
    private TaskInfService taskInfService;
    @Resource
    private ServiceReceiveService serviceReceiveService;
    @Resource
    private JobService jobService;
    @Resource
    private DataSourceConfigService dataSourceConfigService;

    @GetMapping("/sniffer/search")
    public ApiResponse<List<SnifferResult>> logAnalyze(String jobId, String director, String engine) {
        log.info("接收到日志分析操作：{}", jobId);
        List<SnifferResult> result = new ArrayList<>();
        // 查询director的信息
        if (!dataSourceConfigService.prepareDataSourceConnection(director)) {
            return ApiResponse.genFailedResponse("无法连接director数据库", result);
        }
        TaskInf taskInf = taskInfService.getById(jobId);
        result.add(taskInfService.convert(taskInf));
        dataSourceConfigService.destroyDataSourceConnection(director);

        // 查询engine的信息
        if (!dataSourceConfigService.prepareDataSourceConnection(engine)) {
            return ApiResponse.genFailedResponse("无法连接engine数据库", result);
        }

        ServiceReceive serviceReceive = serviceReceiveService.getById(jobId);
        result.add(serviceReceiveService.convert(serviceReceive));

        Job job = jobService.getById(jobId);
        result.add(jobService.convert(job));

        dataSourceConfigService.destroyDataSourceConnection(engine);

        return ApiResponse.genOkResponse(result);
    }


}
