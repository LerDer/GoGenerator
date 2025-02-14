package corn

import (
	"github.com/robfig/cron"
	"${projectName}/configs"
	"log"
)

func CornDemo() {
	configs.Log.Info("Starting...")
	// 新建一个定时任务对象
	c := cron.New()
	// 给对象增加定时任务
	err := c.AddFunc("0 */1 * * * ?", func() {
		log.Println("hello world")
	})
	if err != nil {
		configs.Log.Error(err)
	}
	//启动
	c.Start()
}