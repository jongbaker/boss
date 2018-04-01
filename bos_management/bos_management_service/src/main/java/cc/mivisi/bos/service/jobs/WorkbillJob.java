package cc.mivisi.bos.service.jobs;
/**
 * ClassName:WorkbillJob <br/>
 * Function: <br/>
 * Date: 2018年3月30日 下午10:43:10 <br/>
 */

import java.security.GeneralSecurityException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;

import cc.mivisi.bos.dao.base.take_delivery.WorkBillJpaRepository;
import cc.mivisi.bos.domain.take_delivery.WorkBill;
import cc.mivisi.utils.MailUtils_163;

public class WorkbillJob {

    @Autowired
    private WorkBillJpaRepository workBillJpaRepository;

    public void sendMail() {

        List<WorkBill> workBills = workBillJpaRepository.findAll();

        String emailBody = "编号\t快递员\t取件状态\t时间<br/>";

        for (WorkBill workBill : workBills) {
            emailBody += workBill.getId() + "\t" + workBill.getCourier().getName() + "\t"
                    + workBill.getPickstate() + "\t" + workBill.getBuildtime().toString();

        }
        try {
            MailUtils_163.sendMail("694061256@qq.com", "工单信息", emailBody);
        } catch (MessagingException | GeneralSecurityException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();

            System.out.println("发生错误");

        }

    }

}
