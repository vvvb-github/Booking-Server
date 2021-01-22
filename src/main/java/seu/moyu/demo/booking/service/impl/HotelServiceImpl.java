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
        if(cnt >= a.length())return true;
        return false;
    }

    @Override
    public List<Hotel> Search(String parameter,String location,String type) {
        List<Hotel> HotelList = list();
        List<Hotel> res = new ArrayList<>(),tmp = new ArrayList<>();
        //if(location.equals("江苏"))location = "南京";
        int cnt = 0;
        for(int i = 0; i < HotelList.size() ; i++){
            if((location.equals("")||location.equals(HotelList.get(i).getLocation()))
                    && CheckString(parameter,HotelList.get(i).getHotelName()) == true){
                if(CheckString(type,HotelList.get(i).getHotelName()) == true)res.add(HotelList.get(i));
                else tmp.add(HotelList.get(i));
            }
        }
        for(int i=0;i<tmp.size();i++)res.add(tmp.get(i));
        return res;
    }

    @Override
    public Hotel FindHotel(Integer hotelId){
        Hotel hotel = new Hotel();
        hotel = getById(hotelId);
        return hotel;
    }
}
