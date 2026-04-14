package sk.tuke.gamestudio.game;

public enum TileType {
    PPPP("\uD83C\uDFD4\uFE0F"),
    RRRR("☀\uFE0F"),
    OOOO("\uD83C\uDF35"),
    IIII("  ⛩\uFE0F  "),
    ZZZZ("\uD83C\uDFDB\uFE0F");

    private final String icon;

    TileType(String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }

    @Override
    public String toString() {
        return " " + icon + " ";
    }
}
