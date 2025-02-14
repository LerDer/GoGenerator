package configs

import (
	"bytes"
	_ "embed"
	"github.com/spf13/viper"
)

type Config struct {
	Port   string `json:"port"`
	Debug  bool   `json:"debug"`
	MySQL  MySQL  `json:"MySQL"`
	Logger Logger `json:"Logger"`
	Redis  Redis  `json:"Redis"`
	Email  Email  `json:"Email"`
}

//如果要使用json,只需要更改为 configs/resources/config.json
const defaultConfigFile = "configs/resources/config.yaml"

//go:embed resources/config.yaml
var configs []byte

//打包后可获取静态文件
func initConfig() {
	r := bytes.NewReader(configs)
	viper.SetConfigType("yaml")
	var config Config
	if err := viper.ReadConfig(r); err != nil {
		panic(err)
	}

	if err := viper.Unmarshal(&config); err != nil {
		panic(err)
	}
	CONFIG = &config
}
