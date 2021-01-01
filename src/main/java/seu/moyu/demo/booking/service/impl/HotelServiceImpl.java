package seu.moyu.demo.booking.service.impl;

import seu.moyu.demo.booking.entity.Hotel;
import seu.moyu.demo.booking.mapper.HotelMapper;
import seu.moyu.demo.booking.service.IHotelService;
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
public class HotelServiceImpl extends ServiceImpl<HotelMapper, Hotel> implements IHotelService {

}
