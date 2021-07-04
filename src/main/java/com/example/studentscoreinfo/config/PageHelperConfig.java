package com.example.studentscoreinfo.config;


import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * 分页插件配置
 * 01397466
 */
@Configuration
public class PageHelperConfig {

    @Bean
    public PageHelper pageHelper(){
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("offsetAsPageNum","true"); // 将rowbounds第一个参数offset当做pageNum页码使用
        p.setProperty("rowBoundsWithCount","true"); // 使用rowBounds分页进行count查询
        p.setProperty("reasonable","true"); // 启用合理化查询 pageNum<1时查询第一页，pageNum>pages时查询最后一页
        pageHelper.setProperties(p);
        return pageHelper;
    }

}
