package com.whereisinput.birthday_bot.domain;

import java.util.List;

import lombok.Data;

/**
 * Created by yury_paliakevich at 4/25/22
 */
@Data
public class AdventureConfigAction {

    private static final String IMAGE_FOLDER = "img/";
    private static final String AUDIO_FOLDER = "audio/";

    private String imageUrl;
    private String audioUrl;
    private String key;
    private String text;
    private List<AdventureConfigButton> buttons;

    public void setImageUrl(String imageUrl) {
        this.imageUrl = IMAGE_FOLDER + imageUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = AUDIO_FOLDER + audioUrl;
    }

}
