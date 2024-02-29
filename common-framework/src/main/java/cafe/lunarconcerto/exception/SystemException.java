package cafe.lunarconcerto.exception;

import cafe.lunarconcerto.enums.AppHttpCodeEnum;

/**
 * @author LunarConcerto
 * @time 2023/12/24
 */
public class SystemException extends RuntimeException{

    private final int code;

    private final String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public SystemException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }

}
