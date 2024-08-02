package cn.wz.goldRush.respository;


import cn.wz.goldRush.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, String> {


}
