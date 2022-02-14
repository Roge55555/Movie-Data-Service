package com.example.je;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

public class Utils {

    public static void saveImage(String url, String name) {
        try {

            BufferedImage bufferedImage = ImageIO.read(new URL(url));

            ImageIO.write(bufferedImage, "jpg", new File("C:\\Users\\roge5\\Desktop\\img\\" + name + ".jpg"));

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
