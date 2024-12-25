/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Fees_Management_Systems;

import Fees_Management_Systems.MYSQLConnection;
import Fees_Management_Systems.NumberToWordsConverter;
import Fees_Management_Systems.PrintReciept;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class AddFees extends javax.swing.JFrame {

    /**
     * Creates new form AddFees
     */
    public void displayCashFirst()
    {
        lbl_cheque_num.setVisible(false);
        txt_cheque_num.setVisible(false);
        lbl_dd_num.setVisible(false);
          txt_dd_num.setVisible(false);
            lbl_bank_name.setVisible(false);
              txt_bank_name.setVisible(false);
              
              
    }
    
    public String insertData()
    {
         String status="";
        
        int receiptNo=Integer.parseInt(txt_receipt_num.getText());
        String studentName=txt_rec_name.getText();
        String rollNo=txt_roll_no.getText();
        String paymentMode=combo_mode_payment.getSelectedItem().toString();
        String chequeNo=txt_cheque_num.getText();
        String bankName=txt_bank_name.getText();
        String ddNo=txt_dd_num.getText();
        String courseName=txt_head.getText();
        String gstin=lbl_gstin1.getText();
        float totalAmount=Float.parseFloat(txt_total.getText());
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String date=dateFormat.format(date_c.getDate());
        float initialAmount=Float.parseFloat(txt_amount.getText());
        float cgst=Float.parseFloat(txt_cgst.getText());
        float sgst=Float.parseFloat(txt_sgst.getText());
        String totalInWords=txt_total_in_words.getText();
        String remark=txt_area_remark.getText();
        int year1=Integer.parseInt(txt_from_year.getText());
        int year2=Integer.parseInt(txt_to_year.getText());
        
       
        try
        {
            
             Connection con=MYSQLConnection.getmysqlConnection();
           
             PreparedStatement st=con.prepareStatement("insert into fees_details values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
             st.setInt(1, receiptNo);
             st.setString(2, studentName);
             st.setString(3, rollNo);
             st.setString(4, paymentMode);
             st.setString(5, chequeNo);
             st.setString(6, bankName);
             st.setString(7, ddNo);
             st.setString(8, courseName);
             st.setString(9, gstin);
             st.setFloat(10, totalAmount);
             st.setString(11, date);
             st.setFloat(12, initialAmount);
             st.setFloat(13, cgst);
             st.setFloat(14, sgst);
             st.setString(15, totalInWords);
             st.setString(16, remark);
             st.setInt(17, year1);
             st.setInt(18, year2);
             
             int rowCount=st.executeUpdate();
             
             if(rowCount==1)
             {
                status="success";
             }
             else
             {
                 status="failed";
             }
             
        }
        catch(Exception e1)
        {
            e1.printStackTrace();
        }
        return status;
    }
    
    public AddFees() {
        initComponents();
        displayCashFirst();
        fillComboBox();
        fillComboBoxs();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        int rollNo=getRollNo();
        txt_roll_no.setText(Integer.toString(rollNo));
        int receiptNo=getRNo();
        txt_receipt_num.setText(Integer.toString(receiptNo));
    }
    
     boolean validation()
    {
    if(txt_rec_name.getText().equals(""))
    {
        JOptionPane.showMessageDialog(this, "Please Enter Receiver Name First.");
        return false;
    }
    if(date_c.getDate()==null)
    {
         JOptionPane.showMessageDialog(this, "Please Enter Date.");
        return false;
    }
     if(txt_amount.getText().equals("") || txt_amount.getText().matches("[0-9]+")==false)
    {
        JOptionPane.showMessageDialog(this, "Please Enter Amount(in number).");
        return false;
    }
     if(combo_mode_payment.getSelectedItem().toString().equalsIgnoreCase("cheque"))
     {
         if(txt_cheque_num.getText().equals(""))
    {
        JOptionPane.showMessageDialog(this, "Please Enter Cheque Number.");
        return false;
    }
         if(txt_bank_name.getText().equals(""))
    {
        JOptionPane.showMessageDialog(this, "Please Enter Bank Name.");
        return false;
    }
         
     }
      if(combo_mode_payment.getSelectedItem().toString().equalsIgnoreCase("dd"))
     {
         if(txt_dd_num.getText().equals(""))
    {
        JOptionPane.showMessageDialog(this, "Please Enter DD Number.");
        return false;
    }
         if(txt_bank_name.getText().equals(""))
    {
        JOptionPane.showMessageDialog(this, "Please Enter Bank Name.");
        return false;
    }
         
     }
      if(combo_mode_payment.getSelectedItem().toString().equalsIgnoreCase("card"))
      {
          if(txt_bank_name.getText().equals(""))
    {
        JOptionPane.showMessageDialog(this, "Please Enter Bank Name.");
        return false;
    }
      }
     
    
    return true;
    }
    
    public void fillComboBox()
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
             String url ="jdbc:mysql://localhost:3306/ntfmsdatabase?zeroDateTimeBehavior=CONVERT_TO_NULL";
             Connection con=DriverManager.getConnection(url,"root","system");
             String sql="select cname from course ";
             PreparedStatement st=con.prepareStatement(sql);
             ResultSet rs=st.executeQuery(sql);
             while(rs.next())
             {
                 combo_c.addItem(rs.getString("cname"));
             }
             
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
        }
    }
    
    public void fillComboBoxs()
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
             String url ="jdbc:mysql://localhost:3306/ntfmsdatabase?zeroDateTimeBehavior=CONVERT_TO_NULL";
             Connection con=DriverManager.getConnection(url,"root","system");
             String sql="select cost from course ";
             PreparedStatement st=con.prepareStatement(sql);
             ResultSet rs=st.executeQuery(sql);
             while(rs.next())
             {
                 combo_b.addItem(rs.getString("cost"));
             }
             
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
        }
    }
    
     public int getRNo()
    {
        int receiptNo =0;
        try
        {
            Connection con =MYSQLConnection.getmysqlConnection();
            PreparedStatement pst=con.prepareStatement("select max(reciept_no) from fees_details ");
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()== true)
             {
                 receiptNo =rs.getInt(1);
             }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
        }
        return receiptNo+1;
    }
     
     public int getRollNo()
    {
        int rollNo =0;
        try
        {
            Connection con =MYSQLConnection.getmysqlConnection();
            PreparedStatement pst=con.prepareStatement("select max(roll_no) from fees_details ");
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()== true)
             {
                 rollNo =rs.getInt(1);
             }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
        }
        return rollNo+1;
    }
     

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton4 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lbl_gstin1 = new javax.swing.JLabel();
        lbl_dd_num = new javax.swing.JLabel();
        lbl_mode_payment = new javax.swing.JLabel();
        lbl_cheque_num = new javax.swing.JLabel();
        lbl_bank_name = new javax.swing.JLabel();
        txt_cheque_num = new javax.swing.JTextField();
        txt_bank_name = new javax.swing.JTextField();
        date_c = new com.toedter.calendar.JDateChooser();
        lbl_gstin = new javax.swing.JLabel();
        lbl_date = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lbl_r_n = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txt_rec_name = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txt_roll_no = new javax.swing.JTextField();
        txt_total = new javax.swing.JTextField();
        txt_head = new javax.swing.JTextField();
        txt_amount = new javax.swing.JTextField();
        txt_cgst = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        txt_total_in_words = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txt_sgst = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_area_remark = new javax.swing.JTextArea();
        jLabel23 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        btn_print = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        txt_to_year = new javax.swing.JTextField();
        txt_from_year = new javax.swing.JTextField();
        lbl_receipt_num = new javax.swing.JLabel();
        txt_receipt_num = new javax.swing.JTextField();
        combo_mode_payment = new javax.swing.JComboBox<>();
        txt_dd_num = new javax.swing.JTextField();
        combo_c = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        combo_b = new javax.swing.JComboBox<>();
        jButton7 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton4.setBackground(new java.awt.Color(102, 102, 102));
        jButton4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton4.setText("Search Record");
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(102, 102, 102));
        jButton3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton3.setText("View All Record");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(102, 102, 102));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton2.setText("Edit Course");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(102, 102, 102));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton1.setText("Home");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_gstin1.setText("44AVC8863GHJ");
        jPanel2.add(lbl_gstin1, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 70, 90, 21));

        lbl_dd_num.setText("DD No :");
        jPanel2.add(lbl_dd_num, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 105, 21));

        lbl_mode_payment.setText("Mode of Payment :");
        jPanel2.add(lbl_mode_payment, new org.netbeans.lib.awtextra.AbsoluteConstraints(31, 70, 105, 21));

        lbl_cheque_num.setText("Cheque No :");
        jPanel2.add(lbl_cheque_num, new org.netbeans.lib.awtextra.AbsoluteConstraints(31, 109, 105, 21));

        lbl_bank_name.setText("Bank Name :");
        jPanel2.add(lbl_bank_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 78, 21));

        txt_cheque_num.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel2.add(txt_cheque_num, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 110, 90, -1));

        txt_bank_name.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel2.add(txt_bank_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 150, 220, -1));
        jPanel2.add(date_c, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 30, 140, 30));

        lbl_gstin.setText("GSTIN :");
        jPanel2.add(lbl_gstin, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 70, 70, 21));

        lbl_date.setText("Date:");
        jPanel2.add(lbl_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 30, 70, 21));

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_r_n.setText("Recieved From :");
        jPanel3.add(lbl_r_n, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 100, 21));

        jLabel13.setText("  to");
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 50, 78, 21));

        txt_rec_name.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel3.add(txt_rec_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 3, 230, 30));

        jLabel9.setText("Roll No :");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 10, 70, 21));

        jSeparator1.setForeground(new java.awt.Color(204, 204, 204));
        jPanel3.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 720, 16));

        jSeparator2.setForeground(new java.awt.Color(204, 204, 204));
        jPanel3.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 720, 16));

        jLabel15.setText("Amount (rs)");
        jPanel3.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 110, 78, 21));

        jLabel16.setText("Sr. No");
        jPanel3.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 78, 21));

        jLabel17.setText("Receiver Signature");
        jPanel3.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 390, 110, 21));

        txt_roll_no.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel3.add(txt_roll_no, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 10, 160, -1));

        txt_total.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel3.add(txt_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 300, 130, -1));

        txt_head.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_head.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_headActionPerformed(evt);
            }
        });
        jPanel3.add(txt_head, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 160, 300, -1));

        txt_amount.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_amount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_amountActionPerformed(evt);
            }
        });
        jPanel3.add(txt_amount, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 160, 130, -1));

        txt_cgst.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel3.add(txt_cgst, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 210, 130, -1));
        jPanel3.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 300, -1, -1));

        jLabel18.setText("Heads");
        jPanel3.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 78, 21));

        jLabel19.setText("CGST 7%");
        jPanel3.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 210, 78, 21));
        jPanel3.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 310, -1, -1));
        jPanel3.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 380, 200, 20));

        txt_total_in_words.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jPanel3.add(txt_total_in_words, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 300, 300, -1));

        jLabel20.setText("SGST 7%");
        jPanel3.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 250, 78, 21));

        jLabel21.setText("Total :");
        jPanel3.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 300, 40, 21));

        txt_sgst.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel3.add(txt_sgst, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 250, 130, -1));

        jLabel22.setText("Total In Words :");
        jPanel3.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 80, 21));

        txt_area_remark.setColumns(20);
        txt_area_remark.setRows(5);
        jScrollPane1.setViewportView(txt_area_remark);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 330, 300, 100));

        jLabel23.setText("Remark :");
        jPanel3.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 80, 21));
        jPanel3.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 290, 210, 20));

        btn_print.setBackground(new java.awt.Color(102, 102, 102));
        btn_print.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_print.setText("Print");
        btn_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_printActionPerformed(evt);
            }
        });
        jPanel3.add(btn_print, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 430, 70, -1));

        jLabel10.setText("Recieved From For The Given Year :");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 200, 21));

        txt_to_year.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel3.add(txt_to_year, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 50, 90, -1));

        txt_from_year.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel3.add(txt_from_year, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 50, 90, -1));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 820, 480));

        lbl_receipt_num.setText("Receipt No : ");
        jPanel2.add(lbl_receipt_num, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 78, 21));

        txt_receipt_num.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel2.add(txt_receipt_num, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 30, 90, -1));

        combo_mode_payment.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cheque", "DD", "CASH", "Card", " " }));
        combo_mode_payment.setSelectedIndex(2);
        combo_mode_payment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_mode_paymentActionPerformed(evt);
            }
        });
        jPanel2.add(combo_mode_payment, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 70, 93, 21));

        txt_dd_num.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel2.add(txt_dd_num, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 110, 90, -1));

        combo_c.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_cActionPerformed(evt);
            }
        });
        jPanel2.add(combo_c, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 110, 93, 21));

        jLabel14.setText("Course :");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 110, 78, 21));

        combo_b.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_bActionPerformed(evt);
            }
        });
        jPanel2.add(combo_b, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 110, 93, 21));

        jButton7.setBackground(new java.awt.Color(102, 102, 102));
        jButton7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton7.setText("Back");
        jButton7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton7MouseClicked(evt);
            }
        });
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(102, 102, 102));
        jButton6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton6.setText("Update Fees");
        jButton6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton6MouseClicked(evt);
            }
        });
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(102, 102, 102));
        jButton5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton5.setText("Logout");
        jButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton5MouseClicked(evt);
            }
        });
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jButton6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton3))))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
        SearchRecord record = new SearchRecord();
        record.setVisible(true);
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4MouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        ViewAllRecords records = new ViewAllRecords();
        records.setVisible(true);
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        EditCourse course = new EditCourse();
        course.setVisible(true);
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        HomePage home = new HomePage();
        home.setVisible(true);
        this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txt_headActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_headActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_txt_headActionPerformed

    private void txt_amountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_amountActionPerformed
        String s1= txt_amount.getText();
        float amt=Float.parseFloat(s1);

        float cgst=(float)(amt*0.07);
        float sgst=(float)(amt*0.07);

        txt_cgst.setText(Float.toString(cgst));
        txt_sgst.setText(Float.toString(sgst));

        float t=amt+cgst+sgst;
        txt_total.setText(Float.toString(t));
        txt_total_in_words.setText( NumberToWordsConverter.convert((int)t) +  " only");

        // TODO add your handling code here:
    }//GEN-LAST:event_txt_amountActionPerformed

    private void btn_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_printActionPerformed

       //if(validation()==true)
        {
            String s=insertData();
            if(s.equals("success"))
            {
                JOptionPane.showMessageDialog(this, "Record inserted successfully");
                PrintReciept p = new PrintReciept();
                p.setVisible(true);
                this.dispose();
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Record not inserted successfully");
            }
        }//
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_printActionPerformed

    private void combo_mode_paymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_mode_paymentActionPerformed
        if(combo_mode_payment.getSelectedIndex()==1)
        {
            lbl_dd_num.setVisible(true);
            txt_dd_num.setVisible(true);
            lbl_bank_name.setVisible(true);
            txt_bank_name.setVisible(true);

            lbl_cheque_num.setVisible(false);
            txt_cheque_num.setVisible(false);
        }
        if(combo_mode_payment.getSelectedIndex()==2)
        {
            lbl_cheque_num.setVisible(false);
            txt_cheque_num.setVisible(false);
            lbl_dd_num.setVisible(false);
            txt_dd_num.setVisible(false);

            lbl_bank_name.setVisible(false);
            txt_bank_name.setVisible(false);

        }
        if(combo_mode_payment.getSelectedIndex()==0)
        {
            lbl_cheque_num.setVisible(true);
            txt_cheque_num.setVisible(true);
            lbl_dd_num.setVisible(false);
            txt_dd_num.setVisible(false);

            lbl_bank_name.setVisible(true);
            txt_bank_name.setVisible(true);

        }
        if(combo_mode_payment.getSelectedItem().equals("Card"))
        {
            lbl_cheque_num.setVisible(false);
            txt_cheque_num.setVisible(false);
            lbl_dd_num.setVisible(false);
            txt_dd_num.setVisible(false);

            lbl_bank_name.setVisible(true);
            txt_bank_name.setVisible(true);

        }

        // TODO add your handling code here:
    }//GEN-LAST:event_combo_mode_paymentActionPerformed

    private void combo_cActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_cActionPerformed
        String s1=combo_c.getSelectedItem().toString();
        txt_head.setText(s1);

        // TODO add your handling code here:
    }//GEN-LAST:event_combo_cActionPerformed

    private void jButton7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton7MouseClicked
        HomePage home = new HomePage();
        home.setVisible(true);
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7MouseClicked

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton6MouseClicked
        UpdateFeesDetails fee = new UpdateFeesDetails();
        fee.setVisible(true);
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6MouseClicked

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton5MouseClicked
        Login in = new Login();
        in.setVisible(true);
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5MouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void combo_bActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_bActionPerformed
   String s2=combo_b.getSelectedItem().toString();
             txt_amount.setText(s2);
                   // TODO add your handling code here:
    }//GEN-LAST:event_combo_bActionPerformed

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
            java.util.logging.Logger.getLogger(AddFees.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddFees.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddFees.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddFees.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddFees().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_print;
    private javax.swing.JComboBox<String> combo_b;
    private javax.swing.JComboBox<String> combo_c;
    private javax.swing.JComboBox<String> combo_mode_payment;
    private com.toedter.calendar.JDateChooser date_c;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JLabel lbl_bank_name;
    private javax.swing.JLabel lbl_cheque_num;
    private javax.swing.JLabel lbl_date;
    private javax.swing.JLabel lbl_dd_num;
    private javax.swing.JLabel lbl_gstin;
    private javax.swing.JLabel lbl_gstin1;
    private javax.swing.JLabel lbl_mode_payment;
    private javax.swing.JLabel lbl_r_n;
    private javax.swing.JLabel lbl_receipt_num;
    private javax.swing.JTextField txt_amount;
    private javax.swing.JTextArea txt_area_remark;
    private javax.swing.JTextField txt_bank_name;
    private javax.swing.JTextField txt_cgst;
    private javax.swing.JTextField txt_cheque_num;
    private javax.swing.JTextField txt_dd_num;
    private javax.swing.JTextField txt_from_year;
    private javax.swing.JTextField txt_head;
    private javax.swing.JTextField txt_rec_name;
    private javax.swing.JTextField txt_receipt_num;
    private javax.swing.JTextField txt_roll_no;
    private javax.swing.JTextField txt_sgst;
    private javax.swing.JTextField txt_to_year;
    private javax.swing.JTextField txt_total;
    private javax.swing.JTextField txt_total_in_words;
    // End of variables declaration//GEN-END:variables
}
