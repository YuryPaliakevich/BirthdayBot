package com.whereisinput.birthday_bot.config;

import java.util.List;
import java.util.Objects;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.whereisinput.birthday_bot.domain.AdventureConfigAction;

import lombok.Data;

/**
 * Created by yury_paliakevich at 4/25/22
 */
@ConfigurationProperties(prefix = "adventure.action")
@Component
@Data
public final class AdventureConfigurationProperties {
    private final List<AdventureConfigAction> data;

    public AdventureConfigAction getAdventureConfigActionByKey(final String key) {
        return data.stream().filter(adventureConfigAction -> adventureConfigAction.getKey().equals(key)).findAny()
                .orElse(null);
    }
}
