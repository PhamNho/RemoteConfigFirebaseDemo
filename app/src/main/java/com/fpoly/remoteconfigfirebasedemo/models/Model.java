package com.fpoly.remoteconfigfirebasedemo.models;

public class Model {
    private String btn_text;
    private boolean btn_enable;
    private String image_link;

    public Model() {
    }

    public Model(String btn_text, boolean btn_enable, String image_link) {
        this.btn_text = btn_text;
        this.btn_enable = btn_enable;
        this.image_link = image_link;
    }

    public String getBtn_text() {
        return btn_text;
    }

    public boolean isBtn_enable() {
        return btn_enable;
    }

    public String getImage_link() {
        return image_link;
    }
}
