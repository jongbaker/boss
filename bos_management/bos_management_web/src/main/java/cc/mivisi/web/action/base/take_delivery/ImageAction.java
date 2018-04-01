package cc.mivisi.web.action.base.take_delivery;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;import java.util.Map;
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
    
    @Action("imageAction_manager")
    public String manager() throws IOException{
        ServletContext servletContext = ServletActionContext.getServletContext();
        
        String savePath = "upload";
        String realPath = servletContext.getRealPath(savePath);
        
        File currentPathFile = new File(realPath);
        
        String[] fileTypes = new String[]{"gif", "jpg", "jpeg", "png", "bmp"};

      //遍历目录取的文件信息
      List<Hashtable> fileList = new ArrayList<Hashtable>();
      
      //推测current..为file类型的
      if(currentPathFile.listFiles() != null) {
          for (File file : currentPathFile.listFiles()) {
              Hashtable<String, Object> hash = new Hashtable<String, Object>();
              String fileName = file.getName();
              if(file.isDirectory()) {
                  hash.put("is_dir", true);
                  hash.put("has_file", (file.listFiles() != null));
                  hash.put("filesize", 0L);
                  hash.put("is_photo", false);
                  hash.put("filetype", "");
              } else if(file.isFile()){
                  String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                  hash.put("is_dir", false);
                  hash.put("has_file", false);
                  hash.put("filesize", file.length());
                  hash.put("is_photo", Arrays.<String>asList(fileTypes).contains(fileExt));
                  hash.put("filetype", fileExt);
              }
              hash.put("filename", fileName);
              hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
              fileList.add(hash);
          }
      }
        
      HttpServletResponse response = ServletActionContext.getResponse();
        HashMap<String, Object> result = new HashMap<>();
       String contentPath =ServletActionContext.getServletContext().getContextPath()+"/"+savePath+"/";
       System.out.println(contentPath);
      result.put("current_dir_path", contentPath);
      result.put("file_list", fileList);
      
      JSONObject json = JSONObject.fromObject(result);
      
      //写出到页面
      
      response.setContentType("application/json; charset=UTF-8");
      response.getWriter().write(json.toString());
        
        return NONE;//TODO
    }
    
}
  
