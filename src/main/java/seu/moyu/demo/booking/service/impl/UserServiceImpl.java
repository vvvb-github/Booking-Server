package seu.moyu.demo.booking.service.impl;

import seu.moyu.demo.booking.entity.User;
import seu.moyu.demo.booking.mapper.UserMapper;
import seu.moyu.demo.booking.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author moyu_group
 * @since 2021-01-01
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
