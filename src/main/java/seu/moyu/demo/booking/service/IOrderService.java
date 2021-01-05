package seu.moyu.demo.booking.service;

import seu.moyu.demo.booking.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author moyu_group
 * @since 2021-01-01
 */
public interface IOrderService extends IService<Order> {
    public int RateRoom(String token,int star,int orderID);
}
