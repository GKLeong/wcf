package com.wcf.server.base.attachment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AttachmentConfig implements WebMvcConfigurer {

    @Value("${spring.servlet.attachment.path}")
    private String attachmentPath;
    @Value("${spring.servlet.attachment.url}")
    private String attachmentUrl;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(attachmentUrl + "**").addResourceLocations("file:" + attachmentPath);
    }

}
