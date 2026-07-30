package com.notifi.service;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.notifi.entity.NotificationTemplate;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TemplateRenderService {

    private final Handlebars handlebars;
    private final Cache<UUID, Template> templateCache;

    public TemplateRenderService() {
        this.handlebars = new Handlebars();
        this.templateCache = Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterAccess(24, TimeUnit.HOURS)
                .build();
    }

    public String render(NotificationTemplate notificationTemplate, Map<String, Object> variables) {
        try {
            // Safe fallback for draft/unsaved templates without an ID
            if (notificationTemplate.getId() == null) {
                return handlebars.compileInline(notificationTemplate.getContent()).apply(variables);
            }

            Template compiledTemplate = templateCache.get(notificationTemplate.getId(), key -> {
                try {
                    return handlebars.compileInline(notificationTemplate.getContent());
                } catch (IOException e) {
                    throw new RuntimeException("Failed to compile template", e);
                }
            });

            if (compiledTemplate == null) {
                throw new RuntimeException("Template compilation resulted in null");
            }
            return compiledTemplate.apply(variables);
        } catch (IOException e) {
            throw new RuntimeException("Failed to render template", e);
        }
    }

    /**
     * Evicts a template from the compiled cache when updated or deleted.
     */
    public void evictCache(UUID templateId) {
        templateCache.invalidate(templateId);
    }
}
