/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaImages;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.net.URL;

/**
 *
 * @author Luis Marcos
 */
public class ImageProcessing_OpenImage extends ImageProcessing {
    
    /**
     * Enumeration with the file extensions
     */
    public enum imageFormat{all,all_images,bmp,gif,jpg,png,tiff}
    private BufferedImage openImage;
    private JFileChooser selectorImage;
    private imageFormat selectedFormatImage;
        
    protected boolean openJFileChooser(){
        this.selectorImage=new JFileChooser();
        this.selectorImage.setDialogTitle("Choose an image");
        selectImageFilter();
        int flag=this.selectorImage.showOpenDialog(null);
        if (flag==JFileChooser.APPROVE_OPTION){
            return true;
        }else{
           return false;
        }
    }   
    protected void selectImageFilter(){
        FileNameExtensionFilter imageFilter;
        imageFilter= new FileNameExtensionFilter("All files (*.*)","*.*");
        switch(this.selectedFormatImage){
            case all:
                imageFilter = new FileNameExtensionFilter("All files (*.*)","*.*");
                break;
            case all_images:
                imageFilter = new FileNameExtensionFilter("All image files","bmp","dib","gif",
                        "jpg","jpeg","jpe","jfif","tif","tiff","png");
                break;
            case bmp:
                imageFilter = new FileNameExtensionFilter("Bitmap (*.bmp, *.dib)","bmp","dib");
                break;
            case gif:
                imageFilter = new FileNameExtensionFilter("GIF (*.gif)","gif");
                break;
            case jpg:
                imageFilter = new FileNameExtensionFilter("JPEG (*.jpg,*.jpeg,*.jpe,*.jfif)","jpg","jpeg","jpe","jfif");
                break;
            case tiff:
                imageFilter = new FileNameExtensionFilter("TIFF (*.tif,*tiff)","tif","tiff");
                break;
            case png:
                imageFilter = new FileNameExtensionFilter("PNG (*.png)","png");
                break;   
        }
        this.selectorImage.setFileFilter(imageFilter);
    }
    //TASK comprobar qué pasa si se le envía cadenas sin \\ o vacías (test unitarios tíoooo)
    protected String extractLocalImageName(String imagePath){
        int finalSlash=imagePath.lastIndexOf("\\");
        String nameReturn=imagePath.substring(finalSlash+1);
        return nameReturn;
    }
    //TASK comprobar qué pasa si se le envía cadenas sin \\ o vacías (test unitarios tíoooo)
    public String extractRemoteImageName(String imagePath){
        int finalSlash=imagePath.lastIndexOf("/");
        String nameReturn=imagePath.substring(finalSlash+1);
        return nameReturn;
    }
     
       /**
     * shows the JFileChooser to select an image
     * @param defaultFormat JFileChooser file filter
     * @return BufferedImage of the selected image. If the selected image is
     * incorrect, it returns a null BufferedImage
     * @see JavaImages.ImageProcessing_OpenImage.imageFormat
     */
    public BufferedImage openFile(imageFormat defaultFormat){
        this.selectedFormatImage=defaultFormat;
        this.openImage=null;
        if(this.openJFileChooser()==true){
            File imageFile=this.selectorImage.getSelectedFile();
            try {
                this.openImage = ImageIO.read(imageFile);
                 if (this.openImage!=null){
                super.updateImage("Local image loaded successfully - " + extractLocalImageName(imageFile.toString())
                        + " (" + imageFile.toString() + ")", this.openImage);
                }else{
                super.updateActivityLog("Error: Failed when opening local image");
                }
            } catch (Exception e) {
                super.updateActivityLog("Error: Failed when opening local image. Exception generated:\n" + e.toString());
            }
        }        
        return this.openImage;
    }
    
     /**
     * Upload and return an image, via URL
     * @param urlImage String with the URL of image
     * @return BufferedImage of the selected image. If the URL is
     * incorrect, it returns a null BufferedImage
     */
    public BufferedImage openUrl(String urlImage){
        this.openImage=null;
        try {
            URL url = new URL(urlImage);
            this.openImage = ImageIO.read(url);
            super.updateImage("Remote image loaded successfully - " + extractRemoteImageName(urlImage)
                        + " (" + urlImage + ")", this.openImage);
        } catch (Exception e) {
            super.updateActivityLog("Error: Failed when opening remote image. Exception generated:\n" + e.toString());
        }
        return this.openImage;
    }
}
