package ru.kraldraav.autopartsfinder.services.sources;

import org.springframework.stereotype.Service;
import ru.kraldraav.autopartsfinder.models.Autopart;

import java.util.List;

/**
 * Auto parts catalog interface
 */
@Service
public interface AutopartsCatalogService {
    /**
     * Get autopart by product article
     *
     * @param productArticle autopart vendor article
     * @return autopart object instance
     */
    List<Autopart> getAutopartByArticle(String productArticle);
}
