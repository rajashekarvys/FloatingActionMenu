package tinny.com.fabmenulib;

/**
 * Created by Rajashekar on 21/08/17.
 */

public class ImagesPojo
{
    private int drawable;
    private String name;
    private int fabItemBackgroundColor;
    private int itemTextColor;

    public ImagesPojo(int drawable, String name) {
        this.drawable = drawable;
        this.name = name;
    }

    public ImagesPojo(int drawable) {
        this.drawable = drawable;
    }

    public ImagesPojo(int drawable,int color) {
        this.drawable = drawable;
        this.fabItemBackgroundColor = color;
    }

    public ImagesPojo(int drawable,String name, int fabItemBackgroundColor) {
        this.drawable = drawable;
        this.name = name;
        this.fabItemBackgroundColor = fabItemBackgroundColor;
    }

    public ImagesPojo(int drawable,String name, int fabItemBackgroundColor, int itemTextColor) {
        this.drawable = drawable;
        this.name = name;
        this.fabItemBackgroundColor = fabItemBackgroundColor;
        this.itemTextColor = itemTextColor;
    }

    public int getDrawable() {
        return drawable;
    }

    public String getName() {
        return name;
    }

    public int getFabItemBackgroundColor() {
        return fabItemBackgroundColor;
    }

    public int getItemTextColor() {
        return itemTextColor;
    }

}
