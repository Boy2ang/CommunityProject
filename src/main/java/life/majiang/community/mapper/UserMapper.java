package life.majiang.community.mapper;

import life.majiang.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    @Insert("insert into user values(default,#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);
}
