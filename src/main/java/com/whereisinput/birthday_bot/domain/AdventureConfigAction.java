package com.whereisinput.birthday_bot.domain;

import java.util.List;

import lombok.Data;

/**
 * Created by yury_paliakevich at 4/25/22
 */
@Data
public class AdventureConfigAction {

    private String imageUrl;
    private String audioUrl;
    private String key;
    private String text;
    private List<AdventureConfigButton> buttons;

}
