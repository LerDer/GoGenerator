package configs

import (
	"github.com/go-redis/redis/v8"
	"github.com/jinzhu/gorm"
	"github.com/sirupsen/logrus"
	"go.uber.org/zap"
)

var (
	CONFIG    *Config
	DB        *gorm.DB
	Log       *logrus.Logger
	ZapLog    *zap.Logger
	RedisSe   *redis.Client
	RedisUtil *RedisStore
)

func GetDB() *gorm.DB {
	if CONFIG.Debug {
		return DB.Debug()
	} else {
		return DB
	}
}
