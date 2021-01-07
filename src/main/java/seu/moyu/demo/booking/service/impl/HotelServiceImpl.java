package seu.moyu.demo.booking.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import seu.moyu.demo.booking.entity.Hotel;
import seu.moyu.demo.booking.mapper.HotelMapper;
import seu.moyu.demo.booking.service.IHotelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
public class HotelServiceImpl extends ServiceImpl<HotelMapper, Hotel> implements IHotelService {

    private boolean CheckString(String a,String b){
        int cnt = 0;
        System.out.println(a + "," + b);

        for(int i=0; i<a.length(); i++){
            for(int j=0;j<b.length();j++)
                if(a.charAt(i) == b.charAt(j)){
                    cnt++;
                    break;
                }
        }
        if(cnt * 2 >= b.length())return true;
        return false;
    }

    @Override
    public List<Hotel> Search(String parameter) {
        List<Hotel> HotelList = list();
        List<Hotel> res = new ArrayList<>();
        int cnt = 0;
        for(int i = 0; i < HotelList.size() ; i++){
            if(CheckString(parameter,HotelList.get(i).getHotelName()) == true){
                res.add(HotelList.get(i));
                cnt++;
            }
        }
        if(cnt == 0) return HotelList;
        else return res;
    }

    @Override
    public Hotel FindHotel(Integer hotelId){
        Hotel hotel = new Hotel();
        hotel = getById(hotelId);
        return hotel;
    }
}
