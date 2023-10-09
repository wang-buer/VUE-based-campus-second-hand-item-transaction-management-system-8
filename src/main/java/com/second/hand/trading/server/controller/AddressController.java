package com.second.hand.trading.server.controller;

import com.second.hand.trading.server.enums.ErrorMsg;
import com.second.hand.trading.server.model.AddressModel;
import com.second.hand.trading.server.service.AddressService;
import com.second.hand.trading.server.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@RestController // 相当于控制层类上加 @Controller + 方法上加@ResponseBody
// 这就意味着当前控制层类中所有方法返回的都是json对象
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/info")
    public ResultVo  getAddress(@CookieValue("shUserId")
                                    @NotNull(message = "登录异常 请重新登录")
                                    @NotEmpty(message = "登录异常 请重新登录") String shUserId,
                                @RequestParam(value = "id",required = false) Long id){

		/**
		 * 1.@Null 限制只能为null
		 * @NotNull 限制必须不为null   对象不是null就行，其他的不保证。
		 *
		 * 2.@NotEmpty除了@NotNull之外还需要保证@Size(min=1)
		 * 这也是一个注解，这里规定最小长度等于1，也就是类似于集合非空。
		 *
		 * 3.在 SpringMVC 中可以直接使用 @CookieValue 注解来获得指定的 Cookie 的值。
		 *
		 * 4.使用 @RequestParam 将请求参数绑定至方法参数
		 *
		 *  4.1 若required参数使用了该注解，则该参数默认是必须提供的，但你也可以把该参数标注为非必须的：
		 *  只需要将 @RequestParam 注解的 required 属性设置为 false
		 *
		 *  4.2 这里使用的 required = false 是将请求的参数设置为 null ，
		 *  所以方法里的参数需要为引用类型（Integer），如果使用的是基本类型（int）会出现错误
		 */

		if(null==id){
            return ResultVo.success(addressService.getAddressByUser(Long.valueOf(shUserId)));
        }else {
            return ResultVo.success(addressService.getAddressById(id,Long.valueOf(shUserId)));
        }
    }

    @PostMapping("/add")
    public ResultVo addAddress(@CookieValue("shUserId")
                                   @NotNull(message = "登录异常 请重新登录")
                                   @NotEmpty(message = "登录异常 请重新登录") String shUserId,
                               @RequestBody AddressModel addressModel){
        addressModel.setUserId(Long.valueOf(shUserId));
        if(addressService.addAddress(addressModel)){
            return ResultVo.success(addressModel);
        }
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }

    @PostMapping("/update")
    public ResultVo updateAddress(@CookieValue("shUserId")
                               @NotNull(message = "登录异常 请重新登录")
                               @NotEmpty(message = "登录异常 请重新登录") String shUserId,
                               @RequestBody AddressModel addressModel){
        addressModel.setUserId(Long.valueOf(shUserId));
        if(addressService.updateAddress(addressModel)){
            return ResultVo.success();
        }
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }

    @PostMapping("/delete")
    public ResultVo deleteAddress(@CookieValue("shUserId")
                                  @NotNull(message = "登录异常 请重新登录")
                                  @NotEmpty(message = "登录异常 请重新登录") String shUserId,
                                  @RequestBody AddressModel addressModel){
        addressModel.setUserId(Long.valueOf(shUserId));
        if(addressService.deleteAddress(addressModel)){
            return ResultVo.success();
        }
        return ResultVo.fail(ErrorMsg.SYSTEM_ERROR);
    }
}
