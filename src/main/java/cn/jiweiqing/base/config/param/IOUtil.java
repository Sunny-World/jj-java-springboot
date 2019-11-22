package cn.jiweiqing.base.config.param;

import org.springframework.util.StreamUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.zip.ZipOutputStream;

public class IOUtil {

    public static void closeSilent(Closeable closeable){
        if(closeable!=null){
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String toString(InputStream in) throws IOException {
        try {
            return StreamUtils.copyToString(in, Charset.forName("UTF-8"));
        }finally {
            closeSilent(in);
        }
    }

}
