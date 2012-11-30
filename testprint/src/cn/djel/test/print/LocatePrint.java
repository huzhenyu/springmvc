package cn.djel.test.print;



import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import javax.swing.JOptionPane;

/**
* java��λ��ӡ���Ѵ�ӡ���ݴ�ָ���ĵط���
* 
* @author lyb
* 
*/
public class LocatePrint implements Printable {
   private int PAGES = 1;

   private String  printStr = "��ӡ��������";

   /*
   * Graphicָ����ӡ��ͼ�λ�����PageFormatָ����ӡҳ��ʽ��ҳ���С�Ե�Ϊ������λ��
   * 1��Ϊ1Ӣ���1/72��1Ӣ��Ϊ25.4���ס�A4ֽ����Ϊ595��842�㣩��pageָ��ҳ��
   */
   public int print(Graphics gp, PageFormat pf, int page)
           throws PrinterException {
       Graphics2D g2 = (Graphics2D) gp;
       g2.setPaint(Color.black); // ���ô�ӡ��ɫΪ��ɫ
       if (page >= PAGES) // ����ӡҳ�Ŵ�����Ҫ��ӡ����ҳ��ʱ����ӡ��������
           return Printable.NO_SUCH_PAGE;
       g2.translate(pf.getImageableX(), pf.getImageableY());// ת�����꣬ȷ����ӡ�߽�
       Font font = new Font("����", Font.PLAIN, 24);// ��������
       g2.setFont(font);
       // ��ӡ��ǰҳ�ı�
       int printFontCount = printStr.length();// ��ӡ����
       int printFontSize = font.getSize();// Font �İ�ֵ��С
       float printX = 595 / 2; // �����ַ�����Xҳ������
       float printY = 842 / 2; // �����ַ�����Yҳ������
       float printMX = printX - (printFontCount * printFontSize / 2);// ��ӡ�����м�
       float printMY = printY - printFontSize / 2;// ��ӡ�����м�
       
       
       g2.drawString(printStr, printMX, printMY); // �����ӡÿһ���ı���ͬʱ��ֽ��λ
       
       
       g2.drawString(printStr, printMX - printFontSize * printFontCount,
               printMY + printFontSize); // �����ӡÿһ���ı���ͬʱ��ֽ��λ
       
       g2.drawString(printStr, printMX + printFontSize * printFontCount,
               printMY + printFontSize); // �����ӡÿһ���ı���ͬʱ��ֽ��λ
       
       
       g2.drawString(printStr, printMX, printMY + printFontSize * 2); // �����ӡÿһ���ı���ͬʱ��ֽ��λ
       
       
       return Printable.PAGE_EXISTS; // ���ڴ�ӡҳʱ��������ӡ����
   }

   // ��ӡ���ݵ�ָ��λ��
   public void printContent() {
       printStr = "��ӡ��������";// ��ȡ��Ҫ��ӡ��Ŀ���ı�
       if (printStr != null && printStr.length() > 0) // ����ӡ���ݲ�Ϊ��ʱ
       {
           PAGES = 1; // ��ȡ��ӡ��ҳ��
           // ָ����ӡ�����ʽ
           DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
           // ��λĬ�ϵĴ�ӡ����
           PrintService printService = PrintServiceLookup
                   .lookupDefaultPrintService();
           // ������ӡ��ҵ
           DocPrintJob job = printService.createPrintJob();
           // ���ô�ӡ����
           PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
           // ����ֽ�Ŵ�С,Ҳ�����½�MediaSize�����Զ����С
           pras.add(MediaSizeName.ISO_A4);
           DocAttributeSet das = new HashDocAttributeSet();
           // ָ����ӡ����
           Doc doc = new SimpleDoc(this, flavor, das);
           // ����ʾ��ӡ�Ի���ֱ�ӽ��д�ӡ����
           try {
               job.print(doc, pras); // ����ÿһҳ�ľ����ӡ����
           } catch (PrintException pe) {
               pe.printStackTrace();
           }
       } else {
           // �����ӡ����Ϊ��ʱ����ʾ�û���ӡ��ȡ��
           JOptionPane.showConfirmDialog(null,
                   "Sorry, Printer Job is Empty, Print Cancelled!",

                   "Empty", JOptionPane.DEFAULT_OPTION,
                   JOptionPane.WARNING_MESSAGE);
       }
   }

   /*public static void main(String[] args) {
       LocatePrint lp = new LocatePrint();
       lp.printContent();
   }*/

}   