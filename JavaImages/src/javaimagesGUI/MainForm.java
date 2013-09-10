/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javaimagesGUI;

import JavaImages.ImageProcessing;
import JavaImages.ImageProcessing_AdvancedFilters;
import JavaImages.ImageProcessing_BasicFilters;
import JavaImages.ImageProcessing_Histogram;
import JavaImages.ImageProcessing_OpenImage;
import JavaImages.ImageProcessing_SaveImage;
import JavaImages.ImageProcessing_TransformFormatImages;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 *
 * @author Luis
 */
public class MainForm extends javax.swing.JFrame {

 private ImageProcessing ObjProcessing;
    private ImageProcessing_OpenImage ObjOpenImage;
    private ImageProcessing_SaveImage ObjSaveImage;
    private ImageProcessing_BasicFilters ObjBasicFilters;
    private ImageProcessing_AdvancedFilters ObjAdvancedFilters;
    private ImageProcessing_TransformFormatImages ObjTransformFormats;
    private ImageProcessing_Histogram ObjHistogram;
    private CreateHistogram ObjBarHistogram;
    private Boolean flagImage;
    private BufferedImage bufferImageTemp;
    
 
    private void DeleteText(JTextField jtextfield){
        jtextfield.setText("");
    }
    
    private void interfaceModifications(){
       this.jSplitPanel1.setDividerLocation(0.85);
       this.jSplitPanel2_1.setDividerLocation(0.8);
       this.jSplitPanel2_1.setResizeWeight(1);
       this.jSplitPanel1.setResizeWeight(1);
    }
    private void enabledPanels(Boolean enabled){
          for(int i=0;i<jTabbedPane1.getComponentCount();i++){
            if(jTabbedPane1.getComponent(i) instanceof JPanel){
                JPanel jpanelTemp=(JPanel)jTabbedPane1.getComponent(i);
                for(int j=0;j<jpanelTemp.getComponentCount();j++){
                  jpanelTemp.getComponent(j).setEnabled(enabled);
                }
            }
          }
          if (enabled==false){
          for(int j=0;j<this.jPanel3.getComponentCount();j++){
                  this.jPanel3.getComponent(j).setEnabled(!enabled);
                }
          }
    }
    private void initializeObjects(){
        ObjProcessing=new ImageProcessing();
        ObjOpenImage=new ImageProcessing_OpenImage();
        ObjSaveImage=new ImageProcessing_SaveImage();
        ObjBasicFilters=new ImageProcessing_BasicFilters();
        ObjAdvancedFilters=new ImageProcessing_AdvancedFilters();
        ObjTransformFormats=new ImageProcessing_TransformFormatImages();
        ObjHistogram=new ImageProcessing_Histogram();
        ObjBarHistogram=new CreateHistogram();
        ObjProcessing.attachTextAreaStatus(jTextArea2);
    }
    
    private void formInitialize(){
        interfaceModifications();
        initializeObjects();
        enabledPanels(false);
        this.flagImage=false;
    }
    
    private void showActivity(Boolean visible){
        if(visible==true){
            this. jSplitPanel1.setDividerLocation(0.85);
        }else{
            this.jSplitPanel1.setDividerLocation(0.9999);
        }
    }
    
    private void openImage(){
        this.bufferImageTemp=ObjOpenImage.openFile(ImageProcessing.imageFormat.all_images);
        if (this.bufferImageTemp!=null){
            this.jLabelImage.setIcon(new ImageIcon(this.bufferImageTemp));
            if (this.flagImage==false){
                this.flagImage=true;
                enabledPanels(true);
            }
        }
    }
    private void openURL(){
        if (!"".equals(this.JTextfield_URL.getText())){
            this.bufferImageTemp=ObjOpenImage.openUrl(this.JTextfield_URL.getText());
            if (bufferImageTemp!=null){
                this.jLabelImage.setIcon(new ImageIcon(this.bufferImageTemp));
                if (flagImage==false){
                this.flagImage=true;
                enabledPanels(true);
                }
            }
        }
    }
    private void saveImage(){
         if (this.jLabelImage.getIcon()!=null){
            ImageProcessing.imageFormat extension;
            switch(this.jComboBox1.getSelectedItem().toString()){
                case "BMP":
                extension= ImageProcessing.imageFormat.bmp;
                break;
                case "GIF":
                extension=ImageProcessing.imageFormat.gif;
                break;
                case "JPG":
                extension=ImageProcessing.imageFormat.jpg;
                break;
                case "PNG":
                extension=ImageProcessing.imageFormat.png;
                break;
                default:
                extension=ImageProcessing.imageFormat.jpg;
                break;
            }
            ObjSaveImage.saveFile((BufferedImage)ObjTransformFormats.iconToImage(jLabelImage.getIcon()),extension);
        }
    }
    
    
    private void undoImage(){
        this.bufferImageTemp=ObjProcessing.undoImage();
        if (this.bufferImageTemp!=null){
            this.jLabelImage.setIcon(new ImageIcon(this.bufferImageTemp));
        }
    }
    private void redoImage(){
        this.bufferImageTemp=ObjProcessing.redoImage();
        if (this.bufferImageTemp!=null){
            this.jLabelImage.setIcon(new ImageIcon(this.bufferImageTemp));
        }
    }
    private void lastOpenedImage(){
        this.bufferImageTemp=ObjProcessing.lastOpenedImage();
        if (this.bufferImageTemp!=null){
            this.jLabelImage.setIcon(new ImageIcon(this.bufferImageTemp));
            this.JTexfield_lastOpened.setText(ObjProcessing.lastInfoOpenedImage());
        }
    }
    private void deleteAllImages(){
        int selected=JOptionPane.showConfirmDialog(null,"Do you want to delete all images? This action can´t be undone ...","Warning",JOptionPane.YES_NO_OPTION);
        if (JOptionPane.YES_OPTION==selected){
            ObjProcessing.deleteAllStoredImages();
        }
    }
    
    
    private void grayScale(){
         this.bufferImageTemp=ObjTransformFormats.iconToBufferedImage(this.jLabelImage.getIcon());
         this.jLabelImage.setIcon(ObjTransformFormats.bufferedImageToImageIcon(ObjBasicFilters.grayScale(bufferImageTemp)));
    }
    private void blackWhite(){
         this.bufferImageTemp=ObjTransformFormats.iconToBufferedImage(this.jLabelImage.getIcon());
         this.jLabelImage.setIcon(ObjTransformFormats.bufferedImageToImageIcon(ObjBasicFilters.blackAndWhite(bufferImageTemp,jSlider_Threshold.getValue())));
    }
    private void invertColors(){
        this.bufferImageTemp=ObjTransformFormats.iconToBufferedImage(this.jLabelImage.getIcon());
        switch(jComboBox_Invert.getSelectedItem().toString()){
            case "RGB":
                this.bufferImageTemp=ObjBasicFilters.invertColorsRGB(this.bufferImageTemp, 
                        ImageProcessing_BasicFilters.invertColorsAvailable.RGB);
                break;
            case "RED":
                this.bufferImageTemp=ObjBasicFilters.invertColorsRGB(this.bufferImageTemp, 
                        ImageProcessing_BasicFilters.invertColorsAvailable.red);                
                break;
            case "GREEN":
                this.bufferImageTemp=ObjBasicFilters.invertColorsRGB(this.bufferImageTemp, 
                        ImageProcessing_BasicFilters.invertColorsAvailable.green);                
                break;
            case "BLUE":
                this.bufferImageTemp=ObjBasicFilters.invertColorsRGB(this.bufferImageTemp, 
                        ImageProcessing_BasicFilters.invertColorsAvailable.blue);                
                break;
        }
        this.jLabelImage.setIcon(ObjTransformFormats.bufferedImageToImageIcon(this.bufferImageTemp));
   }
    private void basicFilters(){
        this.bufferImageTemp=ObjTransformFormats.iconToBufferedImage(this.jLabelImage.getIcon());
        switch(jComboBox_BasicFilters.getSelectedItem().toString()){
            case "RED":
                this.bufferImageTemp=ObjBasicFilters.basicFilters(this.bufferImageTemp, 
                        ImageProcessing_BasicFilters.filtersAvailable.red);
                break;
            case "GREEN":
                this.bufferImageTemp=ObjBasicFilters.basicFilters(this.bufferImageTemp,
                        ImageProcessing_BasicFilters.filtersAvailable.green);
                break;
            case "BLUE":
                this.bufferImageTemp=ObjBasicFilters.basicFilters(this.bufferImageTemp, 
                        ImageProcessing_BasicFilters.filtersAvailable.blue);
                break;
            }
        this.jLabelImage.setIcon(ObjTransformFormats.bufferedImageToImageIcon(this.bufferImageTemp));
    }
    private void RGBto(){
        this.bufferImageTemp=ObjTransformFormats.iconToBufferedImage(this.jLabelImage.getIcon());
            switch(jComboBox_RGB.getSelectedItem().toString()){
                case "GBR":
                this.bufferImageTemp=ObjBasicFilters.RGBto(this.bufferImageTemp,
                        ImageProcessing_BasicFilters.RGBTransformAvailable.GBR);
                break;
                case "GRB":
                this.bufferImageTemp=ObjBasicFilters.RGBto(this.bufferImageTemp, 
                        ImageProcessing_BasicFilters.RGBTransformAvailable.GRB);
                break;
                case "BRG":
                this.bufferImageTemp=ObjBasicFilters.RGBto(this.bufferImageTemp, 
                        ImageProcessing_BasicFilters.RGBTransformAvailable.BRG);
                break;
                case "BGR":
                this.bufferImageTemp=ObjBasicFilters.RGBto(this.bufferImageTemp, 
                        ImageProcessing_BasicFilters.RGBTransformAvailable.BGR);
                break;
                case "RBG":
                this.bufferImageTemp=ObjBasicFilters.RGBto(this.bufferImageTemp, 
                        ImageProcessing_BasicFilters.RGBTransformAvailable.RBG);
                break;
            }
        this.jLabelImage.setIcon(ObjTransformFormats.bufferedImageToImageIcon(this.bufferImageTemp));   
   }
   
    private void blackWhiteSmoothing(){
         this.bufferImageTemp=ObjTransformFormats.iconToBufferedImage(this.jLabelImage.getIcon());
         this.jLabelImage.setIcon(ObjTransformFormats.bufferedImageToImageIcon(ObjAdvancedFilters.BlackAndWhiteSmoothing(
                 this.bufferImageTemp,this.jSlider_ThresholdSmoothing.getValue(),this.jSlider_Range.getValue())));
    }
   
    
     private void showHistogram(){
        int[] histogram;
        CreateHistogram.availableChannel histogramColor;
        switch(this.jComboBoxHistogram.getSelectedItem().toString()){
            case "RED":
                    histogram=ObjHistogram.histogramRed(ObjTransformFormats.iconToBufferedImage(this.jLabelImage.getIcon()));
                    histogramColor= CreateHistogram.availableChannel.red;
                    break;
            case "GREEN":
                    histogram=ObjHistogram.histogramGreen(ObjTransformFormats.iconToBufferedImage(this.jLabelImage.getIcon()));
                    histogramColor= CreateHistogram.availableChannel.green;
                    break;
            case "BLUE":
                    histogram=ObjHistogram.histogramBlue(ObjTransformFormats.iconToBufferedImage(this.jLabelImage.getIcon()));
                    histogramColor= CreateHistogram.availableChannel.blue;
                    break;
            case "ALPHA":
                    histogram=ObjHistogram.histogramAlpha(ObjTransformFormats.iconToBufferedImage(this.jLabelImage.getIcon()));
                    histogramColor= CreateHistogram.availableChannel.alpha;
                    break;
            case "GRAYSCALE":
                    histogram=ObjHistogram.histogramGrayscale(ObjTransformFormats.iconToBufferedImage(this.jLabelImage.getIcon()));
                    histogramColor= CreateHistogram.availableChannel.grayscale;
                    break;
            default:
                    histogram=ObjHistogram.histogramGrayscale(ObjTransformFormats.iconToBufferedImage(this.jLabelImage.getIcon()));
                    histogramColor= CreateHistogram.availableChannel.grayscale;
                    break;
        }
        ObjBarHistogram.createHistogramBarChart(histogram, jPanelHistogram, histogramColor);
        this.jLabel_MaxHistogram.setText("Max value "+ ObjHistogram.getMaxValue()[0] + ", with " + ObjHistogram.getMaxValue()[1] + " pixels");
        this.jLabel_MinHistogram.setText("Min value "+ ObjHistogram.getMinValue()[0] + ", with " + ObjHistogram.getMinValue()[1] + " pixels");
    }
     
    
    public MainForm() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jSplitPanel1 = new javax.swing.JSplitPane();
        jSplitPanel2_1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jLabelImage = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jButton12 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox();
        jSeparator2 = new javax.swing.JSeparator();
        JTextfield_URL = new javax.swing.JTextField();
        jButton11 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        JTexfield_lastOpened = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jButton13 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jLabel_Threshold = new javax.swing.JLabel();
        jSlider_Threshold = new javax.swing.JSlider();
        jButton16 = new javax.swing.JButton();
        jComboBox_Invert = new javax.swing.JComboBox();
        jComboBox_BasicFilters = new javax.swing.JComboBox();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jComboBox_RGB = new javax.swing.JComboBox();
        jPanel6 = new javax.swing.JPanel();
        jButton19 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jSlider_ThresholdSmoothing = new javax.swing.JSlider();
        jSlider_Range = new javax.swing.JSlider();
        jLabel_Range = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jButton20 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jButton21 = new javax.swing.JButton();
        jComboBoxHistogram = new javax.swing.JComboBox();
        jPanelHistogram = new javax.swing.JPanel();
        jLabel_MaxHistogram = new javax.swing.JLabel();
        jLabel_MinHistogram = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        addWindowStateListener(new java.awt.event.WindowStateListener() {
            public void windowStateChanged(java.awt.event.WindowEvent evt) {
                formWindowStateChanged(evt);
            }
        });

        jToolBar1.setRollover(true);

        jButton1.setText("Undo");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        jButton5.setText("Redo");
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton5);

        jButton6.setText("Last opened image");
        jButton6.setFocusable(false);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton6);

        jSplitPanel1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPanel1.setMinimumSize(new java.awt.Dimension(500, 500));

        jLabelImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jScrollPane1.setViewportView(jLabelImage);

        jSplitPanel2_1.setLeftComponent(jScrollPane1);

        jButton3.setText("jButton3");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jSplitPanel2_1.setRightComponent(jButton3);

        jButton12.setText("Save image");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "BMP", "GIF", "JPG", "PNG", "NOTHING" }));

        JTextfield_URL.setText("http://");
        JTextfield_URL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JTextfield_URLMouseClicked(evt);
            }
        });

        jButton11.setText("Open URL");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton10.setText("Open local");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(JTextfield_URL))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, 857, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator2))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton12, javax.swing.GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, 0, 383, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JTextfield_URL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton12)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(139, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Open/Save image", jPanel3);

        jButton7.setText("Undo image");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setText("Redo image");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setText("Last opened image");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton13.setText("Delete all stored images");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(JTexfield_lastOpened)
                    .addComponent(jSeparator1)
                    .addComponent(jButton13, javax.swing.GroupLayout.DEFAULT_SIZE, 857, Short.MAX_VALUE)
                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JTexfield_lastOpened, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton13)
                .addContainerGap(110, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Main", jPanel4);

        jButton14.setText("Grayscale");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jButton15.setText("Black and white");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jLabel_Threshold.setText("Threshold 128");

        jSlider_Threshold.setMaximum(255);

        jButton16.setText("Invert colors");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jComboBox_Invert.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "RGB", "RED", "GREEN", "BLUE" }));

        jComboBox_BasicFilters.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "RED", "GREEN", "BLUE" }));

        jButton17.setText("Filter");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jButton18.setText("RGB to");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        jComboBox_RGB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "GBR", "GRB", "BRG", "BGR", "RBG" }));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSlider_Threshold, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
                            .addComponent(jButton18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox_BasicFilters, 0, 408, Short.MAX_VALUE)
                            .addComponent(jComboBox_RGB, 0, 1, Short.MAX_VALUE)
                            .addComponent(jComboBox_Invert, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jButton15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel_Threshold)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton14)
                .addGap(4, 4, 4)
                .addComponent(jButton15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel_Threshold)
                .addGap(3, 3, 3)
                .addComponent(jSlider_Threshold, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox_Invert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox_BasicFilters, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton17))
                .addGap(7, 7, 7)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton18)
                    .addComponent(jComboBox_RGB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(75, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Basic filters", jPanel5);

        jButton19.setText("Black and white smoothing");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        jLabel3.setText("Threshold 128");

        jSlider_ThresholdSmoothing.setMaximum(255);
        jSlider_ThresholdSmoothing.setValue(128);
        jSlider_ThresholdSmoothing.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider_ThresholdSmoothingStateChanged(evt);
            }
        });

        jSlider_Range.setMaximum(127);
        jSlider_Range.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider_RangeStateChanged(evt);
            }
        });

        jLabel_Range.setText("Range 50");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jSlider_ThresholdSmoothing, javax.swing.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSlider_Range, javax.swing.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel_Range)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_Range)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSlider_ThresholdSmoothing, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSlider_Range, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(202, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Advanced filters", jPanel6);

        jButton20.setText("Show activity");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton20, javax.swing.GroupLayout.DEFAULT_SIZE, 857, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton20)
                .addContainerGap(251, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Window", jPanel7);

        jButton21.setText("Show histogram");
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });

        jComboBoxHistogram.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "RED", "GREEN", "BLUE", "ALPHA", "GRAYSCALE" }));

        javax.swing.GroupLayout jPanelHistogramLayout = new javax.swing.GroupLayout(jPanelHistogram);
        jPanelHistogram.setLayout(jPanelHistogramLayout);
        jPanelHistogramLayout.setHorizontalGroup(
            jPanelHistogramLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanelHistogramLayout.setVerticalGroup(
            jPanelHistogramLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 222, Short.MAX_VALUE)
        );

        jLabel_MaxHistogram.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel_MinHistogram.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel_MinHistogram, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelHistogram, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton21, javax.swing.GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxHistogram, 0, 422, Short.MAX_VALUE))
                    .addComponent(jLabel_MaxHistogram, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton21)
                    .addComponent(jComboBoxHistogram, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelHistogram, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel_MinHistogram)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel_MaxHistogram)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Histogram", jPanel1);

        jSplitPanel2_1.setRightComponent(jTabbedPane1);

        jSplitPanel1.setTopComponent(jSplitPanel2_1);

        jTextArea2.setBackground(new java.awt.Color(0, 0, 0));
        jTextArea2.setColumns(20);
        jTextArea2.setForeground(new java.awt.Color(0, 255, 0));
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        jButton2.setText("Hide");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setText("Show all");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Show activity");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 696, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE))
        );

        jSplitPanel1.setRightComponent(jPanel8);

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSplitPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        formInitialize();
    }//GEN-LAST:event_formWindowOpened

    private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged
       
    }//GEN-LAST:event_formWindowStateChanged

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        showActivity(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        saveImage();
    }//GEN-LAST:event_jButton12ActionPerformed

    private void JTextfield_URLMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JTextfield_URLMouseClicked
        DeleteText(JTextfield_URL);
    }//GEN-LAST:event_JTextfield_URLMouseClicked

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        openURL();
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        openImage();
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        showActivity(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        undoImage();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        redoImage();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        lastOpenedImage();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        deleteAllImages();
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        grayScale();
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        blackWhite();
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        invertColors();
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        basicFilters();
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        RGBto();
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        blackWhiteSmoothing();
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jSlider_ThresholdSmoothingStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider_ThresholdSmoothingStateChanged

    }//GEN-LAST:event_jSlider_ThresholdSmoothingStateChanged

    private void jSlider_RangeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider_RangeStateChanged

    }//GEN-LAST:event_jSlider_RangeStateChanged

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        showActivity(true);
    }//GEN-LAST:event_jButton20ActionPerformed
   
    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        showHistogram();
    }//GEN-LAST:event_jButton21ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       undoImage();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        redoImage();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        lastOpenedImage();
    }//GEN-LAST:event_jButton6ActionPerformed
  
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainForm().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField JTexfield_lastOpened;
    private javax.swing.JTextField JTextfield_URL;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBoxHistogram;
    private javax.swing.JComboBox jComboBox_BasicFilters;
    private javax.swing.JComboBox jComboBox_Invert;
    private javax.swing.JComboBox jComboBox_RGB;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelImage;
    private javax.swing.JLabel jLabel_MaxHistogram;
    private javax.swing.JLabel jLabel_MinHistogram;
    private javax.swing.JLabel jLabel_Range;
    private javax.swing.JLabel jLabel_Threshold;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanelHistogram;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSlider jSlider_Range;
    private javax.swing.JSlider jSlider_Threshold;
    private javax.swing.JSlider jSlider_ThresholdSmoothing;
    private javax.swing.JSplitPane jSplitPanel1;
    private javax.swing.JSplitPane jSplitPanel2_1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
