
package JavaImages;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JTextArea;

/**
 *
 * @author Luis Marcos
 */
 
public class ImageProcessing {

        static private String status;
        static private ArrayList<String> allStatus=new ArrayList<>();
        static private BufferedImage currentImage;
        static private ArrayList<BufferedImage> allImages=new ArrayList<>();;
        static private ArrayList<String> activityLog=new ArrayList<>();
        static private int counterImages=-1;
        static private JTextArea txtArea;
               
    
    private boolean checkingSize(ArrayList arrayChecking){
        boolean flag;
        if (arrayChecking.size()>0){
            flag=true;
        }else{
            flag=false;
        }
        return flag;
    }
    
    /**
     * @return a String that contains the status of the last modification made.
     * If no status stored, returns null
     */
    public String getStatus() {
        if (checkingSize(ImageProcessing.allStatus)){
            ImageProcessing.status=allStatus.get(ImageProcessing.allStatus.size()-1);
            return ImageProcessing.status;
        }else{
            return null;
        }
    }

    /**
     * @return a Arraylist<String> that contains all the states of the modifications
     */
    public ArrayList<String> getAllStatus() {
        return allStatus;
    }

    /**
     * @return a BufferedImage that contains the last image received by the class.
     * If no images stored, returns null
     */
    public BufferedImage getCurrentImage() {
        if (checkingSize(ImageProcessing.allImages)){
        ImageProcessing.currentImage=allImages.get(ImageProcessing.allImages.size()-1);
        return ImageProcessing.currentImage;
         }else{
            return null;
        }
    }

    /**
     * @return a Arraylist<BufferedImage> that contains all the images received by the class
     */
    public ArrayList<BufferedImage> getAllImages() {
        return allImages;
    }
     
        /**
     * @return a Arraylist<String> that contains all states and errors processed
     */
    public ArrayList<String> getActivityLog() {
        return activityLog;
    }
    
    
        /**
     * Method that stores all the information about the program, eg errors.
     * Do not use if you have already called the function JavaImages.ImageProcessing.updateImage()
     * @param message String with the information you want to store
     * @see JavaImages.ImageProcessing#updateImage(java.lang.String, java.awt.image.BufferedImage) 
     */
   protected void updateActivityLog(String message){
        ImageProcessing.activityLog.add(message);
        if (txtArea!=null){
            ImageProcessing.txtArea.setText(ImageProcessing.txtArea.getText()  + message+ "\n");
        }
   }
   
    /**
     * Method that stores information relating to an image processing
     * @param statusImage String that stores information concerning the transformation performed
     * @param currentImage BufferedImage that has been processed
     * @see JavaImages.ImageProcessing#getAllStatus()
     * @see JavaImages.ImageProcessing#getAllImages()
     */
    protected void updateImage(String statusImage,BufferedImage currentImage){
        allStatus.add(statusImage);
        allImages.add(currentImage);
        updateActivityLog(statusImage);
        ImageProcessing.counterImages+=1;
    }
    
    
   /**
    * method that associates a JTextArea with all the information covered in the class, 
    * for example, information about the processing of the images, errors ...
    * @param textAreaAttached JTextArea where display information
    * @see JavaImages.ImageProcessing#getActivityLog()
    * @see JavaImages.ImageProcessing#getAllStatus() 
    */
    public void attachLabelStatus(JTextArea textAreaAttached){
        ImageProcessing.txtArea=textAreaAttached;
    }
    
    /**
     * Function that returns the previous image with respect to the current image
     * @return Previous BufferedImage.
     * If no previous images, return null
     * @see JavaImages.ImageProcessing#redoImage() 
     * @see JavaImages.ImageProcessing#getCurrentImage() 
     * @see JavaImages.ImageProcessing#getAllImages() 
     */
    public BufferedImage undoImage(){
        if((ImageProcessing.counterImages)>0){
            ImageProcessing.counterImages-=1;
            ImageProcessing.currentImage=allImages.get(ImageProcessing.counterImages);
            return currentImage;
        }else{
            return null;
        }
    }
    
      /**
     * Function that returns the next image with respect to the current image
     * @return Next BufferedImage.
     * If no next images return null
     * @see JavaImages.ImageProcessing#undoImage() 
     * @see JavaImages.ImageProcessing#getCurrentImage() 
     * @see JavaImages.ImageProcessing#getAllImages() 
     */
   public BufferedImage redoImage(){
        if((ImageProcessing.counterImages)<ImageProcessing.allImages.size()-1){
            ImageProcessing.counterImages+=1;
            ImageProcessing.currentImage=allImages.get(ImageProcessing.counterImages);
            return currentImage;
        }else{
            return null;
        }
            
    }
    
}

