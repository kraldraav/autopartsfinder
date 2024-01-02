package ru.kraldraav.autopartsfinder.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.kraldraav.autopartsfinder.services.sources.AutopartsCatalogService;
import ru.kraldraav.autopartsfinder.services.sources.AutopartsCatalogServiceHandler;
import ru.kraldraav.autopartsfinder.services.sources.ExistAutopartCatalogServiceImpl;

@Configuration
public class AppConfig {

    @Bean
    public AutopartsCatalogService autopartsCatalogService() {
        return new ExistAutopartCatalogServiceImpl();
    }

    @Bean
    public AutopartsCatalogServiceHandler autopartsCatalogServiceHandler(AutopartsCatalogService autopartsCatalogService){
        return new AutopartsCatalogServiceHandler(autopartsCatalogService);
    }

}
