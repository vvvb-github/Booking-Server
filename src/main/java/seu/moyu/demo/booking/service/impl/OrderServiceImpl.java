package seu.moyu.demo.booking.service.impl;

import seu.moyu.demo.booking.entity.Order;
import seu.moyu.demo.booking.mapper.OrderMapper;
import seu.moyu.demo.booking.service.IOrderService;
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
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

}
