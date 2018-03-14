package cc.mivisi.bos.dao.base;

import java.util.List;

import org.bouncycastle.jce.provider.JDKDSASigner.stdDSA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import cc.mivisi.bos.domain.Standard;

/**  
 * ClassName:StandardRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午7:00:03 <br/>       
 */
//泛型1:封装数据对象的类型
//类型2:对象主键的类型
public interface StandardRepository extends JpaRepository<Standard, Long>{
	
	//SpringDataJPA 提供了一套命名规范
	 // 必须以findBy开头,后面跟属性的名字,首字母必须大写
    // 如果有多个条件,使用对应的SQL关键字
	List<Standard> findByName(String name);
	List<Standard> findByNameLike(String name);
	
	List<Standard> findByNameAndMaxWeight(String name,Integer maxWeight);
	
	//查询语句JPQ==HQL
	/*@Query如果查询查询的名字不对的时候那么我们就需要填写query里面的内容.
	 * 
	 * 这样就是玩自己
	 * 
	 * // 查询语句:JPQL === HQL
    @Query("from Standard where name = ? and maxWeight = ?")
    List<Standard> findByNameAndMaxWeight321312(String name, Integer maxWeight);

    // 在?后面追加数字的方式,改变匹配参数的顺序
    @Query("from Standard where name = ?2 and maxWeight = ?1")
    List<Standard> findByNameAndMaxWeight321312(Integer maxWeight, String name);

	List<Standard> findByNameAnd*/
	
	// 原生SQL-------------这样是不好的.
    @Query(value = "select * from T_STANDARD where C_NAME = ? and C_MAX_WEIGHT = ?",
            nativeQuery = true)
    List<Standard> findByNameAndMaxWeightfdsa321312(String name,
            Integer maxWeight);
    	
    //修改数据需要添加modifying
    @Modifying
    @Transactional
    @Query("update Standard set maxWeight = ? where name = ?")
    void updateWeightByName(Integer maxWeight,String name);
	
    @Modifying
    @Transactional
    @Query("delete from Standard where name = ?")
    void deleteByBame(String name);
	
	
	
}
  
