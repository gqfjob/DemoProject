/**  
 * Project Name:api  
 * File Name:ResultModel.java  
 * Package Name:com.miaozhen.mooc.spi  
 * Date:2015年3月19日下午12:51:38   
 *  
*/  
  
package org.stackbox.dancsic.spi;
/**  
 * ClassName:ResultModel <br/>    
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2015年3月19日 下午12:51:38 <br/>  
 * @author   吕思佳  
 * @version    
 * @see        
 */
public class ResultModel {


    /**
     * 200: 正确结果
     */
    private Integer code;
    private String errMsg;
    private Object data;

    private ResultModel(Integer code, String errMsg, Object data) {
        this.code = code;
        this.errMsg = errMsg;
        this.data = data;
    }


    public static ResultModel SUCCESS(Object data) {
        return new ResultModel(200, null, data);
    }

    public static ResultModel ERROR(Integer code, Object data) {
        return new ResultModel(code, null, data);
    }

    public static ResultModel ERROR(Integer code, String errMsg, Object data) {
        return new ResultModel(code, errMsg, data);
    }



    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
  
