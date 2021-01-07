package seu.moyu.demo.booking.service;

import seu.moyu.demo.booking.entity.Hotel;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author moyu_group
 * @since 2021-01-01
 */
public interface IHotelService extends IService<Hotel> {
    public List<Hotel> Search(String parameter);
    public Hotel FindHotel(Integer hotelId);
}
