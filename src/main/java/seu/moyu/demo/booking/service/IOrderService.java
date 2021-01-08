package seu.moyu.demo.booking.service;

import seu.moyu.demo.booking.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import java.sql.Date;
import java.util.List;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author moyu_group
 * @since 2021-01-01
 */
public interface IOrderService extends IService<Order> {
    public int RateOrder(String token,int star,int orderID);

    public int CancelOrder(String token,int orderID);

    public int ReserveHotel(String token,Integer hotelId,Integer roomId,double price,Date startTime,Date endTime,Integer customerNumber);

    public List<Order> FindOrder(String token);

    public void Complete(Integer orderID);
}
