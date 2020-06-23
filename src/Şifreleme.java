import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
final class ImageEncryption extends JFrame implements ActionListener {
    public ImageEncryption() {

    }

    private static final int RGB_SIZE = 3;

    public static void main(String[] args) {
        try {

            JFrame frame;
            JPanel pane;
            JButton button;
            JLabel label;
            JFileChooser fcho;
            frame= new JFrame("AES Encryption");
            frame.setVisible(true);
            frame.setSize(400,300);
            frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
            pane=new JPanel();
            pane.setBackground(Color.BLACK);
            button = new JButton("Şifrele");
            button.setSize(300,700);
            label=new JLabel();
            pane.add(button);
            pane.add(label);
            frame.add(pane);
            JOptionPane.showMessageDialog(null," Şifrelenecek veriyi seçiniz");
            fcho=new JFileChooser();
            frame.add(fcho);
            fcho.showOpenDialog(null);
            File filecho =fcho.getSelectedFile();
            BufferedImage image;
            int width;
            int height;
            int index=0;

            image = ImageIO.read(filecho); //
            width = image.getWidth();     //-> Orijinal resmin en-boy değerlerini aldım.
            height = image.getHeight();   //

            byte[] byteimage = new byte[width * height * RGB_SIZE*3];   //Java'da int türünde bir dizi resme dönüştürülemiyor.Bu yüzden byte türünde
            int round_number=16;
            int block_number=byteimage.length/256;
            // System.out.println(block_number);


            for (int i = 0; i < height; i++) {

                for (int j = 0; j < width; j++) {

                    Color c = new Color(image.getRGB(j, i));
                    //
                    //-> RGB değerleri alındı.
                    byte r = (byte) c.getRed();                  //
                    byte g = (byte) c.getGreen();
                    byte b = (byte) c.getBlue();
                    byte a= (byte)c.getAlpha();
                    byteimage[index++] = r;                      //
                    byteimage[index++] = g;                      //-> RGB değerleri diziye yerleştirildi
                    byteimage[index++] = b;                      //
                    byteimage[index++]=  a;
                }
            }
            int[]xored = new int[byteimage.length];
            byte[] ivBytes = new byte[256];

            new Random().nextBytes(ivBytes);   // Varsayılan Random metodunu kullandım.Her adımda random IV ürettim farklı olarak.
            for(round_number=0;round_number<2;round_number++) {
                for(int b=0;b<block_number;b++) {

                    for(int c=0;c<=255;c++) {



                        xored[c]=ivBytes[c]^byteimage[c];     // Ürettiğim IV ile 256 bitlik bloğu XOR'ladım.

                        System.out.println(ivBytes[c]);
                    }

                }}
            byte[] encveri= new byte[xored.length];
            for(int i=0;i<xored.length;i++) {
                encveri[i]=(byte)(xored[i]);       //xorlanmış diziyi byte türünde bir diziye dönüştürdüm(image'ı açabilmek için gerekliydi)

            }

            BufferedImage bImage2 = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

            for(int y=0;y<height;y++) {
                for(int x=0;x<width;x++) {

                    int rgb = ((encveri[x] & 0xFF) << 16) |
                            ((encveri[x+1] & 0xFF) <<  8) |
                            ((encveri[x+2] & 0xFF)      )
                            ;
                    bImage2.setRGB(x, y, rgb);
                }
            }

            JOptionPane.showMessageDialog(null," Üzerine yazılacak dosyayı oluşturunuz");
            fcho=new JFileChooser();
            fcho.showOpenDialog(null);
            File filecho2 =fcho.getSelectedFile();
            ImageIO.write(bImage2,"jpg",filecho2);
            System.out.println("Resim Şifrelendi");

        }catch (Exception e) {
            e.printStackTrace();
        }}


    public static int[] permute(int size) {

        int[] array1=new int[size];
        array1[0]=2;
        return array1;
    }







    @Override
    public void actionPerformed(ActionEvent arg0) {

    }




}