namespace DEMO_CLOUD_CA_DOTNET
{
    partial class DemoCloudCA
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.urlTxt = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.selectFolderBtn = new System.Windows.Forms.Button();
            this.profileIdTxt = new System.Windows.Forms.TextBox();
            this.selectFileBtn = new System.Windows.Forms.Button();
            this.logTxt = new System.Windows.Forms.RichTextBox();
            this.userIdTxt = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.indexCurrentTxt = new System.Windows.Forms.TextBox();
            this.label4 = new System.Windows.Forms.Label();
            this.numTotalFailTxt = new System.Windows.Forms.TextBox();
            this.label5 = new System.Windows.Forms.Label();
            this.clientIdTxt = new System.Windows.Forms.TextBox();
            this.label6 = new System.Windows.Forms.Label();
            this.clientSecretTxt = new System.Windows.Forms.TextBox();
            this.label7 = new System.Windows.Forms.Label();
            this.numTotalTxt = new System.Windows.Forms.TextBox();
            this.label8 = new System.Windows.Forms.Label();
            this.numTotalSuccessTxt = new System.Windows.Forms.TextBox();
            this.label9 = new System.Windows.Forms.Label();
            this.appTxt = new System.Windows.Forms.TextBox();
            this.label10 = new System.Windows.Forms.Label();
            this.descTxt = new System.Windows.Forms.TextBox();
            this.label11 = new System.Windows.Forms.Label();
            this.idTxt = new System.Windows.Forms.TextBox();
            this.label12 = new System.Windows.Forms.Label();
            this.pathFileTxt = new System.Windows.Forms.TextBox();
            this.label14 = new System.Windows.Forms.Label();
            this.pathSaveFolderTxt = new System.Windows.Forms.TextBox();
            this.label16 = new System.Windows.Forms.Label();
            this.label13 = new System.Windows.Forms.Label();
            this.getCertListBtn = new System.Windows.Forms.Button();
            this.certListCbox = new System.Windows.Forms.ComboBox();
            this.clearLogBtn = new System.Windows.Forms.Button();
            this.signHandWordBtn = new System.Windows.Forms.Button();
            this.resetBtn = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // urlTxt
            // 
            this.urlTxt.Location = new System.Drawing.Point(91, 9);
            this.urlTxt.Name = "urlTxt";
            this.urlTxt.Size = new System.Drawing.Size(200, 20);
            this.urlTxt.TabIndex = 12;
            this.urlTxt.Text = "https://remotesigning.viettel.vn:8773";
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(13, 12);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(29, 13);
            this.label1.TabIndex = 11;
            this.label1.Text = "URL";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(13, 47);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(56, 13);
            this.label2.TabIndex = 13;
            this.label2.Text = "Profile_ID:";
            // 
            // selectFolderBtn
            // 
            this.selectFolderBtn.Location = new System.Drawing.Point(516, 255);
            this.selectFolderBtn.Name = "selectFolderBtn";
            this.selectFolderBtn.Size = new System.Drawing.Size(92, 23);
            this.selectFolderBtn.TabIndex = 16;
            this.selectFolderBtn.Text = "Choose Save";
            this.selectFolderBtn.UseVisualStyleBackColor = true;
            this.selectFolderBtn.Click += new System.EventHandler(this.selectFolderBtn_Click);
            // 
            // profileIdTxt
            // 
            this.profileIdTxt.Location = new System.Drawing.Point(91, 44);
            this.profileIdTxt.Name = "profileIdTxt";
            this.profileIdTxt.Size = new System.Drawing.Size(200, 20);
            this.profileIdTxt.TabIndex = 14;
            this.profileIdTxt.Text = "adss:ras:profile:001";
            // 
            // selectFileBtn
            // 
            this.selectFileBtn.Location = new System.Drawing.Point(516, 221);
            this.selectFileBtn.Name = "selectFileBtn";
            this.selectFileBtn.Size = new System.Drawing.Size(92, 23);
            this.selectFileBtn.TabIndex = 15;
            this.selectFileBtn.Text = "Choose File";
            this.selectFileBtn.UseVisualStyleBackColor = true;
            this.selectFileBtn.Click += new System.EventHandler(this.selectFileBtn_Click);
            // 
            // logTxt
            // 
            this.logTxt.Location = new System.Drawing.Point(16, 362);
            this.logTxt.Name = "logTxt";
            this.logTxt.Size = new System.Drawing.Size(592, 186);
            this.logTxt.TabIndex = 17;
            this.logTxt.Text = "";
            // 
            // userIdTxt
            // 
            this.userIdTxt.Location = new System.Drawing.Point(91, 79);
            this.userIdTxt.Name = "userIdTxt";
            this.userIdTxt.Size = new System.Drawing.Size(200, 20);
            this.userIdTxt.TabIndex = 19;
            this.userIdTxt.Text = "MST_0100109106-998";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(13, 82);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(49, 13);
            this.label3.TabIndex = 18;
            this.label3.Text = "User_ID:";
            // 
            // indexCurrentTxt
            // 
            this.indexCurrentTxt.Location = new System.Drawing.Point(91, 114);
            this.indexCurrentTxt.Name = "indexCurrentTxt";
            this.indexCurrentTxt.ReadOnly = true;
            this.indexCurrentTxt.Size = new System.Drawing.Size(200, 20);
            this.indexCurrentTxt.TabIndex = 21;
            this.indexCurrentTxt.Text = "0";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(13, 117);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(58, 13);
            this.label4.TabIndex = 20;
            this.label4.Text = "Thứ tự YC:";
            // 
            // numTotalFailTxt
            // 
            this.numTotalFailTxt.Location = new System.Drawing.Point(91, 150);
            this.numTotalFailTxt.Name = "numTotalFailTxt";
            this.numTotalFailTxt.ReadOnly = true;
            this.numTotalFailTxt.Size = new System.Drawing.Size(200, 20);
            this.numTotalFailTxt.TabIndex = 23;
            this.numTotalFailTxt.Text = "0";
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(13, 153);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(40, 13);
            this.label5.TabIndex = 22;
            this.label5.Text = "Số Lỗi:";
            // 
            // clientIdTxt
            // 
            this.clientIdTxt.Location = new System.Drawing.Point(408, 12);
            this.clientIdTxt.Name = "clientIdTxt";
            this.clientIdTxt.Size = new System.Drawing.Size(200, 20);
            this.clientIdTxt.TabIndex = 25;
            this.clientIdTxt.Text = "samples_test_client";
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Location = new System.Drawing.Point(330, 15);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(53, 13);
            this.label6.TabIndex = 24;
            this.label6.Text = "Client_ID:";
            // 
            // clientSecretTxt
            // 
            this.clientSecretTxt.Location = new System.Drawing.Point(408, 44);
            this.clientSecretTxt.Name = "clientSecretTxt";
            this.clientSecretTxt.Size = new System.Drawing.Size(200, 20);
            this.clientSecretTxt.TabIndex = 27;
            this.clientSecretTxt.Text = "205640fd6ea8c7d80bb91c630b52d286d21ee511";
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Location = new System.Drawing.Point(330, 47);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(73, 13);
            this.label7.TabIndex = 26;
            this.label7.Text = "Client_Secret:";
            // 
            // numTotalTxt
            // 
            this.numTotalTxt.Location = new System.Drawing.Point(408, 79);
            this.numTotalTxt.Name = "numTotalTxt";
            this.numTotalTxt.Size = new System.Drawing.Size(200, 20);
            this.numTotalTxt.TabIndex = 29;
            this.numTotalTxt.Text = "3";
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.label8.Location = new System.Drawing.Point(330, 82);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(76, 13);
            this.label8.TabIndex = 28;
            this.label8.Text = "Tổng yêu cầu:";
            // 
            // numTotalSuccessTxt
            // 
            this.numTotalSuccessTxt.Location = new System.Drawing.Point(408, 114);
            this.numTotalSuccessTxt.Name = "numTotalSuccessTxt";
            this.numTotalSuccessTxt.ReadOnly = true;
            this.numTotalSuccessTxt.Size = new System.Drawing.Size(200, 20);
            this.numTotalSuccessTxt.TabIndex = 31;
            this.numTotalSuccessTxt.Text = "0";
            // 
            // label9
            // 
            this.label9.AutoSize = true;
            this.label9.Location = new System.Drawing.Point(330, 117);
            this.label9.Name = "label9";
            this.label9.Size = new System.Drawing.Size(80, 13);
            this.label9.TabIndex = 30;
            this.label9.Text = "Số thành công:";
            // 
            // appTxt
            // 
            this.appTxt.Location = new System.Drawing.Point(408, 150);
            this.appTxt.Name = "appTxt";
            this.appTxt.Size = new System.Drawing.Size(200, 20);
            this.appTxt.TabIndex = 33;
            this.appTxt.Text = "vOffice";
            // 
            // label10
            // 
            this.label10.AutoSize = true;
            this.label10.Location = new System.Drawing.Point(330, 153);
            this.label10.Name = "label10";
            this.label10.Size = new System.Drawing.Size(71, 13);
            this.label10.TabIndex = 32;
            this.label10.Text = "Ứng dụng ký:";
            // 
            // descTxt
            // 
            this.descTxt.Location = new System.Drawing.Point(408, 187);
            this.descTxt.Name = "descTxt";
            this.descTxt.Size = new System.Drawing.Size(200, 20);
            this.descTxt.TabIndex = 37;
            this.descTxt.Text = "Transaction ID 123 - Demo Sign Cloud CA";
            // 
            // label11
            // 
            this.label11.AutoSize = true;
            this.label11.Location = new System.Drawing.Point(330, 190);
            this.label11.Name = "label11";
            this.label11.Size = new System.Drawing.Size(51, 13);
            this.label11.TabIndex = 36;
            this.label11.Text = "Mô tả ký:";
            // 
            // idTxt
            // 
            this.idTxt.Location = new System.Drawing.Point(91, 187);
            this.idTxt.Name = "idTxt";
            this.idTxt.Size = new System.Drawing.Size(200, 20);
            this.idTxt.TabIndex = 35;
            this.idTxt.Text = "123";
            // 
            // label12
            // 
            this.label12.AutoSize = true;
            this.label12.Location = new System.Drawing.Point(13, 190);
            this.label12.Name = "label12";
            this.label12.Size = new System.Drawing.Size(21, 13);
            this.label12.TabIndex = 34;
            this.label12.Text = "ID:";
            // 
            // pathFileTxt
            // 
            this.pathFileTxt.Location = new System.Drawing.Point(91, 223);
            this.pathFileTxt.Name = "pathFileTxt";
            this.pathFileTxt.Size = new System.Drawing.Size(408, 20);
            this.pathFileTxt.TabIndex = 39;
            this.pathFileTxt.Text = "C:\\Users\\HoTro\\Documents\\sample.pdf";
            // 
            // label14
            // 
            this.label14.AutoSize = true;
            this.label14.Location = new System.Drawing.Point(13, 226);
            this.label14.Name = "label14";
            this.label14.Size = new System.Drawing.Size(23, 13);
            this.label14.TabIndex = 38;
            this.label14.Text = "File";
            // 
            // pathSaveFolderTxt
            // 
            this.pathSaveFolderTxt.Location = new System.Drawing.Point(91, 257);
            this.pathSaveFolderTxt.Name = "pathSaveFolderTxt";
            this.pathSaveFolderTxt.Size = new System.Drawing.Size(408, 20);
            this.pathSaveFolderTxt.TabIndex = 43;
            this.pathSaveFolderTxt.Text = "C:\\Users\\HoTro\\Documents\\Test";
            // 
            // label16
            // 
            this.label16.AutoSize = true;
            this.label16.Location = new System.Drawing.Point(13, 260);
            this.label16.Name = "label16";
            this.label16.Size = new System.Drawing.Size(28, 13);
            this.label16.TabIndex = 42;
            this.label16.Text = "Lưu:";
            // 
            // label13
            // 
            this.label13.AutoSize = true;
            this.label13.Location = new System.Drawing.Point(13, 293);
            this.label13.Name = "label13";
            this.label13.Size = new System.Drawing.Size(31, 13);
            this.label13.TabIndex = 45;
            this.label13.Text = "CTS:";
            // 
            // getCertListBtn
            // 
            this.getCertListBtn.Location = new System.Drawing.Point(516, 288);
            this.getCertListBtn.Name = "getCertListBtn";
            this.getCertListBtn.Size = new System.Drawing.Size(92, 23);
            this.getCertListBtn.TabIndex = 44;
            this.getCertListBtn.Text = "Get Cert List";
            this.getCertListBtn.UseVisualStyleBackColor = true;
            this.getCertListBtn.Click += new System.EventHandler(this.getCertListBtn_Click);
            // 
            // certListCbox
            // 
            this.certListCbox.FormattingEnabled = true;
            this.certListCbox.Location = new System.Drawing.Point(91, 290);
            this.certListCbox.Name = "certListCbox";
            this.certListCbox.Size = new System.Drawing.Size(408, 21);
            this.certListCbox.TabIndex = 46;
            // 
            // clearLogBtn
            // 
            this.clearLogBtn.Location = new System.Drawing.Point(16, 333);
            this.clearLogBtn.Name = "clearLogBtn";
            this.clearLogBtn.Size = new System.Drawing.Size(92, 23);
            this.clearLogBtn.TabIndex = 47;
            this.clearLogBtn.Text = "Xóa log";
            this.clearLogBtn.UseVisualStyleBackColor = true;
            this.clearLogBtn.Click += new System.EventHandler(this.clearLogBtn_Click);
            // 
            // signHandWordBtn
            // 
            this.signHandWordBtn.Location = new System.Drawing.Point(167, 333);
            this.signHandWordBtn.Name = "signHandWordBtn";
            this.signHandWordBtn.Size = new System.Drawing.Size(92, 23);
            this.signHandWordBtn.TabIndex = 48;
            this.signHandWordBtn.Text = "Ký số";
            this.signHandWordBtn.UseVisualStyleBackColor = true;
            this.signHandWordBtn.Click += new System.EventHandler(this.signHandWordBtn_Click);
            // 
            // resetBtn
            // 
            this.resetBtn.Location = new System.Drawing.Point(483, 333);
            this.resetBtn.Name = "resetBtn";
            this.resetBtn.Size = new System.Drawing.Size(125, 23);
            this.resetBtn.TabIndex = 50;
            this.resetBtn.Text = "Reset số yêu cầu";
            this.resetBtn.UseVisualStyleBackColor = true;
            this.resetBtn.Click += new System.EventHandler(this.resetBtn_Click);
            // 
            // DemoCloudCA
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(634, 560);
            this.Controls.Add(this.resetBtn);
            this.Controls.Add(this.signHandWordBtn);
            this.Controls.Add(this.clearLogBtn);
            this.Controls.Add(this.certListCbox);
            this.Controls.Add(this.label13);
            this.Controls.Add(this.getCertListBtn);
            this.Controls.Add(this.pathSaveFolderTxt);
            this.Controls.Add(this.label16);
            this.Controls.Add(this.pathFileTxt);
            this.Controls.Add(this.label14);
            this.Controls.Add(this.descTxt);
            this.Controls.Add(this.label11);
            this.Controls.Add(this.idTxt);
            this.Controls.Add(this.label12);
            this.Controls.Add(this.appTxt);
            this.Controls.Add(this.label10);
            this.Controls.Add(this.numTotalSuccessTxt);
            this.Controls.Add(this.label9);
            this.Controls.Add(this.numTotalTxt);
            this.Controls.Add(this.label8);
            this.Controls.Add(this.clientSecretTxt);
            this.Controls.Add(this.label7);
            this.Controls.Add(this.clientIdTxt);
            this.Controls.Add(this.label6);
            this.Controls.Add(this.numTotalFailTxt);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.indexCurrentTxt);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.userIdTxt);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.logTxt);
            this.Controls.Add(this.urlTxt);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.selectFolderBtn);
            this.Controls.Add(this.profileIdTxt);
            this.Controls.Add(this.selectFileBtn);
            this.Name = "DemoCloudCA";
            this.Text = "Demo Cloud-CA";
            this.Load += new System.EventHandler(this.Form1_Load);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.TextBox urlTxt;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Button selectFolderBtn;
        private System.Windows.Forms.TextBox profileIdTxt;
        private System.Windows.Forms.Button selectFileBtn;
        private System.Windows.Forms.RichTextBox logTxt;
        private System.Windows.Forms.TextBox userIdTxt;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.TextBox indexCurrentTxt;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.TextBox numTotalFailTxt;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.TextBox clientIdTxt;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.TextBox clientSecretTxt;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.TextBox numTotalTxt;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.TextBox numTotalSuccessTxt;
        private System.Windows.Forms.Label label9;
        private System.Windows.Forms.TextBox appTxt;
        private System.Windows.Forms.Label label10;
        private System.Windows.Forms.TextBox descTxt;
        private System.Windows.Forms.Label label11;
        private System.Windows.Forms.TextBox idTxt;
        private System.Windows.Forms.Label label12;
        private System.Windows.Forms.TextBox pathFileTxt;
        private System.Windows.Forms.Label label14;
        private System.Windows.Forms.TextBox pathSaveFolderTxt;
        private System.Windows.Forms.Label label16;
        private System.Windows.Forms.Label label13;
        private System.Windows.Forms.Button getCertListBtn;
        private System.Windows.Forms.ComboBox certListCbox;
        private System.Windows.Forms.Button clearLogBtn;
        private System.Windows.Forms.Button signHandWordBtn;
        private System.Windows.Forms.Button resetBtn;
    }
}

