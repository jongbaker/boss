package cc.mivisi.web.action.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.springframework.ui.Model;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**  
 * ClassName:BaseAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月17日 下午1:03:59 <br/>       
 */
public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {
	private T model;

	@Override
	public T getModel() {
		  Class<? extends BaseAction> childClazz = this.getClass();
		  //为了方便使用方法.我们不建议用最大的接口接收
		  ParameterizedType pt = (ParameterizedType) childClazz.getGenericSuperclass();
		  
		  Type[] actualTypeArguments = pt.getActualTypeArguments();
		  
		  Class<T> clszz = (Class<T>) actualTypeArguments[0];
		  
		  try {
			model=clszz.newInstance();
		} catch (InstantiationException e) {
			  
			// TODO Auto-generated catch block  
			e.printStackTrace();  
			
		} catch (IllegalAccessException e) {
			  
			// TODO Auto-generated catch block  
			e.printStackTrace();  
			
		}
		  
		
		return model;
	}

}
  
