package cc.mivisi.bos.fore.utils;

import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.lowagie.text.pdf.AcroFields.Item;
import com.sun.mail.util.MailSSLSocketFactory;
/**  
 * ClassName:WebMailUtil <br/>  
 * Function:  <br/>  
 * Date:     2018年3月22日 上午8:57:41 <br/>       
 */
public class WebMailUtil {
	private static String userName = "694061256@qq.com";//发 送邮 件的Q Q邮 箱 账 号  
    private static String password = "ucpbqllbfvbqbehi";//授 权 码  
    //private static String userName2 = "jongbaker@126.com";//接 收 邮 件 的Q Q邮 箱 账 号  
    //这里是这二个端口号,不行就换另一个,与邮箱设置有关
    private static String port = "587";//465、587  
    /** 
     * 该 方 法 用 来 发 送 邮 件 
     * @param to:给 谁 发 邮 件 
     * @throws GeneralSecurityException 
     * @throws MessagingException 
     * @throws AddressException 
     * **/  
    public static void sendMail(String yourMailAddress,String item,String content) throws AddressException, MessagingException, GeneralSecurityException{
    	sendMain(yourMailAddress,item,content);
    }
    
    
    public static void sendMain(String yourMailAddress,String item,String content) throws AddressException, MessagingException, GeneralSecurityException{  
        //1、创 建 连 接 对 象，连 接 到 邮 箱 服 务 器  
        Properties props = new Properties();  
        //开 启 debug 调 试    
        props.setProperty("mail.debug", "true");    
        //stmp服 务 器 需 要 进 行 身 份 验 证，也 就 是 用  户 名 和 密 码 的 校 验，这 样 才 能 通 过 验 证  
        props.setProperty("mail.smtp.auth", "true");   
        //发 送 邮 件 协 议 名 称    
        props.setProperty("mail.transport.protocol", "smtp");    
        //设 置 邮 件服 务 器 主 机 名    
        props.setProperty("mail.host", "smtp.qq.com");//设 置 成 q q 的发 件 服 务 器:（不要使用smtp.exmail.qq.com）  
        //设 端 口 号 (该 配 置 可 写 可 不 写)  
        props.setProperty("mail.smtp.port", port);  
        //授 权 码  
        props.setProperty("mail.smtp.password",password);          
           
        //开 启 S S L 加 密，否 则 会 失 败  
        MailSSLSocketFactory sf = new MailSSLSocketFactory();  
        sf.setTrustAllHosts(true);  
        props.put("mail.smtp.ssl.enable", "true");  
        props.put("mail.smtp.ssl.socketFactory", sf);  
          
        //Authenticator:认 证 信 息  
        Session session = Session.getInstance(props, new Authenticator(){  
            @Override  
            protected PasswordAuthentication getPasswordAuthentication() {  
                return new PasswordAuthentication(userName,password);//使 用 它 给 其 他 账 户 发 邮 件  
            }  
        });  
          
        //2、创 建 邮 件 对 象  
        Message message = new MimeMessage(session);  
        //2.1 设 置 发 件 人  
        message.setFrom(new InternetAddress(userName));  
        //2、2 设 置 收 件 人  
        message.setRecipient(RecipientType.TO, new InternetAddress(yourMailAddress));  
        //2.3 邮 件 的 主 题  
        message.setSubject(item);  
        //2.4 邮 件 的 正 文（即 邮 件 的 内 容）  
        message.setContent(content,"text/html;charset=utf-8");  
          
        //3.发 送 邮 件  
        Transport trans = session.getTransport();  
        //连 接 邮 件 服 务 器  
        trans.connect(userName, password);    
        //发 送 邮 件  
        trans.sendMessage(message, message.getAllRecipients());    
        //关 闭 连 接  
        trans.close();  
          
        //Transport.send(message);(两 种 方 式 都 可 以)  
        System.out.println("发 送 成 功");  
    }  
      
   public static void main(String[] args) {  
        try {  
        	//sendMain("hexuwei@126.com","hexuwei@126.com","快来激活");  
        	sendMain("jongbaker@126.com", "active", "激活");
        } catch (Exception e) {  
            e.printStackTrace();  
        }   
    }  
}
  
