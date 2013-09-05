/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javaimagesGUI;

import JavaImages.ImageProcessing;
import JavaImages.ImageProcessing_AdvancedFilters;
import JavaImages.ImageProcessing_BasicFilters;
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
 * @author Luis Marcos
 */
public class MainFormGUI extends javax.swing.JFrame {

    private ImageProcessing ObjProcessing;
    private ImageProcessing_OpenImage ObjOpenImage;
    private ImageProcessing_SaveImage ObjSaveImage;
    private ImageProcessing_BasicFilters ObjBasicFilters;
    private ImageProcessing_AdvancedFilters ObjAdvancedFilters;
    private ImageProcessing_TransformFormatImages ObjTransformFormats;
    private Boolean flagImage;
    private BufferedImage bufferImageTemp;
    
    
    private void DeleteText(JTextField jtextfield){
        jtextfield.setText("");
    }
    
    
    
    
    private void interfaceModifications(){
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.jSplitPane1.setDividerLocation(0.9);
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
        ObjProcessing.attachTextAreaStatus(jTextArea2);
    }
    private void formInitialize(){
        interfaceModifications();
        initializeObjects();
        enabledPanels(false);
        this.flagImage=false;
    }
    
    private void showActivity(Boolean visible){
        this.jPanel_Activity.setVisible(visible);
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
   
    public MainFormGUI() {
        initComponents();
        this.formInitialize();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel_Activity = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jButton5 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        JTextfield_URL = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jButton12 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox();
        jPanel4 = new javax.swing.JPanel();
        jButton9 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        JTexfield_lastOpened = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jComboBox_BasicFilters = new javax.swing.JComboBox();
        jComboBox_RGB = new javax.swing.JComboBox();
        jButton16 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jComboBox_Invert = new javax.swing.JComboBox();
        jButton14 = new javax.swing.JButton();
        jSlider_Threshold = new javax.swing.JSlider();
        jLabel_Threshold = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jButton18 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jSlider_ThresholdSmoothing = new javax.swing.JSlider();
        jSlider_Range = new javax.swing.JSlider();
        jLabel_Range = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jLabelImage = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

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

        jButton3.setText("Redo");
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton3);

        jButton4.setText("Last opened image");
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton4);

        jPanel_Activity.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTextArea2.setBackground(new java.awt.Color(0, 0, 0));
        jTextArea2.setColumns(20);
        jTextArea2.setForeground(new java.awt.Color(0, 255, 0));
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        jButton5.setText("Hide");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Show activity");

        javax.swing.GroupLayout jPanel_ActivityLayout = new javax.swing.GroupLayout(jPanel_Activity);
        jPanel_Activity.setLayout(jPanel_ActivityLayout);
        jPanel_ActivityLayout.setHorizontalGroup(
            jPanel_ActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addGroup(jPanel_ActivityLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton5))
        );
        jPanel_ActivityLayout.setVerticalGroup(
            jPanel_ActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_ActivityLayout.createSequentialGroup()
                .addGroup(jPanel_ActivityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE))
        );

        jButton10.setText("Open local");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setText("Open URL");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        JTextfield_URL.setText("http://");
        JTextfield_URL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JTextfield_URLMouseClicked(evt);
            }
        });

        jButton12.setText("Save image");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "BMP", "GIF", "JPG", "PNG", "NOTHING" }));

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
                        .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, 888, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator2))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton12, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, 0, 399, Short.MAX_VALUE)))
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
                .addContainerGap(184, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Open/Save image", jPanel3);

        jButton9.setText("Delete all stored images");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton8.setText("Last opened image");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton7.setText("Redo image");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton6.setText("Undo image");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(JTexfield_lastOpened)
                    .addComponent(jSeparator1)
                    .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, 888, Short.MAX_VALUE)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JTexfield_lastOpened, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton9)
                .addContainerGap(155, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Main", jPanel4);

        jComboBox_BasicFilters.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "RED", "GREEN", "BLUE" }));

        jComboBox_RGB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "GBR", "GRB", "BRG", "BGR", "RBG" }));

        jButton16.setText("Filter");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jButton15.setText("Invert colors");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jButton13.setText("Grayscale");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton17.setText("RGB to");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jComboBox_Invert.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "RGB", "RED", "GREEN", "BLUE" }));

        jButton14.setText("Black and white");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jSlider_Threshold.setMaximum(255);

        jLabel_Threshold.setText("Threshold 128");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSlider_Threshold, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)
                            .addComponent(jButton17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox_BasicFilters, 0, 424, Short.MAX_VALUE)
                            .addComponent(jComboBox_RGB, 0, 1, Short.MAX_VALUE)
                            .addComponent(jComboBox_Invert, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jButton14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel_Threshold)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton13)
                .addGap(4, 4, 4)
                .addComponent(jButton14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel_Threshold)
                .addGap(3, 3, 3)
                .addComponent(jSlider_Threshold, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox_Invert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox_BasicFilters, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton16))
                .addGap(7, 7, 7)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton17)
                    .addComponent(jComboBox_RGB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(120, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Basic filters", jPanel5);

        jButton18.setText("Black and white smoothing");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
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
                    .addComponent(jButton18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jSlider_ThresholdSmoothing, javax.swing.GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSlider_Range, javax.swing.GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE))
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
                .addComponent(jButton18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_Range)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSlider_ThresholdSmoothing, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSlider_Range, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(247, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Advanced filters", jPanel6);

        jButton2.setText("Show activity");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 888, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2)
                .addContainerGap(296, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Window", jPanel9);

        jSplitPane1.setRightComponent(jTabbedPane1);

        jLabelImage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jScrollPane1.setViewportView(jLabelImage);

        jSplitPane1.setLeftComponent(jScrollPane1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel_Activity, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 943, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel_Activity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void jSlider_ThresholdSmoothingStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider_ThresholdSmoothingStateChanged
     }//GEN-LAST:event_jSlider_ThresholdSmoothingStateChanged

    private void jSlider_RangeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider_RangeStateChanged
     }//GEN-LAST:event_jSlider_RangeStateChanged

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        openImage();
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        openURL();
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        saveImage();
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        undoImage();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        redoImage();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        lastOpenedImage();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        deleteAllImages();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        grayScale();
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        blackWhite();
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        invertColors();
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        basicFilters();
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        RGBto();
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        blackWhiteSmoothing();
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        showActivity(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        showActivity(false);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        undoImage();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        redoImage();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        lastOpenedImage();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void JTextfield_URLMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JTextfield_URLMouseClicked
          DeleteText(JTextfield_URL);
    }//GEN-LAST:event_JTextfield_URLMouseClicked

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
            java.util.logging.Logger.getLogger(MainFormGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFormGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFormGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFormGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFormGUI().setVisible(true);
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
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox_BasicFilters;
    private javax.swing.JComboBox jComboBox_Invert;
    private javax.swing.JComboBox jComboBox_RGB;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelImage;
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
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanel_Activity;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSlider jSlider_Range;
    private javax.swing.JSlider jSlider_Threshold;
    private javax.swing.JSlider jSlider_ThresholdSmoothing;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
