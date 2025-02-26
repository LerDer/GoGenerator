package configs

import (
	"fmt"
	"sync"
    "time"

	"github.com/jinzhu/gorm"
	_ "github.com/jinzhu/gorm/dialects/mysql"
)

type MySQL struct {
	Host     string `mapstructure:"Host" json:"Host"`
	Port     string `mapstructure:"Port" json:"Port"`
	User     string `mapstructure:"User" json:"User"`
	Password string `mapstructure:"Password" json:"Password"`
	DBName   string `mapstructure:"DBName" json:"DBName"`
}

//初始化数据库连接
func initDB() {
	initConfig()
	cfg := CONFIG
	dsn := fmt.Sprintf("%s:%s@tcp(%s:%s)/%s?charset=utf8&parseTime=True&loc=Local", cfg.MySQL.User, cfg.MySQL.Password, cfg.MySQL.Host, cfg.MySQL.Port, cfg.MySQL.DBName)
	fmt.Println(dsn)
	db, err := gorm.Open("mysql", dsn)
	if err != nil {
		panic(err)
	}
    // 获取底层的 *sql.DB 对象
    sqlDB := db.DB()
    // 配置连接池
    sqlDB.SetMaxOpenConns(100)                // 最大打开连接数
    sqlDB.SetMaxIdleConns(10)                 // 最大空闲连接数
    sqlDB.SetConnMaxIdleTime(time.Minute * 5) // 连接的最大空闲时间
    sqlDB.SetConnMaxLifetime(time.Minute * 5) // 连接的最大存活时间
	DB = db
}

var once sync.Once

func BeforeRun() {
	once.Do(initDB)
}
