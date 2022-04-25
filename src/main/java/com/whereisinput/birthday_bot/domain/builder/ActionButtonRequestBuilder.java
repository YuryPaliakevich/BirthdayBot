package com.whereisinput.birthday_bot.domain.builder;

import com.whereisinput.birthday_bot.domain.request.ActionButtonRequest;

/**
 * Created by yuriy_polyakevich at 4/17/22
 */

public class ActionButtonRequestBuilder {

    private String name;
    private String callback;

    public ActionButtonRequestBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ActionButtonRequestBuilder withCallback(String name) {
        this.name = name;
        return this;
    }

    public ActionButtonRequest build() {
        return new ActionButtonRequest(name, callback);
    }

}
