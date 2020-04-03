package com.cn.lucky.morning.model.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Redis操作服务类
 *
 * @author lucky_morning
 */
public interface RedisService {
    //////////////////////////////////////////////VALUE///////////////////////////////////////////////////

    /**
     * @param @param keys    参数
     * @return void    返回类型
     * @throws
     * @Title: remove
     * @Description: TODO(批量删除对应的value)
     */
    public void remove(final String... keys);

    /**
     * @param @param pattern    参数
     * @return void    返回类型
     * @throws
     * @Title: removePattern
     * @Description: TODO(批量删除key)
     */
    public void removePattern(final String pattern);

    /**
     * @param @param key    参数
     * @return void    返回类型
     * @throws
     * @Title: remove
     * @Description: TODO(删除对应的value)
     */
    public void remove(final String key);

    /**
     * @param @param  key
     * @param @return 参数
     * @return boolean    返回类型
     * @throws
     * @Title: exists
     * @Description: TODO(判断缓存中是否有对应的value)
     */
    public boolean exists(final String key);

    /**
     * @param @param  key
     * @param @return 参数
     * @return String    返回类型
     * @throws
     * @Title: get
     * @Description: TODO(读取缓存)
     */
    public String get(final String key);

    /**
     * @param @param  key
     * @param @param  value
     * @param @return 参数
     * @return boolean    返回类型
     * @throws
     * @Title: set
     * @Description: TODO(写入缓存 ， 永久)
     */
    public boolean set(final String key, String value);

    /**
     * @param @param  key
     * @param @param  value
     * @param @param  expireTime
     * @param @param  timeUnit
     * @param @return 参数
     * @return boolean    返回类型
     * @throws
     * @Title: set
     * @Description: TODO(写入缓存 （ 有时间限制 ）)
     */
    public boolean set(final String key, String value, Long expireTime, TimeUnit timeUnit);


    //////////////////////////////////////////////LIST///////////////////////////////////////////////////

    /**
     * @param @param  key
     * @param @param  start
     * @param @param  end
     * @param @return 参数
     * @return List<String>    返回类型
     * @throws
     * @Title: range
     * @Description: TODO(获取指定key的范围内的value值的 list列表 。 （ 0 - 1 ） 返回所有值列表)
     */
    List<String> range(final String key, Long start, Long end);

    /**
     * @param @param key
     * @param @param start
     * @param @param end
     * @return void    返回类型
     * @throws
     * @Title: trim
     * @Description: TODO(保留key指定范围内的列表值 。 其它的都删除 。)
     */
    void trim(final String key, Long start, Long end);

    /**
     * @param @param  key
     * @param @return 参数
     * @return Long    返回类型
     * @throws
     * @Title: size
     * @Description: TODO(获取key 列表的长度)
     */
    Long size(final String key);

    /**
     * @param @param  key
     * @param @param  value
     * @param @return 参数
     * @return Long    返回类型
     * @throws
     * @Title: leftPush
     * @Description: TODO(写入缓存 ， 是左面进入 先进后出)
     */
    public Long leftPush(final String key, String value);

    /**
     * @param @param  key
     * @param @param  values
     * @param @return 参数
     * @return Long    返回类型
     * @throws
     * @Title: leftPushAll
     * @Description: TODO(多个值写入缓存 ， 是左面进入 先进后出)
     */
    public Long leftPushAll(final String key, String... values);

    /**
     * @param @param  key
     * @param @param  value
     * @param @return 参数
     * @return Long    返回类型
     * @throws
     * @Title: leftPushIfPresent
     * @Description: TODO(如果列表存在 ， 则在列表左边插入值value)
     */
    Long leftPushIfPresent(final String key, String value);

    /**
     * @param @param  key
     * @param @param  pivot
     * @param @param  value
     * @param @return 参数
     * @return Long    返回类型
     * @throws
     * @Title: leftPush
     * @Description: TODO(在key的列表中指定的value左边 （ 前面 ） 插入一个新的value.如果 指定的value不存在则不插入任何值 。)
     */
    Long leftPush(final String key, String pivot, String value);

    /**
     * @param @param  key
     * @param @param  value
     * @param @return 参数
     * @return Long    返回类型
     * @throws
     * @Title: rightPush
     * @Description: TODO(写入缓存 ， 是右边面进入 先进先出)
     */
    Long rightPush(final String key, String value);

    /**
     * @param @param  key
     * @param @param  values
     * @param @return 参数
     * @return Long    返回类型
     * @throws
     * @Title: rightPushAll
     * @Description: TODO(多个值写入缓存 ， 是右边面进入 先进先出)
     */
    Long rightPushAll(final String key, String... values);

    /**
     * @param @param  key
     * @param @param  value
     * @param @return 参数
     * @return Long    返回类型
     * @throws
     * @Title: rightPushIfPresent
     * @Description: TODO(如果列表存在 ， 则在列表右边插入值value)
     */
    Long rightPushIfPresent(final String key, String value);

    /**
     * @param @param  key
     * @param @param  pivot
     * @param @param  value
     * @param @return 参数
     * @return Long    返回类型
     * @throws
     * @Title: rightPush
     * @Description: TODO(在key的列表中指定的value右边 （ 前面 ） 插入一个新的value.如果 指定的value不存在则不插入任何值 。)
     */
    Long rightPush(final String key, String pivot, String value);

    /**
     * @param @param key
     * @param @param index
     * @param @param value    参数
     * @return void    返回类型
     * @throws
     * @Title: set
     * @Description: TODO(设置key列表中指定位置的值为value index不能大于列表长度 。 大于抛出异常, 为负数则从右边开始计算)
     */
    void set(final String key, Long index, String value);

    /**
     * @param @param  key
     * @param @param  count (大于0从左边开始，等于0全部删除，小于0从右边开始)
     * @param @param  value
     * @param @return 参数
     * @return Long    返回类型
     * @throws
     * @Title: remove
     * @Description: TODO(删除列表中第一个遇到的value值 。 count指定删除多少个, count为0则全部删除)
     */
    Long remove(final String key, Long count, Object value);

    /**
     * @param @param  key
     * @param @param  index  (大于0从左边开始，小于0从右边开始)
     * @param @return 参数
     * @return String    返回类型
     * @throws
     * @Title: index
     * @Description: TODO(通过索引获取列表中的元素)
     */
    String index(final String key, Long index);

    /**
     * @param @param  key
     * @param @return 参数
     * @return String    返回类型
     * @throws
     * @Title: leftPop
     * @Description: TODO(移除列表中的第一个值 ， 并返回该值)
     */
    String leftPop(final String key);

    /**
     * @param @param  key
     * @param @param  timeout
     * @param @param  unit
     * @param @return 参数
     * @return String    返回类型
     * @throws
     * @Title: leftPop
     * @Description: TODO(移除列表中的第一个值 ， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止 。)
     */
    String leftPop(final String key, Long timeout, TimeUnit unit);

    /**
     * @param @param  key
     * @param @return 参数
     * @return String    返回类型
     * @throws
     * @Title: rightPop
     * @Description: TODO(移除列表中的最后一个值 ， 并返回该值)
     */
    String rightPop(final String key);

    /**
     * @param @param  key
     * @param @param  timeout
     * @param @param  unit
     * @param @return 参数
     * @return String    返回类型
     * @throws
     * @Title: rightPop
     * @Description: TODO(移除列表中的最后一个值 ， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止 。)
     */
    String rightPop(final String key, Long timeout, TimeUnit unit);

    /**
     * @param @param  sourceKey
     * @param @param  destinationKey
     * @param @return 参数
     * @return String    返回类型
     * @throws
     * @Title: rightPopAndLeftPush
     * @Description: TODO(从指定列表中从右边 （ 尾部 ） 移除第一个值 ， 并将这个值从左边 （ 头部 ） 插入目标列表)
     */
    String rightPopAndLeftPush(final String sourceKey, final String destinationKey);

    /**
     * @param @param  sourceKey
     * @param @param  destinationKey
     * @param @param  timeout
     * @param @param  unit
     * @param @return 参数
     * @return String    返回类型
     * @throws
     * @Title: rightPopAndLeftPush
     * @Description: TODO(从指定列表中从右边 （ 尾部 ） 移除第一个值 ， 并将这个值从左边 （ 头部 ） 插入目标列表 ， 如果移除的列表中没有值 ， 则一直阻塞指定的单位时间)
     */
    String rightPopAndLeftPush(final String sourceKey, final String destinationKey, Long timeout, TimeUnit unit);
}
