package cc.mivisi.web.action.base.take_delivery;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.junit.Test;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.ValueStack;

import net.sf.json.JSONObject;

/**  
 * ClassName:ImageAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月27日 下午2:34:18 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class ImageAction extends ActionSupport {
    
    //通过set方法只是获取了文件,没有获取文件名字
    private File imgFile;
    
   
    public void setImgFile(File imgFile) {
        this.imgFile = imgFile;
    }
    
    //使用属性驱动获取用户文件上传的名字,Content-Disposition: form-data; name="imgFile"; filename="nipon.jpg"
    private String imgFileFileName;
    public void setImgFileFileName(String imgFileFileName) {
        this.imgFileFileName = imgFileFileName;
    }
    
    
    @Action("imageAction_upload")
    public String imageUpload() throws IOException{
        //那么就开始拼接目标文件夹
        //获取当前文件夹-------------获取servlet的api
        HashMap<String, Object> map = new HashMap<>();
        String realPath = ServletActionContext.getServletContext().getRealPath("/");
        System.out.println(realPath);
        realPath= realPath+("upload");
        System.out.println(realPath);
    
        String fileName = UUID.randomUUID().toString().replaceAll("-", "")+imgFileFileName;
        realPath = realPath+"/"+fileName;
        File file = new File(realPath);
        //获取本项目地址
        String contextPath = ServletActionContext.getRequest().getContextPath();
        System.out.println("context:"+contextPath);
        try {
            FileUtils.copyFile(imgFile,file);
            //页面需要返回
            map.put("error", 0);
            map.put("url", contextPath+"/upload/"+fileName);
            
            
        } catch (IOException e) {
              
            // TODO Auto-generated catch block  
            e.printStackTrace();  
            map.put("error",1 );
            map.put("message", e.getMessage());
        }
        
      HttpServletResponse response = ServletActionContext.getResponse();
        PrintWriter writer = response .getWriter();
        String json = JSONObject.fromObject(map).toString();
        //消除乱码
        response.setContentType("application/json;charset=utf-8");
        writer.write(json);
        return NONE;
    }

    
    
    @Test
    public void test(){
        
        
        ActionContext context2 = ServletActionContext.getContext();
        ActionContext context = ActionContext.getContext();
        
        ValueStack valueStack = context.getValueStack();
        valueStack.set("a", 1);//键值方式,存入值栈
        valueStack.push(1);//直接存到栈顶
        
    }
    
}
  
