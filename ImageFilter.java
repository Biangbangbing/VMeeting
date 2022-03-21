package net.by0119;

import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.util.Locale;

public class ImageFilter extends FileFilter {
    public final static String JPEG = "jpeg";
    public final static String JPG = "jpg";
    public final static String GIF = "gif";
    public final static String TIFF = "tiff";
    public final static String TIF = "tif";
    public final static String PNG = "png";

    public boolean accept(File f) {
        if (f.isDirectory())
            return true;
        String extension = getExtension(f);
        if (extension != null && (extension.equals(JPEG) || extension.equals(JPG) || extension.equals(GIF) || extension.equals(TIF) || extension.equals(TIFF) || extension.equals(PNG))) {
            return true;
        }
        return false;
    }

    public String getExtension(File f) {
        String fileName = f.getName();
        int i = fileName.lastIndexOf('.');
        String ext = fileName.substring(i + 1);
        ext.toLowerCase();
        return ext;
    }

    public String getDescription() {
        return "Image File";
    }




}
