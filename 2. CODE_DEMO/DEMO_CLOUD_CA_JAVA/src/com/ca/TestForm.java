/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ca;

import com.viettel.cloud.ca.CertBO;
import com.viettel.signature.pdf.TimestampConfig;
import com.viettel.signature.plugin.SignOOXmlFile;
import com.viettel.signature.plugin.SignPdfFile;
import com.viettel.signature.plugin.SignXmlFile;
import com.viettel.signature.utils.CertUtils;
import com.viettel.signature.xml.XmlDigitalSignature;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.bouncycastle.util.encoders.Base64;

/**
 *
 * @author HoTro
 */
public class TestForm extends javax.swing.JFrame {

    private static Logger logger = Logger.getLogger(TestForm.class);

    final static String LOOKANDFEEL = "System";
    final static String THEME = "Ocean";
    List<SignPdfFile> pdfSigList;
    List<SignXmlFile> xmlSigList;
    List<SignOOXmlFile> ooxmlSigList;

    private String userId;
    private String wsdlUrl;
    private String clientId;
    private String clientSecret;
    private String profileId;
    private String desc;
    private String app;
    private int id;
    private int indexCurrent;
    private int count;
//    private String token = "";
    private TokenBO token = new TokenBO();
    private Map<String, CertBO> certMap = new HashMap<String, CertBO>();
    private List<String> credentialIDList = new ArrayList<String>();

    /**
     * Creates new form TestForm
     */
    public TestForm() {

//        System.setProperty("http.proxyHost", "192.168.193.12");
//        System.setProperty("http.proxyPort", "3128");
        initLog4j();
        initComponents();
        //Set icon logo - title
        ImageIcon img = new ImageIcon("images/logo.jpg");
        this.setIconImage(img.getImage());
        this.setTitle("Thử nghiệm Ký Cloud-CA");
        String[][] props = {
            {"javax.net.ssl.trustStore", "config/keystore.jks",},
            {"javax.net.ssl.keyStore", "config/keystore.jks",},
            {"javax.net.ssl.keyStorePassword", "qwerty@123",},
            {"javax.net.ssl.keyStoreType", "JKS",}
        };

        for (int i = 0; i < props.length; i++) {
            System.getProperties().setProperty(props[i][0], props[i][1]);
        }

        HttpsURLConnection.setDefaultHostnameVerifier(SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
    }

    private static void initLookAndFeel() {
        try {
            if (LOOKANDFEEL != null) {
                String lookAndFeel;
                if ("Metal".equals(LOOKANDFEEL)) {
                    lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
                    //  an alternative way to set the Metal L&F is to replace the 
                    // previous line with:
                    // lookAndFeel = "javax.swing.plaf.metal.MetalLookAndFeel";

                } else if ("System".equals(LOOKANDFEEL)) {
                    lookAndFeel = UIManager.getSystemLookAndFeelClassName();
                } else if ("Motif".equals(LOOKANDFEEL)) {
                    lookAndFeel = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
                } else if ("GTK".equals(LOOKANDFEEL)) {
                    lookAndFeel = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
                } else {
                    logger.error("Unexpected value of LOOKANDFEEL specified: "
                            + LOOKANDFEEL);
                    lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
                }

                try {

                    UIManager.setLookAndFeel(lookAndFeel);

                    // If L&F = "Metal", set the theme
                    if ("Metal".equals(LOOKANDFEEL)) {
                        if ("DefaultMetal".equals(THEME)) {
                            MetalLookAndFeel.setCurrentTheme(new DefaultMetalTheme());
                        } else if ("Ocean".equals(THEME)) {
                            MetalLookAndFeel.setCurrentTheme(new OceanTheme());
                        }

                        UIManager.setLookAndFeel(new MetalLookAndFeel());
                    }

                } catch (ClassNotFoundException e) {
                    logger.error("Couldn't find class for specified look and feel:" + lookAndFeel, e);
                    logger.error("Did you include the L&F library in the class path?");
                    logger.error("Using the default look and feel.");
                } catch (UnsupportedLookAndFeelException e) {
                    logger.error("Can't use the specified look and feel (" + lookAndFeel + ") on this platform.", e);
                    logger.error("Using the default look and feel.");
                } catch (Exception e) {
                    logger.error("Couldn't get specified look and feel (" + lookAndFeel + "), for some reason.", e);
                    logger.error("Using the default look and feel.");
                }
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }

    private static void createAndShowGUI() throws IOException, FileNotFoundException, CertificateException, URISyntaxException, Exception {
        //Set the look and feel.
        initLookAndFeel();

        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        TestForm mainFrame = new TestForm();
        //Tool Khach Hang
//        mainFrame.jTabbedPane1.remove(1);

        final Toolkit toolkit = Toolkit.getDefaultToolkit();
        final Dimension screenSize = toolkit.getScreenSize();
        int x = (screenSize.width - mainFrame.getWidth()) / 2;
        int y = (screenSize.height - mainFrame.getHeight()) / 2;
        mainFrame.setLocation(x, y);

        mainFrame.setVisible(true);
    }

    public void initLog4j() {
        System.out.println("Log4JInitServlet is initializing log4j");
        String log4jLocation = "config/log4j.properties";

        File yoMamaYesThisSaysYoMama = new File(log4jLocation);
        if (yoMamaYesThisSaysYoMama.exists()) {
            System.out.println("Initializing log4j with: " + log4jLocation);
            PropertyConfigurator.configure(log4jLocation);
            logger.info("Start Program");
        } else {
            System.out.println("*** " + log4jLocation + " file not found, so initializing log4j with BasicConfigurator");
            BasicConfigurator.configure();
        }
    }

    private void log(String log) {
        logger.info(log);
        logTxt.setText(logTxt.getText() + "\n" + log);
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
        jLabel3 = new javax.swing.JLabel();
        wsdlUrlTxt = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        userIdTxt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        pathFileTxt = new javax.swing.JTextField();
        selectFileBtn = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        pathSaveFolderTxt = new javax.swing.JTextField();
        selectFolderBtn = new javax.swing.JButton();
        numTotalTxt = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        indexCurrentTxt = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        logTxt = new javax.swing.JTextArea();
        clearLogBtn = new javax.swing.JButton();
        signHandWordBtn = new javax.swing.JButton();
        signAutoBtn = new javax.swing.JButton();
        numTotalFailTxt = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        numTotalSuccessTxt = new javax.swing.JTextField();
        resetBtn = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        descTxt = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        appTxt = new javax.swing.JTextField();
        idTxt = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        profileIdTxt = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        clientSecretTxt = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        clientIdTxt = new javax.swing.JTextField();
        getCertListBtn = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        certListCbox = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setText("WSDL URL:");

        wsdlUrlTxt.setText("https://remotesigning.viettel.vn:8773");
        wsdlUrlTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wsdlUrlTxtActionPerformed(evt);
            }
        });

        jLabel1.setText("User_ID:");

        userIdTxt.setText("MST_0100109106-998");

        jLabel5.setText("File:");

        pathFileTxt.setText("C:\\Users\\HoTro\\Documents\\sample.pdf");

        selectFileBtn.setText("Chọn");
        selectFileBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectFileBtnActionPerformed(evt);
            }
        });

        jLabel6.setText("Thư mục lưu:");

        pathSaveFolderTxt.setText("C:\\Users\\HoTro\\Documents\\Test");

        selectFolderBtn.setText("Chọn");
        selectFolderBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectFolderBtnActionPerformed(evt);
            }
        });

        numTotalTxt.setText("3");

        jLabel4.setText("Thứ tự YC:");

        jLabel7.setText("Tổng yêu cầu:");

        indexCurrentTxt.setText("0");
        indexCurrentTxt.setEnabled(false);

        logTxt.setColumns(20);
        logTxt.setRows(5);
        jScrollPane1.setViewportView(logTxt);

        clearLogBtn.setText("Xóa Log");
        clearLogBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearLogBtnActionPerformed(evt);
            }
        });

        signHandWordBtn.setText("Ký đơn");
        signHandWordBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signHandWordBtnActionPerformed(evt);
            }
        });

        signAutoBtn.setText("Ký lô");
        signAutoBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signAutoBtnActionPerformed(evt);
            }
        });

        numTotalFailTxt.setEditable(false);
        numTotalFailTxt.setText("0");

        jLabel8.setText("Số Lỗi:");

        jLabel9.setText("Số thành công:");

        numTotalSuccessTxt.setEditable(false);
        numTotalSuccessTxt.setText("0");

        resetBtn.setText("Reset");
        resetBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetBtnActionPerformed(evt);
            }
        });

        jLabel10.setText("Mô tả ký:");

        descTxt.setText("Transaction ID 123 - Demo Sign Cloud CA");

        jLabel11.setText("Ứng dụng ký:");

        appTxt.setText("vOffice");

        idTxt.setText("123");

        jLabel12.setText("ID:");

        jLabel2.setText("Profile_ID:");

        profileIdTxt.setText("adss:ras:profile:001");

        jLabel13.setText("Client_Secret:");

        clientSecretTxt.setText("205640fd6ea8c7d80bb91c630b52d286d21ee511");

        jLabel14.setText("Client_ID:");

        clientIdTxt.setText("samples_test_client");

        getCertListBtn.setLabel("Get Cert List");
        getCertListBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getCertListBtnActionPerformed(evt);
            }
        });

        jLabel15.setText("CTS:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                    .addComponent(jLabel3)
                                                    .addGap(44, 44, 44))
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                    .addComponent(jLabel10)
                                                    .addGap(53, 53, 53)))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel11)
                                                    .addComponent(jLabel1)
                                                    .addComponent(jLabel5)
                                                    .addComponent(jLabel6))
                                                .addGap(33, 33, 33)))
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(pathSaveFolderTxt)
                                            .addComponent(pathFileTxt)
                                            .addComponent(descTxt)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addComponent(appTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(45, 45, 45)
                                                        .addComponent(jLabel12)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(idTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                            .addComponent(jLabel4)
                                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                    .addComponent(userIdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                                                                    .addComponent(jLabel7))
                                                                .addComponent(wsdlUrlTxt)
                                                                .addComponent(profileIdTxt)))
                                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGap(81, 81, 81)
                                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                    .addComponent(jLabel14)
                                                                    .addComponent(jLabel13)))
                                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                    .addComponent(indexCurrentTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addComponent(numTotalTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                                                .addGap(0, 0, Short.MAX_VALUE))))
                                    .addComponent(jLabel2))
                                .addGap(18, 18, 18))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(clearLogBtn)
                                    .addComponent(jLabel15))
                                .addGap(27, 27, 27)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(signHandWordBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(signAutoBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(resetBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(certListCbox, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(clientIdTxt, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(clientSecretTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(numTotalSuccessTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(numTotalFailTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(getCertListBtn)
                                    .addComponent(selectFolderBtn)
                                    .addComponent(selectFileBtn))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(wsdlUrlTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel14))
                    .addComponent(clientIdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(profileIdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(clientSecretTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(userIdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(numTotalSuccessTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(numTotalTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(indexCurrentTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)
                        .addComponent(numTotalFailTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pathFileTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectFileBtn)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pathSaveFolderTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectFolderBtn)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(descTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(appTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel15)
                        .addComponent(certListCbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(getCertListBtn))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(signHandWordBtn)
                    .addComponent(signAutoBtn)
                    .addComponent(resetBtn)
                    .addComponent(clearLogBtn))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void selectFileBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectFileBtnActionPerformed
        // TODO add your handling code here:

        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setCurrentDirectory(new java.io.File(pathFileTxt.getText()));
        //fileSignChooser.setCurrentDirectory(new java.io.File(JShellLink.getDirectory("desktop")));

//        FileNameExtensionFilter filter = new FileNameExtensionFilter("pdf", "pdf");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("pdf, xml", "pdf", "xml");

        fileChooser.setFileFilter(filter);
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.showDialog(this, "Chọn File");

        File selectedfile = fileChooser.getSelectedFile();
        if (selectedfile != null) {
            pathFileTxt.setText(selectedfile.getAbsolutePath());
        }
    }//GEN-LAST:event_selectFileBtnActionPerformed

    private void selectFolderBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectFolderBtnActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File(pathSaveFolderTxt.getText()));
        //chooser.setCurrentDirectory(new java.io.File(JShellLink.getDirectory("desktop")));

        String fileP12FolderPath = pathSaveFolderTxt.getText();
        if (!fileP12FolderPath.isEmpty()) {
            File folderDefaultSignFile = new File(fileP12FolderPath);
            if (folderDefaultSignFile.exists()) {
                chooser.setCurrentDirectory(folderDefaultSignFile);
            }
        }

        //chooser.setDialogTitle("Thư mục lưu");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        int result = chooser.showDialog(this, "Chọn thư mục");
        if (result == JFileChooser.APPROVE_OPTION) {

            File folder = chooser.getSelectedFile();
            if (!folder.isDirectory()) {
                folder = folder.getParentFile();
            }
            pathSaveFolderTxt.setText(folder.toString());
        }
    }//GEN-LAST:event_selectFolderBtnActionPerformed

    private void clearLogBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearLogBtnActionPerformed
        // TODO add your handling code here:
        logTxt.setText("");
    }//GEN-LAST:event_clearLogBtnActionPerformed

    private void addSuccessCount() {
        String numTotal = numTotalSuccessTxt.getText();
        if (numTotal.trim().isEmpty()) {
            log("Tổng yêu cầu thành công không hợp lệ");
            return;
        }
        int count = 0;
        try {
            count = Integer.valueOf(numTotal);
        } catch (Exception ex) {
            logger.error(ex);
            log("Tổng yêu cầu thành công không hợp lệ: " + ex.toString());
            return;
        }
        count++;
        numTotalSuccessTxt.setText("" + count);
    }

    private void addFailCount() {
        String numTotal = numTotalFailTxt.getText();
        if (numTotal.trim().isEmpty()) {
            log("Tổng yêu cầu Fail không hợp lệ");
            return;
        }
        int count = 0;
        try {
            count = Integer.valueOf(numTotal);
        } catch (Exception ex) {
            logger.error(ex);
            log("Tổng yêu cầu Fail không hợp lệ: " + ex.toString());
            return;
        }
        count++;
        numTotalFailTxt.setText("" + count);
    }

    private void signHandWordBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_signHandWordBtnActionPerformed

        if (signFile()) {
            addSuccessCount();
        } else {
            addFailCount();
        }
    }//GEN-LAST:event_signHandWordBtnActionPerformed

    private Boolean signFile() {
        String info = "Ký lô.";
        if (!getFormData(info)) {
            return false;
        }

        String idString = idTxt.getText();
        if (idString.trim().isEmpty()) {
            log(info + "ID tài liệu không hợp lệ");
            return false;
        }
        int id;
        try {
            id = Integer.valueOf(idString);
        } catch (Exception e) {
            log(info + "ID tài liệu phải là số nguyên");
            return false;
        }
        int indexCurrent = 0;
        try {
            indexCurrent = Integer.valueOf(indexCurrentTxt.getText());
        } catch (Exception e) {
            log(info + "Thứ tự yêu cầu không hợp lệ");
            return false;
        }

        String numTotal = numTotalTxt.getText();
        if (numTotal.trim().isEmpty()) {
            log("Tổng yêu cầu không hợp lệ");
            return false;
        }
        int count = 0;
        try {
            count = Integer.valueOf(numTotal);
        } catch (Exception ex) {
            logger.error(ex);
            log(info + "Tổng yêu cầu không hợp lệ: " + ex.toString());
            return false;
        }

        if (indexCurrent >= count) {
            log(info + "Vượt quá số lượng lô");
            return false;
        }

        indexCurrent++;
        indexCurrentTxt.setText("" + indexCurrent);
        info += indexCurrent + ".";

        String pathDes = pathSaveFolderTxt.getText();
        String pathFile = pathFileTxt.getText();
        try {

            if (pathFile.trim().isEmpty()) {
                log(info + "Chưa chọn file ký");
                return false;
            }
            if (!(new File(pathFile.trim())).exists() || !(new File(pathFile.trim())).isFile()) {
                log(info + "File ký không tồn tại");
                return false;
            }
            if (pathDes.trim().isEmpty()) {
                log(info + "Chưa chọn thư mục lưu");
                return false;
            }
            if (!(new File(pathDes.trim())).exists() || !(new File(pathDes.trim())).isDirectory()) {
                log(info + "thư mục lưu không tồn tại");
                return false;
            }

            InfoFile infoFile = new InfoFile(pathFile, '\\', '.');
            String fileName = infoFile.filename();
            String ext = infoFile.extension();
            String pathFolder = infoFile.path();
            String destFilepath = pathDes + File.separator + fileName + "." + ext;
            File fileDes = new File(destFilepath);
            if (fileDes.exists()) {
                String name = fileName + "-signed";
                destFilepath = pathDes + File.separator + name + "." + ext;
                fileDes = new File(destFilepath);
                int index = 1;
                while (fileDes.exists()) {
                    index++;
                    String name_2 = name + " (" + index + ")";
                    destFilepath = pathDes + File.separator + name_2 + "." + ext;
                    fileDes = new File(destFilepath);
                }
            }
            SignMobileCAAction signMobileCAAction = new SignMobileCAAction();

            if (certMap == null || certMap.size() == 0 || credentialIDList == null || credentialIDList.size() == 0 || certListCbox.getSelectedIndex() == -1) {
                log(info + "Chưa chọn CTS");
                return false;
            }

            String credentialID = credentialIDList.get(certListCbox.getSelectedIndex());
            CertBO certBO = certMap.get(credentialID);


            String folderRootCA = "RootCA";
            X509Certificate[] certChain = X509ExtensionUtil.getCertChainOfCert(certBO.getCertificates().get(0), folderRootCA);
            String data = app + " - " + desc;
            byte[] utf8DataBytes = data.getBytes(StandardCharsets.UTF_8);
            String dataDisplay = Base64.toBase64String(utf8DataBytes);
            String base64Hash = new String();
            SignPdfFile pdfSig = null;
            SignOOXmlFile ooxmlSig = null;
            SignXmlFile xmlSig = null;
            if ("pdf".equals(ext)) {
                String fontPath = "font/times.ttf";
                pdfSig = new SignPdfFile();
                //        String fieldName = "sig2";
                //        base64Hash = HashFilePDF.getHashTypeNoDisplay_ExistedSignatureField(pdfSig, src, certChain, fontPath, fieldName);
                //        base64Hash = HashFilePDF.getHashTypeRectangleTextDefault(pdfSig, src, certChain, fontPath);
                //        base64Hash = HashFilePDF.getHashTypeRectangleTextDefault_ExistedSignatureField(pdfSig, src, certChain, fontPath, fieldName);

                base64Hash = HashFilePDF.getHashTypeRectangleText(pdfSig, pathFile, certChain, fontPath);
                //        String imageFile = realPath + "attpStamp.png";
//                String imageFile = "images/logo.jpg";
//                String displayText = "Người ký: xxx\n"
//                        + "Ngày ký: xxx\n"
//                        + "Chức danh: xxx\n"
//                        + "Đơn vị: xxx\n"
//                        + "Nội dung: xxx\n";
//                base64Hash = HashFilePDF.getHashTypeImageTextKBNN(pdfSig, pathFile, certChain, fontPath, imageFile, displayText);
                //        String displayText = "123";
                //        base64Hash = HashFilePDF.getHashTypeRectangleText2_ExistedSignatureField(pdfSig, src, certChain, fontPath, displayText, fieldName);
                //        String imageFile = realPath + "attpStamp.png";
                //        base64Hash = HashFilePDF.getHashTypeImageDefault(pdfSig, src, certChain, imageFile, fontPath);
                //        base64Hash = HashFilePDF.getHashTypeImageDefault_ExistedSignatureField(pdfSig, src, certChain, imageFile, fontPath, fieldName);
                //        base64Hash = HashFilePDF.getHashTypeImage(pdfSig, src, certChain, imageFile, fontPath);
                //        base64Hash = HashFilePDF.getHashTypeTableDefault(pdfSig, src, certChain, fontPath);
                //        base64Hash = HashFilePDF.getHashTypeTable(pdfSig, src, certChain, fontPath);
            } else if ("docx".equals(ext) || "xlsx".equals(ext) || "pptx".equals(ext)) {
                ooxmlSig = new SignOOXmlFile();
                base64Hash = getHashOOXML(ooxmlSig, pathFile, certChain);
            } else if ("xml".equals(ext)) {
                xmlSig = new SignXmlFile();
                base64Hash = getHashXML(xmlSig, pathFile, certChain);
            }

            if (base64Hash == null || base64Hash.trim().isEmpty()) {
                log(info + "Tạo Hash không thành công");
                return false;
            }
//            log(info + "Tạo Hash thành công: " + base64Hash);
            log(info + "Tạo Hash thành công");
            List<String> hashList = new ArrayList<String>();
            hashList.add(base64Hash);
            List<String> signatureList = signMobileCAAction.signHash(hashList, id, dataDisplay, clientId, clientSecret, credentialID, wsdlUrl, token);

            if (signatureList == null || signatureList.size() == 0) {
                log(info + "Ký không thành công");
                return false;
            }
//            log(info + "Ký hash thành công: " + signature);
            log(info + "Ký hash thành công");

            try {
                Base64.decode(signatureList.get(0));
            } catch (Exception e) {
                logger.error(e);
                log(info + "Định dạng chữ ký không hợp lệ:" + e.getMessage());
                return false;
            }
            Boolean restul = false;
            if ("pdf".equals(ext)) {
                restul = insertSignaturePdfFile(pdfSig, signatureList.get(0), destFilepath);
            } else if ("docx".equals(ext) || "xlsx".equals(ext) || "pptx".
                    equals(ext)) {
                restul = insertSignatureOOXmlFile(ooxmlSig, signatureList.get(0), destFilepath);
            } else if ("xml".equals(ext)) {
                restul = insertSignatureXmlFile(xmlSig, signatureList.get(0), destFilepath);
            }
            if (!restul) {
                log(info + "Ký không thành công");
            } else {
                log(info + "Ký thành công");
                return true;
            }

        } catch (Exception ex) {
            logger.error(ex);
            log(ex.toString());
        }
        return false;
    }

    private Boolean signFileBatch() {
        pdfSigList = new ArrayList<SignPdfFile>();
        ooxmlSigList = new ArrayList<SignOOXmlFile>();
        xmlSigList = new ArrayList<SignXmlFile>();
        String info = "Ký lô.";
        String wsdlUrl = wsdlUrlTxt.getText();
        if (wsdlUrl.trim().isEmpty()) {
            log(info + "WSDL URL không hợp lệ");
            return false;
        }
        String clientId = clientIdTxt.getText();
        if (clientId.trim().isEmpty()) {
            log(info + "Client Id không hợp lệ");
            return false;
        }
        String clientSecret = clientSecretTxt.getText();
        if (clientSecret.trim().isEmpty()) {
            log(info + "Client Secret không hợp lệ");
            return false;
        }
        String profileId = profileIdTxt.getText();
        if (profileId.trim().isEmpty()) {
            log(info + "Profile Id không hợp lệ");
            return false;
        }
        String userId = userIdTxt.getText();
        if (userId.trim().isEmpty()) {
            log(info + "userId không hợp lệ");
            return false;
        }
        String desc = descTxt.getText();
        if (desc.trim().isEmpty()) {
            log(info + "Mô tả ký không hợp lệ");
            return false;
        }
        String app = appTxt.getText();
        if (app.trim().isEmpty()) {
            log(info + "Ứng dụng ký không hợp lệ");
            return false;
        }
        String idString = idTxt.getText();
        if (idString.trim().isEmpty()) {
            log(info + "ID tài liệu không hợp lệ");
            return false;
        }
        int id;
        try {
            id = Integer.valueOf(idString);
        } catch (Exception e) {
            log(info + "ID tài liệu phải là số nguyên");
            return false;
        }
        int indexCurrent = 0;
        try {
            indexCurrent = Integer.valueOf(indexCurrentTxt.getText());
        } catch (Exception e) {
            log(info + "Thứ tự yêu cầu không hợp lệ");
            return false;
        }

        String numTotal = numTotalTxt.getText();
        if (numTotal.trim().isEmpty()) {
            log("Tổng yêu cầu không hợp lệ");
            return false;
        }
        int count = 0;
        try {
            count = Integer.valueOf(numTotal);
        } catch (Exception ex) {
            logger.error(ex);
            log(info + "Tổng yêu cầu không hợp lệ: " + ex.toString());
            return false;
        }

        if (indexCurrent >= count) {
            log(info + "Vượt quá số lượng lô");
            return false;
        }

        String pathDes = pathSaveFolderTxt.getText();
        String pathFile = pathFileTxt.getText();
        try {

            if (pathFile.trim().isEmpty()) {
                log(info + "Chưa chọn file ký");
                return false;
            }
            if (!(new File(pathFile.trim())).exists() || !(new File(pathFile.trim())).isFile()) {
                log(info + "File ký không tồn tại");
                return false;
            }
            if (pathDes.trim().isEmpty()) {
                log(info + "Chưa chọn thư mục lưu");
                return false;
            }
            if (!(new File(pathDes.trim())).exists() || !(new File(pathDes.trim())).isDirectory()) {
                log(info + "thư mục lưu không tồn tại");
                return false;
            }

            InfoFile infoFile = new InfoFile(pathFile, '\\', '.');
            String fileName = infoFile.filename();
            String ext = infoFile.extension();
            String pathFolder = infoFile.path();

            SignMobileCAAction signMobileCAAction = new SignMobileCAAction();
            HashMap<String, CertBO> certList = signMobileCAAction.getAllCertificates(userId, wsdlUrl, clientId, clientSecret, profileId, token);

            if (certList == null || certList.size() == 0) {
                log(info + "Không lấy được thông tin CTS");
                return false;
            }
            //Default get the last certificate or customer select certificate
            CertBO certBO = null;
            String credentialID = null;
            for (String i : certList.keySet()) {
                logger.info("key: " + i + " value: " + certList.get(i));
                credentialID = i;
                certBO = certList.get(i);
//                break;
            }

            String folderRootCA = "RootCA";
            X509Certificate[] certChain = X509ExtensionUtil.getCertChainOfCert(certBO.getCertificates().get(0), folderRootCA);
//            String dataDisplay = "Test ký Cloud CA";
//            String dataDisplay = desc;
            String dataDisplay = app + " - " + desc;

            List<String> base64HashList = new ArrayList<String>();
            for (int i = 0; i < count - indexCurrent; i++) {
                String base64Hash = null;
                if ("pdf".equals(ext)) {
                    String fontPath = "font/times.ttf";
                    SignPdfFile pdfSig = new SignPdfFile();
                    //        String fieldName = "sig2";
                    //        base64Hash = HashFilePDF.getHashTypeNoDisplay_ExistedSignatureField(pdfSig, src, certChain, fontPath, fieldName);
                    //        base64Hash = HashFilePDF.getHashTypeRectangleTextDefault(pdfSig, src, certChain, fontPath);
                    //        base64Hash = HashFilePDF.getHashTypeRectangleTextDefault_ExistedSignatureField(pdfSig, src, certChain, fontPath, fieldName);

//                    base64Hash = HashFilePDF.getHashTypeRectangleText(pdfSig, pathFile, certChain, fontPath);
                    //        String imageFile = realPath + "attpStamp.png";
                    String imageFile = "images/logo.jpg";
                    String displayText = "Người ký: xxx\n"
                            + "Ngày ký: xxx\n"
                            + "Chức danh: xxx\n"
                            + "Đơn vị: xxx\n"
                            + "Nội dung: xxx\n";
                    base64Hash = HashFilePDF.getHashTypeImageTextKBNN(pdfSig, pathFile, certChain, fontPath, imageFile, displayText);
                    //        String displayText = "123";
                    //        base64Hash = HashFilePDF.getHashTypeRectangleText2_ExistedSignatureField(pdfSig, src, certChain, fontPath, displayText, fieldName);
                    //        String imageFile = realPath + "attpStamp.png";
                    //        base64Hash = HashFilePDF.getHashTypeImageDefault(pdfSig, src, certChain, imageFile, fontPath);
                    //        base64Hash = HashFilePDF.getHashTypeImageDefault_ExistedSignatureField(pdfSig, src, certChain, imageFile, fontPath, fieldName);
                    //        base64Hash = HashFilePDF.getHashTypeImage(pdfSig, src, certChain, imageFile, fontPath);
                    //        base64Hash = HashFilePDF.getHashTypeTableDefault(pdfSig, src, certChain, fontPath);
                    //        base64Hash = HashFilePDF.getHashTypeTable(pdfSig, src, certChain, fontPath);
                    pdfSigList.add(pdfSig);
                } else if ("docx".equals(ext) || "xlsx".equals(ext) || "pptx".equals(ext)) {
                    SignOOXmlFile ooxmlSig = new SignOOXmlFile();
                    base64Hash = getHashOOXML(ooxmlSig, pathFile, certChain);
                    ooxmlSigList.add(ooxmlSig);
                } else if ("xml".equals(ext)) {
                    SignXmlFile xmlSig = new SignXmlFile();
                    base64Hash = getHashXML(xmlSig, pathFile, certChain);
                    xmlSigList.add(xmlSig);
                }
                if (base64Hash == null || base64Hash.trim().isEmpty()) {
                    log(info + "Tạo Hash không thành công");
                    return false;
                }
                log(info + "Tạo Hash thành công");
                base64HashList.add(base64Hash);
//            log(info + "Tạo Hash thành công: " + base64Hash);
            }

            List<String> signatureList = signMobileCAAction.signHash(base64HashList, id, dataDisplay, clientId, clientSecret, credentialID, wsdlUrl, token);

            if (signatureList == null || signatureList.size() == 0) {
                log(info + "Ký không thành công");
                return false;
            }
//            log(info + "Ký hash thành công: " + signature);
            log(info + "Ký hash thành công");
            for (int i = 0; i < signatureList.size(); i++) {
                try {
                    Base64.decode(signatureList.get(i));
                } catch (Exception e) {
                    logger.error(e);
                    log(info + "Định dạng chữ ký không hợp lệ:" + e.getMessage());
                    return false;
                }
                Boolean restul = false;
                String destFilepath = pathDes + File.separator + fileName + "." + ext;
                File fileDes = new File(destFilepath);
                if (fileDes.exists()) {
                    String name = fileName + "-signed";
                    destFilepath = pathDes + File.separator + name + "." + ext;
                    fileDes = new File(destFilepath);
                    int index = 1;
                    while (fileDes.exists()) {
                        index++;
                        String name_2 = name + " (" + index + ")";
                        destFilepath = pathDes + File.separator + name_2 + "." + ext;
                        fileDes = new File(destFilepath);
                    }
                }
                if ("pdf".equals(ext)) {
                    restul = insertSignaturePdfFile(pdfSigList.get(i), signatureList.get(i), destFilepath);
                } else if ("docx".equals(ext) || "xlsx".equals(ext) || "pptx".
                        equals(ext)) {
                    restul = insertSignatureOOXmlFile(ooxmlSigList.get(i), signatureList.get(i), destFilepath);
                } else if ("xml".equals(ext)) {
                    restul = insertSignatureXmlFile(xmlSigList.get(i), signatureList.get(i), destFilepath);
                }
                if (!restul) {
                    log(info + "Ký " + i + " không thành công");
                    addFailCount();
                } else {
                    log(info + "Ký " + i + " thành công");
                    addSuccessCount();
                }
            }
            indexCurrentTxt.setText("" + count);
            return true;
        } catch (Exception ex) {
            logger.error(ex);
            log(ex.toString());
        }
        return false;
    }


    private void resetBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetBtnActionPerformed
        // TODO add your handling code here:
        numTotalSuccessTxt.setText("0");
        indexCurrentTxt.setText("0");
        numTotalFailTxt.setText("0");
    }//GEN-LAST:event_resetBtnActionPerformed

    private void signAutoBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_signAutoBtnActionPerformed
        // TODO add your handling code here:
        String info = "Ký lô tự động.";

        int indexCurrent = 0;
        try {
            indexCurrent = Integer.valueOf(indexCurrentTxt.getText());
        } catch (Exception e) {
            log(info + "Thứ tự yêu cầu không hợp lệ");
            return;
        }

        String numTotal = numTotalTxt.getText();
        if (numTotal.trim().isEmpty()) {
            log("Tổng yêu cầu không hợp lệ");
            return;
        }
        int count = 0;
        try {
            count = Integer.valueOf(numTotal);
        } catch (Exception ex) {
            logger.error(ex);
            log(info + "Tổng yêu cầu không hợp lệ: " + ex.toString());
            return;
        }

        if (indexCurrent >= count) {
            log(info + "Vượt quá số lượng lô");
            return;
        }
        signFileBatch();
    }//GEN-LAST:event_signAutoBtnActionPerformed

    private Boolean getFormData(String info) {
        wsdlUrl = wsdlUrlTxt.getText();
        if (wsdlUrl.trim().length() == 0) {
            log(info + "WSDL URL không hợp lệ");
            return false;
        }

        clientId = clientIdTxt.getText();
        if (clientId.trim().length() == 0) {
            log(info + "Client Id không hợp lệ");
            return false;
        }

        clientSecret = clientSecretTxt.getText();
        if (clientSecret.trim().length() == 0) {
            log(info + "Client Secret không hợp lệ");
            return false;
        }

        profileId = profileIdTxt.getText();
        if (profileId.trim().length() == 0) {
            log(info + "Profile Id không hợp lệ");
            return false;
        }

        userId = userIdTxt.getText();
        if (userId.trim().length() == 0) {
            log(info + "userId không hợp lệ");
            return false;
        }

        desc = descTxt.getText();
        if (desc.trim().length() == 0) {
            log(info + "Mô tả ký không hợp lệ");
            return false;
        }

        app = appTxt.getText();
        if (app.trim().length() == 0) {
            log(info + "Ứng dụng ký không hợp lệ");
            return false;
        }
        return true;
    }

    private void getCertListBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getCertListBtnActionPerformed
        certListCbox.removeAllItems();
        certMap.clear();
        credentialIDList.clear();
        String info = "Get Cert List. ";
        if (!getFormData(info)) {
            return;
        }

        certMap = SignMobileCAAction.getAllCertificates(userId, wsdlUrl, clientId, clientSecret, profileId, token);

        if (certMap == null || certMap.size() == 0) {
            log("Không tìm thấy CTS");
            return;
        }
        //Default get the last certificate or customer select certificate
        CertBO certBO = null;
        String credentialID = null;

        X509Certificate[] certChain = null;
        X509Certificate x509Cert = null;

//        X509Certificate x509Cert = CertUtils.getX509Cert(certList.get(i));
        for (Map.Entry<String, CertBO> entry : certMap.entrySet()) {
            String key = entry.getKey();
            CertBO value = entry.getValue();

            credentialID = key;
            certBO = value;
            if (certBO != null && certBO.getCertificates() != null && certBO.getCertificates().size() != 0) {
                x509Cert = CertUtils.getX509Cert(certBO.getCertificates().get(0));

                if (certBO.getCertificates().size() > 1) {
                    X509Certificate certViettelCA = CertUtils.getX509Cert(certBO.getCertificates().get(1));
                    if (certViettelCA != null) {
                        certChain = new X509Certificate[]{x509Cert, certViettelCA};
                    }
                }
                credentialIDList.add(credentialID);
                certListCbox.addItem(x509Cert.getSerialNumber().toString(16) + " - " + x509Cert.getNotAfter().toGMTString() + " - " + CertUtils.getCN(x509Cert));
            }
        }
        certListCbox.setSelectedIndex(certListCbox.getItemCount() - 1);
    }//GEN-LAST:event_getCertListBtnActionPerformed

    private void wsdlUrlTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wsdlUrlTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_wsdlUrlTxtActionPerformed

    private String getHashXML(SignXmlFile xmlSig, String src, X509Certificate[] certChain) {
        try {
//            String src = getServlet().getServletContext().getRealPath("/") + "example.xml";
            String base64Hash = xmlSig.createHash(src, certChain, XmlDigitalSignature.HASH_ALGORITHM_SHA1);
            return base64Hash;
        } catch (Exception ex) {
            logger.error(ex);
        }
        return null;
    }

    private String getHashOOXML(SignOOXmlFile ooxmlSig, String src, X509Certificate[] certChain) {
        try {
//            String src = getServlet().getServletContext().getRealPath("/") + "example.docx";
            String base64Hash = ooxmlSig.createHash(src, certChain);
            return base64Hash;
        } catch (Exception ex) {
            logger.error(ex);
        }
        return null;
    }

    private Boolean insertSignatureXmlFile(SignXmlFile xmlSig, String signature, String destFilepath) {
        try {
            xmlSig.insertSignature(signature, destFilepath);
            return true;
        } catch (Exception ex) {
            logger.error(ex);
        }
        return false;
    }

    private Boolean insertSignatureOOXmlFile(SignOOXmlFile xmlSig, String signature, String destFilepath) {
        try {
            xmlSig.insertSignature(signature, destFilepath);
            return true;
        } catch (Exception ex) {
            logger.error(ex);
        }
        return false;
    }

    private Boolean insertSignaturePdfFile(SignPdfFile pdfSig, String signature, String destPath) {
        TimestampConfig timestampConfig = new TimestampConfig();
        timestampConfig.setUseTimestamp(false);
        if (!pdfSig.insertSignature(signature, destPath, timestampConfig)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    String[][] props = {
                        {"javax.net.ssl.trustStore", "config/keystore.jks",},
                        {"javax.net.ssl.keyStore", "config/keystore.jks",},
                        {"javax.net.ssl.keyStorePassword", "qwerty@123",},
                        {"javax.net.ssl.keyStoreType", "JKS",}
                    };

                    for (int i = 0; i < props.length; i++) {
                        System.getProperties().setProperty(props[i][0], props[i][1]);
                    }

                    HttpsURLConnection.setDefaultHostnameVerifier(SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                    createAndShowGUI();
                } catch (IOException ex) {
                    logger.error(ex);
                } catch (CertificateException ex) {
                    logger.error(ex);
                } catch (URISyntaxException ex) {
                    logger.error(ex);
                } catch (Exception ex) {
                    logger.error(ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField appTxt;
    private javax.swing.JComboBox<String> certListCbox;
    private javax.swing.JButton clearLogBtn;
    private javax.swing.JTextField clientIdTxt;
    private javax.swing.JTextField clientSecretTxt;
    private javax.swing.JTextField descTxt;
    private javax.swing.JButton getCertListBtn;
    private javax.swing.JTextField idTxt;
    private javax.swing.JTextField indexCurrentTxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea logTxt;
    private javax.swing.JTextField numTotalFailTxt;
    private javax.swing.JTextField numTotalSuccessTxt;
    private javax.swing.JTextField numTotalTxt;
    private javax.swing.JTextField pathFileTxt;
    private javax.swing.JTextField pathSaveFolderTxt;
    private javax.swing.JTextField profileIdTxt;
    private javax.swing.JButton resetBtn;
    private javax.swing.JButton selectFileBtn;
    private javax.swing.JButton selectFolderBtn;
    private javax.swing.JButton signAutoBtn;
    private javax.swing.JButton signHandWordBtn;
    private javax.swing.JTextField userIdTxt;
    private javax.swing.JTextField wsdlUrlTxt;
    // End of variables declaration//GEN-END:variables
}
