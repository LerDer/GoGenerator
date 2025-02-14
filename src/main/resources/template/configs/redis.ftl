package configs

import (
	"context"
	"time"

	"github.com/go-redis/redis/v8"
)

type Redis struct {
	Addr     string `mapstructure:"addr" json:"addr" yaml:"addr"`             // 地址
	Password string `mapstructure:"password" json:"password" yaml:"password"` // 密码
	DB       int    `mapstructure:"db" json:"db" yaml:"db"`                   // 数据库
}

type RedisStore struct {
	Expiration time.Duration
	PreKey     string
	Context    context.Context
}

func (rs *RedisStore) Set(id string, value interface{}) {
	err := RedisSe.Set(rs.Context, rs.PreKey+id, value, rs.Expiration).Err()
	if err != nil {
		Log.Error("RedisStoreSetError!", err)
	}
}

func (rs *RedisStore) Get(key string, clear bool) string {
	val, err := RedisSe.Get(rs.Context, key).Result()
	if err != nil {
		Log.Error("RedisStoreGetError!", err)
		return ""
	}
	if clear {
		err := RedisSe.Del(rs.Context, key).Err()
		if err != nil {
			Log.Error("RedisStoreClearError!", err)
			return ""
		}
	}
	return val
}

func InitRedis() {
	redisCfg := CONFIG.Redis
	client := redis.NewClient(&redis.Options{
	Addr:     redisCfg.Addr,
	Password: redisCfg.Password, // no password set
	DB:       redisCfg.DB,       // use default DB
	})

	pong, err := client.Ping(context.Background()).Result()
	if err != nil {
		Log.Error("redis connect ping failed, err:", err)
	} else {
		Log.Info("redis connect ping response:", pong)
		RedisSe = client
	}
	RedisUtil = &RedisStore{Expiration: time.Second * 180, PreKey: "CAPTCHA_", Context: context.Background()}
}
