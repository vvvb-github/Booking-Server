package seu.moyu.demo.booking.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import seu.moyu.demo.booking.entity.Order;
import seu.moyu.demo.booking.mapper.OrderMapper;
import seu.moyu.demo.booking.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import seu.moyu.demo.booking.service.IUserService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author moyu_group
 * @since 2021-01-01
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {
    @Autowired
    private IUserService userService;

    @Override
    public int RateRoom(String token, int star, int orderID) {
        Integer uuid = userService.AnalyzeToken(token);
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("uuid",orderID);
        Order order = getOne(wrapper);
        if(order.getUserId() != uuid){
            return -1; //用户不同
        }
        if(order.getState() != 1){
            return -2; //不能评价
        }
        order.setStar(star);
        order.setState(2);
        update(order,wrapper);
        return 0;
    }
}
