import com.ace.util.ImageBaser64;

/**
 * @author bamboo
 */
public class ImageBaser64Test {
    public static void main(String[] args) {
        String s = ImageBaser64.encoder("/home/bamboo/1.png");
        System.out.println(s);
        ImageBaser64.decoder(s, "/home/bamboo/2.png");
    }
}
