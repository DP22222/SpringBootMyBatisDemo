package com.springboot.demo.mapper;

import com.springboot.demo.bean.UserBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LoginMapper {
    @Select("SELECT * FROM LOGIN WHERE id = #{id}")
    UserBean getById(@Param("id") int id);

    /**通过搜索名字选择登录*/
    @Select("SELECT * FROM LOGIN WHERE account = #{account}")
    UserBean getByName(@Param("account") String account);

    /**注册账号*/
    @Insert("INSERT INTO LOGIN(account,password) VALUES(#{account},#{password})")
    int register(UserBean userBean);



//    int register(@Param("account") String account,@Param("password") String password);

//    @Select("select * from mybatis_Student where id=#{id}")
//    public Student getStudent(int id);

//    @Insert("insert into mybatis_Student (name, age, remark, pic,grade_id,address_id) values (#{name},#{age},#{remark}, #{pic},#{grade.id},#{address.id})")
//    public int insert(Student student);

//    @Update("update mybatis_Student set name=#{name},age=#{age} where id=#{id}")
//    public int update(Student student);
//    @Delete("delete from mybatis_Student where id=#{id}")
//    public int delete(int id);

//    @Select("select *from User")
//    public List<User> retrieveAllUsers();
//
//    //注意这里只有一个参数，则#{}中的标识符可以任意取
//    @Select("select *from User where id=#{idss}")
//    public User retrieveUserById(int id);
//
//    @Select("select *from User where id=#{id} and userName like #{name}")
//    public User retrieveUserByIdAndName(@Param("id")int id,@Param("name")String names);
//
//    @Insert("INSERT INTO user(userName,userAge,userAddress) VALUES(#{userName},"
//            + "#{userAge},#{userAddress})")
//    public void addNewUser(User user);
//
//    @Delete("delete from user where id=#{id}")
//    public void deleteUser(int id);
//
//    @Update("update user set userName=#{userName},userAddress=#{userAddress}"
//            + " where id=#{id}")
//    public void updateUser(User user);
}
