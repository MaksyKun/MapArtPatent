package net.maksy.mapartpatent.enums;

public enum ConfigValue {
    GUI_TITLE("Gui.Title"),

    GUI_STATE_ACTIVE("Gui.States.Active"),
    GUI_STATE_UNACTIVE("Gui.States.Unactive"),

    BUTTON_CONFIRM_DISPLAY("Gui.Buttons.Confirm.Display"),
    BUTTON_CONFIRM_LORE("Gui.Buttons.Confirm.Lore"),

    BUTTON_INSERT_DISPLAY("Gui.Buttons.Insert.Display"),
    BUTTON_INSERT_LORE("Gui.Buttons.Insert.Lore"),

    BUTTON_CRAFTABILITY_DISPLAY("Gui.Buttons.Craftability.Display"),
    BUTTON_CRAFTABILITY_LORE("Gui.Buttons.Craftability.Lore"),
    BUTTON_CRAFTABILITY_COSTS("Gui.Buttons.Craftability.Costs"),

    BUTTON_USABILITY_DISPLAY("Gui.Buttons.Usability.Display"),
    BUTTON_USABILITY_LORE("Gui.Buttons.Usability.Lore"),
    BUTTON_USABILITY_COSTS("Gui.Buttons.Usability.Costs"),

    BUTTON_EXIT_DISPLAY("Gui.Buttons.Exit.Display"),
    BUTTON_EXIT_LORE("Gui.Buttons.Exit.Lore"),

    MAPART_LORE("MapArt.Lore"),

    LANG_NO_PERMISSION("Language.no_permission"),
    LANG_NO_MAPART("Language.no_mapart"),
    LANG_NOT_ALLOWED("Language.not_allowed"),
    LANG_NOT_ENOUGH_MONEY("Language.not_enough_money"),
    LANG_INVENTORY_FULL("Language.inventory_full"),
    LANG_FINISH("Language.finish");

    private final String path;

    ConfigValue(String path) {
        this.path = path;
    }

    public static String getPath(ConfigValue value) { return value.path; }
}
