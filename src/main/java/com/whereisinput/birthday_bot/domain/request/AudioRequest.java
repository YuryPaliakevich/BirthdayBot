package com.whereisinput.birthday_bot.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by yuriy_polyakevich at 4/17/22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AudioRequest {

    private byte[] audio;

}
