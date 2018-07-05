package io.ermdev.papershelf.psimg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourceConfig implements WebMvcConfigurer {

    private PsImgApplication.ApplicationProperty property;

    @Autowired
    public ResourceConfig(PsImgApplication.ApplicationProperty property) {
        this.property = property;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("file:" + property.getBookDirectory(), "file:" + property.getCoverDirectory());
    }
}
