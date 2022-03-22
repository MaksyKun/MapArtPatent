package net.maksy.mapartpatent;

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
    BUTTON_EXIT_LORE("Gui.Buttons.Exit.Lore");

    private final String path;

    ConfigValue(String path) {
        this.path = path;
    }

    public static String getPath(ConfigValue value) { return value.path; }
}
