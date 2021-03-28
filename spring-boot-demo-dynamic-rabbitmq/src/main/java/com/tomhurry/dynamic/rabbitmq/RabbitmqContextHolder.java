package com.tomhurry.dynamic.rabbitmq;

/**
 * @author taozhi
 * @date 2021/3/22
 * @since 1.0.0
 */
public class RabbitmqContextHolder {

    private RabbitmqContextHolder() {
    }

    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * 调用该方法，切换数据源
     *
     * @param rabbitmq
     */
    public static void setRabbitmq(String rabbitmq) {
        CONTEXT_HOLDER.set(rabbitmq);
    }

    /**
     * 获取数据源
     *
     * @return
     */
    public static String getRabbitmq() {
        return CONTEXT_HOLDER.get();
    }

    /**
     * 删除数据源
     */
    public static void clearRabbitmq() {
        CONTEXT_HOLDER.remove();
    }

}
