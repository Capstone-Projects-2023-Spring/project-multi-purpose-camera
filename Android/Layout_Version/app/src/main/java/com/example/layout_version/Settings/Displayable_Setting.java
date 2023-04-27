package com.example.layout_version.Settings;

import com.example.layout_version.Settings.Attributes.Attribute;

public interface Displayable_Setting {
    public Attribute[] get_edit_attributes();
    public String get_display_text();
    public boolean includes_duplicate(Displayable_Setting setting);
}