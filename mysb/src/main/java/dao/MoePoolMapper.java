package dao;

import dao.domain.MoePool;
import dao.domain.MoePoolExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MoePoolMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table moepool
     *
     * @mbg.generated Fri Jan 03 17:17:47 CST 2020
     */
    long countByExample(MoePoolExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table moepool
     *
     * @mbg.generated Fri Jan 03 17:17:47 CST 2020
     */
    int deleteByExample(MoePoolExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table moepool
     *
     * @mbg.generated Fri Jan 03 17:17:47 CST 2020
     */
    int deleteByPrimaryKey(Integer postid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table moepool
     *
     * @mbg.generated Fri Jan 03 17:17:47 CST 2020
     */
    int insert(MoePool record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table moepool
     *
     * @mbg.generated Fri Jan 03 17:17:47 CST 2020
     */
    int insertSelective(MoePool record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table moepool
     *
     * @mbg.generated Fri Jan 03 17:17:47 CST 2020
     */
    List<MoePool> selectByExample(MoePoolExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table moepool
     *
     * @mbg.generated Fri Jan 03 17:17:47 CST 2020
     */
    MoePool selectByPrimaryKey(Integer postid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table moepool
     *
     * @mbg.generated Fri Jan 03 17:17:47 CST 2020
     */
    int updateByExampleSelective(@Param("record") MoePool record, @Param("example") MoePoolExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table moepool
     *
     * @mbg.generated Fri Jan 03 17:17:47 CST 2020
     */
    int updateByExample(@Param("record") MoePool record, @Param("example") MoePoolExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table moepool
     *
     * @mbg.generated Fri Jan 03 17:17:47 CST 2020
     */
    int updateByPrimaryKeySelective(MoePool record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table moepool
     *
     * @mbg.generated Fri Jan 03 17:17:47 CST 2020
     */
    int updateByPrimaryKey(MoePool record);
}