package seu.moyu.demo.booking.service.impl;

import seu.moyu.demo.booking.entity.Hotel;
import seu.moyu.demo.booking.entity.Room;
import seu.moyu.demo.booking.mapper.RoomMapper;
import seu.moyu.demo.booking.service.IRoomService;
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
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements IRoomService {

    @Override
    public Room FindRoom(Integer roomId) {
        Room room = new Room();
        room = getById(roomId);
        return room;
    }
}
