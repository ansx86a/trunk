package dao;

import dao.domain.ExPool;
import dao.domain.ExPoolExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ExPoolMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table expool
     *
     * @mbg.generated Fri Jan 03 17:17:47 CST 2020
     */
    long countByExample(ExPoolExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table expool
     *
     * @mbg.generated Fri Jan 03 17:17:47 CST 2020
     */
    int deleteByExample(ExPoolExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table expool
     *
     * @mbg.generated Fri Jan 03 17:17:47 CST 2020
     */
    int deleteByPrimaryKey(Integer exid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table expool
     *
     * @mbg.generated Fri Jan 03 17:17:47 CST 2020
     */
    int insert(ExPool record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table expool
     *
     * @mbg.generated Fri Jan 03 17:17:47 CST 2020
     */
    int insertSelective(ExPool record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table expool
     *
     * @mbg.generated Fri Jan 03 17:17:47 CST 2020
     */
    List<ExPool> selectByExample(ExPoolExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table expool
     *
     * @mbg.generated Fri Jan 03 17:17:47 CST 2020
     */
    ExPool selectByPrimaryKey(Integer exid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table expool
     *
     * @mbg.generated Fri Jan 03 17:17:47 CST 2020
     */
    int updateByExampleSelective(@Param("record") ExPool record, @Param("example") ExPoolExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table expool
     *
     * @mbg.generated Fri Jan 03 17:17:47 CST 2020
     */
    int updateByExample(@Param("record") ExPool record, @Param("example") ExPoolExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table expool
     *
     * @mbg.generated Fri Jan 03 17:17:47 CST 2020
     */
    int updateByPrimaryKeySelective(ExPool record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table expool
     *
     * @mbg.generated Fri Jan 03 17:17:47 CST 2020
     */
    int updateByPrimaryKey(ExPool record);
}