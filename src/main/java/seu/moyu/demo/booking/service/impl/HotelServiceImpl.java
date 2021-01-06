package seu.moyu.demo.booking.service.impl;

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

    private boolean checkString(String a,String b){
        int cnt = 0;
        System.out.println(a + "," + b);

        for(int i=0; i<a.length(); i++){
            for(int j=0;j<b.length();j++)
                if(a.charAt(i) == b.charAt(j)){
                    cnt++;
                    break;
                }
        }
        System.out.println(cnt + " " + b.length());
        if(cnt * 2 >= b.length())return true;
        return false;
    }
    @Override
    public List<Hotel> Search(String parameter) {
        List<Hotel> HotelList = list();
        List<Hotel> res = new ArrayList<>();
        int cnt = 0;
        for(int i = 0; i < HotelList.size() ; i++){
            if(checkString(parameter,HotelList.get(i).getHotelName()) == true){
                System.out.println("hello");
                res.add(HotelList.get(i));
                cnt++;
            }
        }
        if(cnt == 0)return HotelList;
        else return res;
    }
}
