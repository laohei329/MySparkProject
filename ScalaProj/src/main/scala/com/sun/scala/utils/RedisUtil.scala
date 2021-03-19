package com.sun.scala.utils


import org.apache.logging.log4j.util.PropertiesUtil
import redis.clients.jedis.{JedisPool, JedisPoolConfig}


/**
 * @ClassName RedisUtil
 * @author Mr.Sun
 * @version 1.0.0
 * @Description TODO
 * @createTime 2021年03月05日 15:15:00
 */
object RedisUtil {

  var jedisPool: JedisPool = null

  def getJedis() = {
    if (jedisPool == null) {
      val properties: PropertiesUtil = PropertiesUtil.getProperties
      val host: String = properties.getStringProperty("redis.host.ip")
      val port: Int = properties.getIntegerProperty("redis.host.host", 2181)
      var config: JedisPoolConfig = new JedisPoolConfig
      config.setMaxTotal(200) //资源池中的最大连接数
      config.setMaxIdle(10) //资源池允许的最大空闲连接数
      config.setMinIdle(10) //资源池确保的最少空闲连接数
      config.setBlockWhenExhausted(true) //当资源池用尽后，调用者是否要等待。只有当值为true时，下面的maxWaitMillis才会生效。
      config.setMaxWaitMillis(1 * 500) //当资源池连接用尽后，调用者的最大等待时间（单位为毫秒）
      config.setTestOnBorrow(true) //向资源池借用连接时是否做连接有效性检测（ping）。检测到的无效连接将会被移除。业务量很大时候建议设置为false，减少一次ping的开销。
      config.setTestOnReturn(true) //向资源池归还连接时是否做连接有效性检测（ping）。检测到无效连接将会被移除。 业务量很大时候建议设置为false，减少一次ping的开销。
      config.setJmxEnabled(true) //是否开启JMX监控 建议开启，请注意应用本身也需要开启。
      jedisPool = new JedisPool(config, host, port)

    }
    jedisPool.getResource
  }

}
