/**  
 * Project Name:api  
 * File Name:TestController.java  
 * Package Name:com.miaozhen.mooc.api  
 * Date:2015年3月19日上午11:58:18   
 *  
*/  
  
package org.stackbox.dancsic.web.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import org.stackbox.dancsic.spi.ResultModel;

/**  
 * ClassName:TestController <br/>    
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2015年3月19日 上午11:58:18 <br/>  
 * @author   吕思佳  
 * @version    
 * @see        
 */
@Controller
@RequestMapping("/test")
public class TestController {
    
    @RequestMapping(value = "/test",  method = RequestMethod.GET)
    @ResponseBody
    public Object test() {
        return ResultModel.SUCCESS(null);
    }
}
  
