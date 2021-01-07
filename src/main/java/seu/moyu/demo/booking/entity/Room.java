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
@TableName("t_Room")
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "uuid", type = IdType.AUTO)
    private Integer uuid;

    @TableField("title")
    private String title;

    @TableField("introduction")
    private String introduction;

    @TableField("price")
    private Double price;

    @TableField("hotelId")
    private Integer hotelId;

    @TableField("pictureUrl")
    private String pictureUrl;

    @TableField("peopleNumber")
    private Integer peopleNumber;
}
