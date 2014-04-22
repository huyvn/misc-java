import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Unzip {
    public static void main(String args[]){
        new Unzip().start();
    }

    public void start()
    {
        try{
            byte[] data = new byte[1000];
            int byteRead;
            BufferedOutputStream bout = null;
            ZipInputStream zin = new ZipInputStream(new BufferedInputStream(new FileInputStream("data-V1.zip")));
            ZipEntry entry;
            while((entry = zin.getNextEntry()) != null){
                byteRead=0;
                data = new byte[1000];
                bout = new BufferedOutputStream(new FileOutputStream(entry.getName()),1000);
                while ((byteRead = zin.read(data,0,1000)) != -1){
                    bout.write(data,0,byteRead);
                }
                bout.flush();
                bout.close();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}