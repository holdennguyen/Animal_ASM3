package funix.prm.animal.model;

import android.graphics.Bitmap;

public class Animal {
    private final Bitmap photo; //hình icon
    private final Bitmap photoBg; //hình dùng trong Detail Fragment
    private final String path; //đường dẫn để mở file icon
    private final String name; // tên con vật
    private final String content; //nội dung trong Detail Fragment
    private boolean isFav; //trạng thái thả tim

    public void setFav(boolean fav) {
        isFav = fav;
    }
    public boolean isFav() {
        return isFav;
    }

    public Animal(String path, String name, Bitmap photo, Bitmap photoBg, String content) {
        this.path = path;
        this.name = name;
        this.photo = photo;
        this.photoBg = photoBg;
        this.content = content;
        this.isFav = isFav;
    }

    public String getPath() {
        return path;
    }
    public Bitmap getPhoto() {
        return photo;
    }
    public Bitmap getPhotoBg() {
        return photoBg;
    }
    public String getContent() {
        return content;
    }
    public String getName() {
        return name;
    }

}
