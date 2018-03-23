package cc.mivisi.bos.service.take_delivery.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.mivisi.bos.dao.base.AreaReposity;
import cc.mivisi.bos.dao.base.FixedJpaRepository;
import cc.mivisi.bos.dao.base.take_delivery.OrderJpaRepository;
import cc.mivisi.bos.dao.base.take_delivery.WorkBillJpaRepository;
import cc.mivisi.bos.domain.Area;
import cc.mivisi.bos.domain.Courier;
import cc.mivisi.bos.domain.FixedArea;
import cc.mivisi.bos.domain.SubArea;
import cc.mivisi.bos.domain.take_delivery.Order;
import cc.mivisi.bos.domain.take_delivery.WorkBill;
import cc.mivisi.bos.service.take_delivery.OrderService;

/**
 * ClassName:OrderServiceImpl <br/>
 * Function: <br/>
 * Date: 2018年3月23日 下午5:42:18 <br/>
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderJpaRepository orderJpaRepository;

	@Autowired
	private AreaReposity areaReposity;

	@Autowired
	private FixedJpaRepository fixedJpaRepository;

	@Autowired
	private WorkBillJpaRepository workBillJpaRepository;

	@Override
	public void save(Order order) {
		
		System.out.println("order-------"+order);
		// 把瞬时态的area转换为持久态的area
		Area sendArea = order.getSendArea();
		
		// 传到这里,也有可能为空
		if (sendArea != null) {
			// 持久态
			Area sendAreaDB = areaReposity.findByProvinceAndCityAndDistrict(sendArea.getProvince(), sendArea.getCity(),
					sendArea.getDistrict());
			order.setSendArea(sendAreaDB);
		}

		Area recArea = order.getRecArea();
		if (recArea != null) {
			Area recAreaDB = areaReposity.findByProvinceAndCityAndDistrict(sendArea.getProvince(), sendArea.getCity(),
					sendArea.getDistrict());
			order.setRecArea(recAreaDB);
		}

		// 保存订单
		// 设置订单的时间,订单号,----------------这里生产的订单由可能有地址,也有可能没有地址,也可能是乱写的.
		order.setOrderTime(new Date());
		order.setOrderNum(UUID.randomUUID().toString().replaceAll("-", ""));
		orderJpaRepository.save(order);

		// 自动分单---1,精准分单,模糊分单,人工分单
		String sendAddress = order.getSendAddress();

		// 1,--根据发件地址完全匹配-----查不到,
		// 这个是建立在大量的用户的基础之上的,感觉需要添加,优化查询,这样才好,要不数据量太大
		// 去数据库直接查询地址,这是建立在,用户直接点击的情况下,而且是比较规矩的地址,要不然计算机是不能识别的.
		// 我还必须要去,CRM去webService去查询
		
		if (StringUtils.isNotEmpty(sendAddress)) {
			System.out.println("--------------");
			String fixedAreaID = WebClient.create("http://localhost:8180/crm28/webService/customerService/getFixedArea")
					.accept(MediaType.APPLICATION_JSON)
					.type(MediaType.APPLICATION_JSON).query("address", sendAddress).get(String.class);
			/* 这里分二种情况 */
			System.out.println("fixedId---------"+fixedAreaID);
			if (fixedAreaID != null) {
				// 根据定区ID查询定区
				FixedArea fixedArea = fixedJpaRepository.findOne(Long.parseLong(fixedAreaID));

				if (fixedArea != null) {
					Set<Courier> couriers = fixedArea.getCouriers();

					if (!couriers.isEmpty()) {

						// 这里只是做简单的选择快递员
						// 实际过程中会增加复杂程度,需要根据时间,能力,繁忙程度等合理分配快递员,感觉这个太难了
						Iterator<Courier> iterator = couriers.iterator();
						Courier courier = iterator.next();

						// 指派快递员
						order.setCourier(courier);

						// 生成工单
						WorkBill workBill = new WorkBill();
						workBill.setAttachbilltimes(0);
						workBill.setBuildtime(new Date());
						workBill.setCourier(courier);

						workBill.setOrder(order);
						workBill.setPickstate("新单");
						workBill.setRemark(order.getRemark());
						workBill.setSmsNumber("111");
						workBill.setType("新");

						workBillJpaRepository.save(workBill);

						// 发送短信,推送通知
						// 中断代码
						order.setOrderType("自动分单");
						return;
					}
				}
			} else {
				// 更具已经有的客服查到是空,所以走第二条线路
				// 持久态
				// 定区关联分区,在页面上填写的发件地址,必须是对应的分区的关键字或者辅助关键字
				// 这里因为order是持久态了,怎么说sendArea也是---?
				// 第一个sendArea是瞬时态,这时候获取的就是持久态的sendArea
				Area sendArea2 = order.getSendArea();

				if (sendArea2 != null) {

					Set<SubArea> subareas = sendArea2.getSubareas();// 通过get方法获得的是持久态
					for (SubArea subArea : subareas) {
						String keyWords = subArea.getKeyWords();
						String assistKeyWords = subArea.getAssistKeyWords();
						if (sendAddress.contains(keyWords) || sendAddress.contains(assistKeyWords)) {
							FixedArea fixedArea = subArea.getFixedArea();
							if (fixedArea != null) {
								Set<Courier> couriers = fixedArea.getCouriers();

								if (!couriers.isEmpty()) {

									// 这里只是做简单的选择快递员
									// 实际过程中会增加复杂程度,需要根据时间,能力,繁忙程度等合理分配快递员,感觉这个太难了
									Iterator<Courier> iterator = couriers.iterator();
									Courier courier = iterator.next();

									// 指派快递员
									order.setCourier(courier);

									// 生成工单
									WorkBill workBill = new WorkBill();
									workBill.setAttachbilltimes(0);
									workBill.setBuildtime(new Date());
									workBill.setCourier(courier);

									workBill.setOrder(order);
									workBill.setPickstate("新单");
									workBill.setRemark(order.getRemark());
									workBill.setSmsNumber("111");
									workBill.setType("新");

									workBillJpaRepository.save(workBill);

									// 发送短信,推送通知
									// 中断代码
									order.setOrderType("自动分单");
									return;
								}

							}
						}

					}
				}

			}
		}
		
		order.setOrderType("人工分单");

	}

}
