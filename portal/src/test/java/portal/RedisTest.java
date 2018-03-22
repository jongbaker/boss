package portal;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cc.mivisi.bos.fore.utils.MailUtils;

/**  
 * ClassName:RedisTest <br/>  
 * Function:  <br/>  
 * Date:     2018年3月21日 下午4:07:17 <br/>       
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class RedisTest {
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Test
	public void test01(){
		//添加数据
		redisTemplate.opsForValue().set("age", "18");
		
	}
	@Test
	public void test02(){
		//删除
		redisTemplate.delete("age");
	}
	@Test
	public void test03(){
		//添加数据
		redisTemplate.opsForValue().set("baby", "myhonnybaby", 10,TimeUnit.SECONDS);
		
	}
	
	@Test
	public void send(){
		//发送邮件
		String receiver = "ls@store.com";
		String subject = "测试";
		String emailBody = "邮件内容就是为了测试来的";
		MailUtils.sendMail(receiver, subject, emailBody);
		
		
	}
	

	

}
  
