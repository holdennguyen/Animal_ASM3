package funix.prm.animal.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import funix.prm.animal.R;
import funix.prm.animal.fragment.MenuFragment;
import funix.prm.animal.model.Animal;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Đặt tên cho file xml trong Share Preferences
    public static final String SAVE_PREF_HEART_FLAG = "heart_flag"; //heart_flag.xml
    public static final String SAVE_PREF_DIALOG_PHONE_NUMBER = "phone_number"; //phone_number.xml

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showMenu();
        //yêu cầu cấp quyền trong Manifest
        if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_CALL_LOG,
            }, 101);
        }
    }

    //mở MenuFragment
    public void showMenu() {
        MenuFragment menuFragment = new MenuFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.ln_main, menuFragment, null).commit();
        menuFragment.setListAnimals(listAnimals()); //Load thông tin các đối tượng để xử lý bên MenuFragment
    }

    //Lấy thông tin các đối tượng Animal từ assets
    public ArrayList<Animal> listAnimals() {
        ArrayList<Animal> listAnimals = new ArrayList<>();
        int[] animalGroup = {R.string.group_bird, R.string.group_mammal, R.string.group_sea};
        String[] files;
        try {
            for (int id : animalGroup) {
                String folder = getString(id);
                //Duyệt qua tất cả các file trong folder và gán tất cả tên file vào String[] files
                files = getApplicationContext().getAssets().list(folder);
                for (String fileName : files) {
                    //tên file có cấu trúc là ic_[name].png
                    //Vì vậy  sẽ lấy subString từ index 3 đến index của phần tử dấu .name
                    String name = fileName.substring(3, fileName.indexOf("."));
                    //tạo path đến vị trí lưu file
                    String path = folder + "/" + fileName;
                    //input file photo
                    Bitmap photo = BitmapFactory.decodeStream(getApplicationContext().getAssets().open(path));
                    //input file photoBg
                    String pathPhotoBg = "detail/photo/" + name + ".jpg";
                    Bitmap photoBg = BitmapFactory.decodeStream(getApplicationContext().getAssets().open(pathPhotoBg));
                    //input detail content từ file .txt trong thư mục assets/detail/text/...
                    InputStream input = getApplicationContext().getAssets().open("detail/text/" + name + ".txt");
                    BufferedReader br = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
                    StringBuilder content = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        content.append(line).append("\n");
                    }
                    //thêm đối tượng vào listAnimals
                    listAnimals.add(new Animal(path, name, photo, photoBg, content.toString()));
                    br.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listAnimals;
    }

}