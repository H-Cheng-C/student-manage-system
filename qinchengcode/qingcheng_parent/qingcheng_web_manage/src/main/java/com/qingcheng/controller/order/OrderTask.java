package com.qingcheng.controller.order;

import com.qingcheng.service.order.CategoryReportService;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Component
@EnableScheduling
public class OrderTask {

    @Reference
    private CategoryReportService categoryReportService;

//    @Scheduled(cron = "* * * * * ?")
//    public void test(){
//        System.out.println(new Date());
//    }

    @Scheduled(cron = "0 * * * * ?")
    public void createCategoryReportData(){
        System.out.println("生成类目统计数据");
        categoryReportService.createData();
    }

}
