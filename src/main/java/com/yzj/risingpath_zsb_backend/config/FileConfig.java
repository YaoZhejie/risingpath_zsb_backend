package com.yzj.risingpath_zsb_backend.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



/**
 * 定位各种文件和头像地址
 */
@Configuration
public class FileConfig implements WebMvcConfigurer {
    /**
     * 添加处理程序来服务静态资源
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //前端用户头像地址
        registry.addResourceHandler("/avatorImages/**") //前端可以访问图片
                .addResourceLocations("file:" + System.getProperty("user.dir")//兼容linux系统
                        + System.getProperty("file.separator") + "avatorImages"
                        + System.getProperty("file.separator"));
    }

}
