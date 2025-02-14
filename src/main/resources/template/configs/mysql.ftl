package configs

import (
	"fmt"
	"github.com/jinzhu/gorm"
	_ "github.com/jinzhu/gorm/dialects/mysql"
	"sync"
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
	DB = db
}

var once sync.Once

func BeforeRun() {
	once.Do(initDB)
}
