package seu.moyu.demo.booking.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import seu.moyu.demo.booking.entity.Order;
import seu.moyu.demo.booking.mapper.OrderMapper;
import seu.moyu.demo.booking.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import seu.moyu.demo.booking.service.IUserService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
    public int RateOrder(String token, int star, int orderID) {
        Integer uuid = userService.AnalyzeToken(token);
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("uuid",orderID);
        Order order = getOne(wrapper);
        if(!order.getUserId().equals(uuid)){
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

    @Override
    public int CancelOrder(String token, int orderID) {
        Integer uuid = userService.AnalyzeToken(token);
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("uuid",orderID);
        Order order = getOne(wrapper);
        if(!order.getUserId().equals(uuid)){
            return -1; //用户不同
        }
        if(order.getState() != 0){
            return -2; //不能退订
        }
        remove(wrapper);
        return 0;
    }

    @Override
    public int ReserveHotel(String token, Integer hotelId, Integer roomId, double price, Date startTime, Date endTime, Integer customerNumber) {
        Integer uuid = userService.AnalyzeToken(token);
        Order order = new Order();
        order.setUserId(uuid);
        order.setHotelId(hotelId);
        order.setRoomId(roomId);
        order.setStartDate(startTime);
        order.setEndDate(endTime);
        order.setPrice(price);
        order.setCustomerNumber(customerNumber);
        order.setState(0);
        save(order);
        return 0;
    }

    @Override
    public List<Order> FindOrder(String token) {
        List<Order> order = new ArrayList<>();
        Integer uuid = userService.AnalyzeToken(token);
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("userId",uuid);
        order = list(wrapper);
        return order;
    }

    @Override
    public void Complete(Integer orderID) {
        UpdateWrapper wrapper = new UpdateWrapper();
        wrapper.eq("uuid", orderID);
        Order order = getById(orderID);
        order.setState(1);
        update(order, wrapper);
    }
}
