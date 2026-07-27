package com.notifi.service;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.notifi.entity.NotificationTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TemplateRenderService {

    private final Handlebars handlebars;
    private final Map<UUID, Template> templateCache;

    public TemplateRenderService() {
        this.handlebars = new Handlebars();
        this.templateCache = new ConcurrentHashMap<>();
    }

    public String render(NotificationTemplate notificationTemplate, Map<String, Object> variables) {
        try {
            // Safe fallback for draft/unsaved templates without an ID
            if (notificationTemplate.getId() == null) {
                return handlebars.compileInline(notificationTemplate.getContent()).apply(variables);
            }

            Template template = templateCache.computeIfAbsent(notificationTemplate.getId(), id -> {
                try {
                    return handlebars.compileInline(notificationTemplate.getContent());
                } catch (IOException e) {
                    throw new RuntimeException("Failed to compile template inline", e);
                }
            });
            return template.apply(variables);
        } catch (IOException e) {
            throw new RuntimeException("Failed to render template", e);
        }
    }

    /**
     * Evicts a template from the compiled cache when updated or deleted.
     */
    public void evictCache(UUID templateId) {
        if (templateId != null) {
            templateCache.remove(templateId);
        }
    }
}
