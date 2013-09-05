package JavaImages;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author Luis
 */
public class ImageProcessing_TransformFormatImages extends ImageProcessing{
//          

    /**
     * Converts a BufferedImage in a Image
     * @param bufferImage BufferedImage is going to be transformed
     * @return returns an Image from a BufferedImage
     */
    public Image bufferedImageToImage(BufferedImage bufferImage){
        Image imgReturn = (Image)bufferImage;
        return imgReturn;
    }
    
     /**
     * Converts a BufferedImage in a ImageIcon
     * @param bufferImage BufferedImage is going to be transformed
     * @return returns an ImageIcon from a BufferedImage
     */
    public ImageIcon bufferedImageToImageIcon(BufferedImage bufferImage){
        ImageIcon imgIconReturn = new ImageIcon(bufferImage);
        return imgIconReturn;
    }

     /**
     * Converts a BufferedImage in a Icon
     * @param bufferImage BufferedImage is going to be transformed
     * @return returns an Icon from a BufferedImage
     */
    public Icon bufferedImageToIcon(BufferedImage bufferImage){
        ImageIcon imgIcon=new ImageIcon(bufferImage);
        Icon iconReturn = (Icon)(imgIcon);
        return iconReturn;
    }
    
     /**
      * Converts a Icon in a BufferedImage (source: http://stackoverflow.com/questions/5830533/how-can-i-convert-an-icon-to-an-image)
      * @param icon Icon is going to be transformed
      * @return returns an BufferedImage from a Icon
      */
    public BufferedImage iconToBufferedImage(Icon icon){
      BufferedImage bufferImage=(BufferedImage)this.iconToImage(icon);
      return bufferImage;
    }
    
      /**
      * Converts a Icon in a Image https://groups.google.com/forum/#!topic/comp.lang.java.programmer/OI_IdebPL68
      * @param icon Icon is going to be transformed
      * @return returns an Image from a Icon
      */
    public Image iconToImage(Icon icon) {
          if (icon instanceof ImageIcon) {
              return ((ImageIcon)icon).getImage();
          } else {
              int w = icon.getIconWidth();
              int h = icon.getIconHeight();
              GraphicsEnvironment ge = 
                GraphicsEnvironment.getLocalGraphicsEnvironment();
              GraphicsDevice gd = ge.getDefaultScreenDevice();
              GraphicsConfiguration gc = gd.getDefaultConfiguration();
              BufferedImage image = gc.createCompatibleImage(w, h);
              Graphics2D g = image.createGraphics();
              icon.paintIcon(null, g, 0, 0);
              g.dispose();
              return image;
          }
      }
    
    /**
    * Converts a Icon in a ImageIcon
    * @param icon Icon is going to be transformed
    * @return returns an ImageIcon from a Icon
    */
    public ImageIcon iconToImageIcon(Icon icon){
        ImageIcon imageIconRetur=new ImageIcon(this.iconToImage(icon));
        return imageIconRetur;
    }
    
    /**
     * Converts a Image in a BufferedImage
     * @param image Image is going to be transformed
     * @return returns an BufferedImage from a Image
     */
    public BufferedImage imageToBufferedImage(Image image){
        BufferedImage bufferReturn=(BufferedImage)image;
        return bufferReturn;
    }
    
    /**
    * Converts a Image in a ImageIcon
    * @param image Image is going to be transformed
    * @return returns an ImageIcon from a Image
    */
    public ImageIcon imageToImageIcon(Image image){
        ImageIcon imageIconReturn=new ImageIcon(image);
        return imageIconReturn;
    }

    /**
    * Converts a Image in a Icon
    * @param image Image is going to be transformed
    * @return returns an Icon from a Image
    */
    public Icon imageToIcon(Image image){
        ImageIcon imgIcon=new ImageIcon(image);
        Icon iconReturn=(Icon)imgIcon;
        return iconReturn;
    }
    
    /**
     * Converts a ImageIcon in a BufferedImage
     * @param imageIcon ImageIcon is going to be transformed
     * @return returns an BufferedImage from a ImageIcon
     */
    public BufferedImage imageIconToBufferedImage(ImageIcon imageIcon){
        BufferedImage bufferReturn=(BufferedImage)((Image)imageIcon.getImage());
        return bufferReturn;
    }
    
     /**
     * Converts a ImageIcon in a Image
     * @param imageIcon ImageIcon is going to be transformed
     * @return returns an Image from a ImageIcon
     */
    public Image imageIconToImage(ImageIcon imageIcon){
        Image imgReturn=(Image)imageIcon.getImage();
        return imgReturn;
    }
    
      /**
     * Converts a ImageIcon in a Icon
     * @param imageIcon ImageIcon is going to be transformed
     * @return returns an Icon from a ImageIcon
     */
    public Icon imageIconToIcon(ImageIcon imageIcon){
        Icon iconReturn=(Icon)imageIcon;
        return iconReturn;
    }
}
