package seu.moyu.demo.booking.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author moyu_group
 * @since 2021-01-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_Hotel")
public class Hotel implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "uuid", type = IdType.AUTO)
    private Integer uuid;

    @TableField("hotelName")
    private String hotelName;

    @TableField("introduction")
    private String introduction;

    @TableField("location")
    private String location;

    @TableField("roomList")
    private String roomList;

    @TableField("pictureUrl")
    private String pictureUrl;

    @TableField("minPrice")
    private double minPrice;

    @TableField("stars")
    private double stars;

    @TableField("commentNumber")
    private Integer commentNumber;
}
