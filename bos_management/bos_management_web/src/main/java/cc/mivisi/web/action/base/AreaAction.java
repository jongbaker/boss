package cc.mivisi.web.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

import cc.mivisi.bos.domain.Area;
import cc.mivisi.bos.service.base.AreaService;
import cc.mivisi.utils.FileDownloadUtils;
import cc.mivisi.utils.PinYin4jUtils;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * ClassName:AreaAction <br/>
 * Function: <br/>
 * Date: 2018年3月16日 下午7:06:37 <br/>
 */
// struts
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class AreaAction extends CommonAction<Area> {

    public AreaAction() {

        super(Area.class);

    }

    @Autowired
    private AreaService areaService;

    // 使用set获取接收
    private File file;

    public void setFile(File file) {
        this.file = file;
    }

    @Action(value = "areaAction_importXLS", results = {
            @Result(name = "success", location = "/pages/base/area.html", type = "redirect")})
    public String getfile() {

        try {
            // 1、 得到Excel常用对象
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(new FileInputStream(file));
            // 2.读取第一个工作簿
            HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
            // 3.存储对象的集合
            ArrayList<Area> list = new ArrayList<>();

            // 开始遍历读取到的工作薄
            for (Row row : sheet) {
                // 第一行不需要读取,跳过第一行
                if (row.getRowNum() == 0) {
                    continue;
                }
                // 读取单远哥的内容
                String province = row.getCell(1).getStringCellValue();
                System.out.println("第一次" + province);
                String city = row.getCell(2).getStringCellValue();
                String district = row.getCell(3).getStringCellValue();
                String postcode = row.getCell(4).getStringCellValue();

                // 截掉最后一个字符
                province = province.substring(0, province.length() - 1);
                System.out.println("第二次" + province);
                city = city.substring(0, city.length() - 1);
                district = district.substring(0, district.length() - 1);

                // 获取城市编码和简码
                String citycode = PinYin4jUtils.hanziToPinyin(city, "").toUpperCase();
                String[] headByString = PinYin4jUtils.getHeadByString(province + city + district);
                String shortcode = PinYin4jUtils.stringArrayToString(headByString);

                // 封装数据
                Area area = new Area();

                area.setProvince(province);
                area.setCity(city);
                area.setDistrict(district);
                area.setPostcode(postcode);
                area.setCitycode(citycode);
                area.setShortcode(shortcode);
                System.out.println(area);
                list.add(area);
            }

            areaService.save(list);
            // 释放资源
            hssfWorkbook.close();
        } catch (FileNotFoundException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();

        } catch (IOException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        return SUCCESS;
    }

    @Action("areaAction_pageQuery")
    public String pageQuery() throws IOException {

        // 这里这一段有areaService,不方便抽取.
        // jsonConfig 因为不确定是排除什么条件所以,在这里确定条件在传过去
        // 抽取原则---本质就是抽取共性的东西
        // 成员元变量不可抽取,一起他使用的参数,变量方法都不可以抽取
        // 不确定因素不可以抽取

        Pageable pageable = new PageRequest(page - 1, rows);

        Page<Area> page = areaService.findAll(pageable);

        // jsonConfig 因为不确定是排除什么条件所以,在这里确定条件在传过去
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"subareas"});

        toJons(jsonConfig, page);

        return NONE;
    }

    private String q;

    public void setQ(String q) {
        this.q = q;
    }

    @Action("areaAction_findAll")
    public String findAll() throws IOException {
        List<Area> list = new ArrayList<>();
        if (StringUtils.isNotEmpty(q)) {
            // 根據用戶模糊查詢
            list = areaService.findByQ(q);
        } else {
            // 查詢所有
            Page<Area> page = areaService.findAll(null);

            list = page.getContent();
        }
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"subareas"});
        list2json(list, jsonConfig);

        return NONE;
    }

    @Action("areaAction_exportExcel")
    public String exportExcel() throws IOException {
        Page<Area> pages = areaService.findAll(null);
        List<Area> list = pages.getContent();

        // 创建Excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();

        // 创建sheet
        HSSFSheet sheet = workbook.createSheet();

        // 创建标题行
        HSSFRow titleRow = sheet.createRow(0);
        titleRow.createCell(0).setCellValue("省");
        titleRow.createCell(1).setCellValue("市");
        titleRow.createCell(2).setCellValue("区");
        titleRow.createCell(3).setCellValue("邮编");
        titleRow.createCell(4).setCellValue("简码");
        titleRow.createCell(5).setCellValue("城市编码");

        // 遍历数据,创建数据行

        for (Area area : list) {
            int lastRowNum = sheet.getLastRowNum();
            HSSFRow dataRow = sheet.createRow(lastRowNum+1);
            
            dataRow.createCell(0).setCellValue(area.getProvince());
            dataRow.createCell(1).setCellValue(area.getCity());
            dataRow.createCell(2).setCellValue(area.getDistrict());
            dataRow.createCell(3).setCellValue(area.getPostcode());
            dataRow.createCell(4).setCellValue(area.getShortcode());
            dataRow.createCell(5).setCellValue(area.getCitycode());
        }
            //文件名字
            String filename= "区域数据统计.xls";
            
            //二头一流
            HttpServletResponse response = ServletActionContext.getResponse();
            
            HttpServletRequest request = ServletActionContext.getRequest();
            
            ServletContext servletContext = ServletActionContext.getServletContext();
            
            ServletOutputStream outputStream = response.getOutputStream();
            
         
            
            //获取mimType
            //
            /*
             * MIME(Multipurpose Internet Mail Extensions)多用途互联网邮件扩展类型。
             * 是设定某种扩展名的文件用一种应用程序来打开的方式类型，当该扩展名文件被访问的时候， 浏览器会自动使用指定应用程序来打开。多用于指定一些客户端自定义的文件名，
             * 以及一些媒体文件打开方式。
             */
            
            //先获取mimetype再重新编码,避免编码后后缀名丢失,导致获取失败..
           String mimeType = servletContext.getMimeType(filename); 
           
           
           //获取浏览器的类型
           String agent = request.getHeader("User-Agent");
           
           //对文件名重新编码
           filename = FileDownloadUtils.encodeDownloadFilename(filename, agent);
            
            /**
             * Content-disposition是 MIME 协议的扩展，MIME 协议指示 MIME 用户代理如何显示附加的文件。
             * 当 Internet Explorer 接收到头时，它会激活文件下载对话框，它的文件名框自动填充了头中指定的文件名。
             * （请注意，这是设计导致的；无法使用此功能将文档保存到用户的计算机上，而不向用户询问保存位置。）
             */
            //设置信息头
           response.setContentType(mimeType);
           response.setHeader("Content-Disposition","attachment;filename="+filename);
            
           //写出文件
           workbook.write(outputStream);
           workbook.close();
            
            
    

        return NONE;
    }

}
