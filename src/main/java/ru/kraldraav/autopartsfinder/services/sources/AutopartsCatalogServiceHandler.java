package ru.kraldraav.autopartsfinder.services.sources;

import ru.kraldraav.autopartsfinder.models.Autopart;

import java.util.List;

public class AutopartsCatalogServiceHandler {
    private final AutopartsCatalogService autopartsCatalogService;

    public AutopartsCatalogServiceHandler(AutopartsCatalogService autopartsCatalogService) {
        this.autopartsCatalogService = autopartsCatalogService;
    }

    public List<Autopart> getAutopartByArticle(String productArticle) {
        return autopartsCatalogService.getAutopartByArticle(productArticle);
    }
}
