using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using DEMO_CLOUD_CA_DOTNET.BO;
using Org.BouncyCastle.X509;
using ViettelFileSigner;

namespace DEMO_CLOUD_CA_DOTNET
{
    public partial class DemoCloudCA : Form
    {

        private static readonly log4net.ILog logger = log4net.LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);

        private String userId;
        private String wsdlUrl;
        private String clientId;
        private String clientSecret;
        private String profileId;
        private string desc;
        private string app;
        private int id;
        private int indexCurrent;
        private int count;
        private String token = "";
        private Dictionary<String, CertBO> certMap = new Dictionary<string, CertBO>();
        private List<string> credentialIDList = new List<string>();
        public DemoCloudCA()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }

        private void selectFileBtn_Click(object sender, EventArgs e)
        {

            System.Windows.Forms.OpenFileDialog openFile;
            openFile = new System.Windows.Forms.OpenFileDialog();
            openFile.Filter = "PDF, PDF files *.pdf|*.pdf";
            openFile.Title = "Select a file";
            if (openFile.ShowDialog() != DialogResult.OK)
                return;

            pathFileTxt.Text = openFile.FileName;
        }

        private void selectFolderBtn_Click(object sender, EventArgs e)
        {

            using (var fbd = new FolderBrowserDialog())
            {
                DialogResult result = fbd.ShowDialog();

                if (result == DialogResult.OK && !string.IsNullOrWhiteSpace(fbd.SelectedPath))
                {
                    pathSaveFolderTxt.Text = fbd.SelectedPath;
                }
            }
        }

        private void clearLogBtn_Click(object sender, EventArgs e)
        {
            logTxt.Text = "";
        }

        private void resetBtn_Click(object sender, EventArgs e)
        {
            numTotalSuccessTxt.Text = "0";
            indexCurrentTxt.Text = "0";
            numTotalFailTxt.Text = "0";
        }

        private void signHandWordBtn_Click(object sender, EventArgs e)
        {
            if (signFile())
            {
                addSuccessCount();
            }
            else
            {
                addFailCount();
            }
        }

        private Boolean getFormData(String info)
        {
            wsdlUrl = urlTxt.Text;
            if (wsdlUrl.Trim().Length == 0)
            {
                log(info + "WSDL URL không hợp lệ");
                return false;
            }

            clientId = clientIdTxt.Text;
            if (clientId.Trim().Length == 0)
            {
                log(info + "Client Id không hợp lệ");
                return false;
            }

            clientSecret = clientSecretTxt.Text;
            if (clientSecret.Trim().Length == 0)
            {
                log(info + "Client Secret không hợp lệ");
                return false;
            }

            profileId = profileIdTxt.Text;
            if (profileId.Trim().Length == 0)
            {
                log(info + "Profile Id không hợp lệ");
                return false;
            }

            userId = userIdTxt.Text;
            if (userId.Trim().Length == 0)
            {
                log(info + "userId không hợp lệ");
                return false;
            }

            desc = descTxt.Text;
            if (desc.Trim().Length == 0)
            {
                log(info + "Mô tả ký không hợp lệ");
                return false;
            }

            app = appTxt.Text;
            if (app.Trim().Length == 0)
            {
                log(info + "Ứng dụng ký không hợp lệ");
                return false;
            }
            return true;
        }

        private Boolean signFile()
        {
            String info = "Ký lô.";
            if (!getFormData(info))
            {
                return false;
            }



            string idString = idTxt.Text;
            if (idString.Trim().Length == 0)
            {
                log(info + "ID tài liệu không hợp lệ");
                return false;
            }
            try
            {
                id = Int32.Parse(idString);
            }
            catch (Exception e)
            {
                log(info + "ID tài liệu phải là số nguyên");
                return false;
            }
            indexCurrent = 0;
            try
            {
                indexCurrent = Int32.Parse(indexCurrentTxt.Text);
            }
            catch (Exception e)
            {
                log(info + "Thứ tự yêu cầu không hợp lệ");
                return false;
            }

            String numTotal = numTotalTxt.Text;
            if (numTotal.Trim().Length == 0)
            {
                log("Tổng yêu cầu không hợp lệ");
                return false;
            }
            count = 0;
            try
            {
                count = Int32.Parse(numTotal);
            }
            catch (Exception ex)
            {
                logger.Error(ex);
                log(info + "Tổng yêu cầu không hợp lệ: " + ex.ToString());
                return false;
            }

            if (indexCurrent >= count)
            {
                log(info + "Vượt quá số lượng lô");
                return false;
            }

            indexCurrent++;
            indexCurrentTxt.Text = "" + indexCurrent;
            info += indexCurrent + ".";

            String pathDes = pathSaveFolderTxt.Text;
            String pathFile = pathFileTxt.Text;
            try
            {

                if (pathFile.Trim().Length == 0)
                {
                    log(info + "Chưa chọn file ký");
                    return false;
                }
                if (!File.Exists(pathFile.Trim()))
                {
                    log(info + "File ký không tồn tại");
                    return false;
                }
                if (pathDes.Trim().Length == 0)
                {
                    log(info + "Chưa chọn thư mục lưu");
                    return false;
                }
                if (!Directory.Exists(pathDes.Trim()))
                {
                    log(info + "thư mục lưu không tồn tại");
                    return false;
                }

                String fileName = Path.GetFileNameWithoutExtension(pathFile);
                String ext = Path.GetExtension(pathFile);
                String pathFolder = Path.GetDirectoryName(pathFile);
                String destFilepath = pathDes + @"\" + fileName + ext;
                bool exists = System.IO.File.Exists(destFilepath);
                if (exists)
                {
                    int index = 1;
                    string name_2 = fileName + "_" + index;
                    string path_2 = pathDes + @"\" + name_2 + ext;
                    while (System.IO.File.Exists(path_2))
                    {
                        index++;
                        name_2 = fileName + "_" + index;
                        path_2 = pathDes + @"\" + name_2 + ext;
                    }
                    fileName = name_2;
                }

                string signedFile = pathDes + @"\" + fileName + ext;

                // Contructor
                PdfSignerSynchronous signer = new PdfSignerSynchronous();
                //System.Security.Cryptography.X509Certificates.X509Certificate[] chain = UtilSigner.GetChainCertFromP12(textBoxFileP12.Text, textBoxPasswordFileP12.Text);


                //X509Certificate[] chain = mobileCA.GetCertificate(apID, msisdn, certSerial, fileP12, passFileP12);
                
                if (certMap == null || certMap.Count == 0 || credentialIDList == null || credentialIDList.Count == 0 || certListCbox.SelectedIndex == -1)
                {
                    log(info + "Chưa chọn CTS");
                    return false;
                }

                string credentialID = credentialIDList.ElementAt(certListCbox.SelectedIndex);
                CertBO certBO = null;
                certMap.TryGetValue(credentialID, out certBO);

                //certMap = MobileCA.getAllCertificates(userId, wsdlUrl, clientId, clientSecret, profileId, ref token);

                Org.BouncyCastle.X509.X509Certificate[] certChain = null;
                Org.BouncyCastle.X509.X509Certificate x509Cert = null;

                if (certBO.certificates != null && certBO.certificates.Length != 0)
                {
                    var certParser = new Org.BouncyCastle.X509.X509CertificateParser();
                    x509Cert = certParser.ReadCertificate(Convert.FromBase64String(certBO.certificates[0]));

                    if (certBO.certificates.Length > 1)
                    {
                        Org.BouncyCastle.X509.X509Certificate certViettelCA = certParser.ReadCertificate(Convert.FromBase64String(certBO.certificates[1]));
                        if (certViettelCA != null)
                        {
                            certChain = new X509Certificate[] { x509Cert, certViettelCA };
                        }
                    }
                }

                if (certChain == null || certChain.Length != 2)
                {
                    log(info + "Lấy Chứng thư số không thành công. Không lấy được CTS CA.");
                    return false;
                }

                // Set parameters
                signer.SigTextFormat = PdfSignerSynchronous.FORMAT_TEXT_4;
                signer.SigContact = UtilSigner.GetCNFromDN(x509Cert.SubjectDN.ToString());
                signer.SigLocation = "Hanoi";
                signer.IsMultiSignatures = true;
                signer.Visible = true;
                signer.OriginX = 10;
                signer.OriginY = 10;
                signer.CoordinateX = 300;
                signer.CoordinateY = 50;
                signer.TsaClient = null;
                signer.UseTSA = false;
                DateTime signDate = DateTime.Now;

                // Create hash file

                SignPdfFile pdfSig = new SignPdfFile();
                //Khai bao duong dan toi file pdf can ky tren web server
                //string fileFullPath = @"D:\backup\Project\Viettel CA\Ho Tro\Dot Net\Demo Mobile CA ASP DotNet\PDF\Sample.pdf";
                //ext = DateTime.Now.Ticks.ToString();
                //string signedFile = @"D:\backup\Project\Viettel CA\Ho Tro\Dot Net\Demo Mobile CA ASP DotNet\PDF\Sample_" + ext + ".pdf";
                //byte[] hash = signer.CreateHash(fileFullPath, signedFile, certChain, signDate);
                string base64Hash = HashFilePDF.GetHashTypeRectangleText(pdfSig, pathFile, certChain, HashFilePDF.HASH_ALGORITHM_SHA_256);
                //string base64Hash = HashFilePDF.GetHashTypeRectangleText2_ExistedSignatureField(fileFullPath, certChain, "Ký", "1");
                byte[] hash = Convert.FromBase64String(base64Hash);
                // Sign hash use Prikey
                String[] hashList = new String[1];
                hashList[0] = base64Hash;
                String data = app + " - " + desc;
                var dataBytes = Encoding.UTF8.GetBytes(data);
                String dataDisplay = Convert.ToBase64String(dataBytes);
                String[] signatureList = MobileCA.signHash(hashList, id, dataDisplay, clientId, clientSecret, credentialID, wsdlUrl, token);
                if (signatureList == null || signatureList.Length == 0)
                {
                    log(info + "Ký không thành công");
                    return false;
                }
                //            log(info + "Ký hash thành công: " + signature);
                log(info + "Ký hash thành công");
                var signature = signatureList[0];

                if (signature == null)
                {
                    log(info + "Phát sinh lỗi trong quá trình thực hiện chữ ký số");
                    return false;
                }


                TimestampConfig timestampConfig = new TimestampConfig();
                timestampConfig.UseTimestamp = false;
                //string signatureBase64 = Convert.ToBase64String(signature);
                if (!pdfSig.insertSignature(signature, signedFile, timestampConfig, HashFilePDF.HASH_ALGORITHM_SHA_256))
                {
                    log(info + "Insert signature into file fail.");
                    return false;
                }
                else
                {
                    log(info + "Ký thành công");
                    return true;
                }
            }
            catch (Exception ex)
            {
                logger.Error(ex);
                log(ex.ToString());
            }
            return false;
        }


        private void addSuccessCount()
        {
            String numTotal = numTotalSuccessTxt.Text;
            if (numTotal.Trim().Length == 0)
            {
                log("Tổng yêu cầu thành công không hợp lệ");
                return;
            }
            int count = 0;
            try
            {
                count = Int32.Parse(numTotal);
            }
            catch (Exception ex)
            {
                logger.Error(ex);
                log("Tổng yêu cầu thành công không hợp lệ: " + ex.ToString());
                return;
            }
            count++;
            numTotalSuccessTxt.Text = "" + count;
        }

        private void addFailCount()
        {
            String numTotal = numTotalFailTxt.Text;
            if (numTotal.Trim().Length == 0)
            {
                log("Tổng yêu cầu Fail không hợp lệ");
                return;
            }
            int count = 0;
            try
            {
                count = Int32.Parse(numTotal);
            }
            catch (Exception ex)
            {
                logger.Error(ex);
                log("Tổng yêu cầu Fail không hợp lệ: " + ex.ToString());
                return;
            }
            count++;
            numTotalFailTxt.Text = "" + count;
        }

        private void log(String log)
        {
            logger.Info(log);
            logTxt.Text = logTxt.Text + "\n" + log;
        }

        private void getCertListBtn_Click(object sender, EventArgs e)
        {
            certListCbox.Items.Clear();
            certMap.Clear();
            credentialIDList.Clear();
            String info = "Get Cert List. ";
            if (!getFormData(info))
            {
                return;
            }
            certMap = MobileCA.getAllCertificates(userId, wsdlUrl, clientId, clientSecret, profileId, ref token);

            if (certMap == null || certMap.Count == 0)
            {
                log("Không tìm thấy CTS");
                return;
            }
            //Default get the last certificate or customer select certificate
            CertBO certBO = null;
            String credentialID = null;

            Org.BouncyCastle.X509.X509Certificate[] certChain = null;
            Org.BouncyCastle.X509.X509Certificate x509Cert = null;

            foreach (KeyValuePair<string, CertBO> kvp in certMap)
            {
                Console.WriteLine("Key: {0}, Value: {1}", kvp.Key, kvp.Value);
                credentialID = kvp.Key;
                certBO = kvp.Value;
                if (certBO != null && certBO.certificates != null && certBO.certificates.Length != 0)
                {
                    var certParser = new Org.BouncyCastle.X509.X509CertificateParser();
                    x509Cert = certParser.ReadCertificate(Convert.FromBase64String(certBO.certificates[0]));

                    if (certBO.certificates.Length > 1)
                    {
                        Org.BouncyCastle.X509.X509Certificate certViettelCA = certParser.ReadCertificate(Convert.FromBase64String(certBO.certificates[1]));
                        if (certViettelCA != null)
                        {
                            certChain = new X509Certificate[] { x509Cert, certViettelCA };
                        }
                    }
                    credentialIDList.Add(credentialID); 
                    certListCbox.Items.Add(x509Cert.SerialNumber.ToString(16) + " - " + x509Cert.NotAfter.ToString("dd-MM-yyyy") + " - " + UtilSigner.GetCNFromDN(x509Cert.SubjectDN.ToString()));
                }
            }
            certListCbox.SelectedIndex = certListCbox.Items.Count - 1;
        }

    }
}
