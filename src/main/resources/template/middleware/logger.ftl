package middleware

import (
	"bytes"
	"fmt"
	"github.com/gin-gonic/gin"
	"github.com/lestrrat/go-file-rotatelogs"
	"github.com/rifflock/lfshook"
	"github.com/sirupsen/logrus"
	"${projectName}/configs"
	"io/ioutil"
	"net/http"
	"os"
	"strconv"
	"time"
)

var level logrus.Level

// 日志记录到文件
func Logger() gin.HandlerFunc {
	configs.Log = logrus.New()
	// 写入文件
	osf, err := os.OpenFile(os.DevNull, os.O_APPEND|os.O_WRONLY, os.ModeAppend)
	if err != nil {
		fmt.Println("err", err)
	}
	// 设置输出
	configs.Log.Out = osf
	// 设置日志级别
	switch configs.CONFIG.Logger.Level {
		case "debug":
		level = logrus.DebugLevel
		case "info":
		level = logrus.InfoLevel
		case "warn":
		level = logrus.WarnLevel
		case "error":
		level = logrus.ErrorLevel
		case "panic":
		level = logrus.PanicLevel
		case "fatal":
		level = logrus.FatalLevel
		default:
		level = logrus.InfoLevel
	}

	configs.Log.SetLevel(level)
	// 设置 rotatelogs
	logWriter, err := rotatelogs.New(
	// 分割后的文件名称
	configs.CONFIG.Logger.Director+configs.CONFIG.Logger.Name+".%Y-%m-%d.log",
	// 生成软链，指向最新日志文件
	//rotatelogs.WithLinkName(configs.CONFIG.Logger.Director+configs.CONFIG.Logger.Name),
	// 设置最大保存时间(7天)
	rotatelogs.WithMaxAge(7*24*time.Hour),
	// 设置日志切割时间间隔(1天)
	rotatelogs.WithRotationTime(24*time.Hour),
	)
	writeMap := lfshook.WriterMap{
		logrus.InfoLevel:  logWriter,
		logrus.FatalLevel: logWriter,
		logrus.DebugLevel: logWriter,
		logrus.WarnLevel:  logWriter,
		logrus.ErrorLevel: logWriter,
		logrus.PanicLevel: logWriter,
	}

	lfHook := lfshook.NewHook(writeMap, &logrus.JSONFormatter{TimestampFormat: "2006-01-02 15:04:05"})
	// 新增 Hook
	configs.Log.AddHook(lfHook)
	return func(c *gin.Context) {
		var body []byte
		//不取get参数
		if c.Request.Method != http.MethodGet {
			var err error
			body, err = ioutil.ReadAll(c.Request.Body)
			if err != nil {
				configs.Log.Error("read body from request error:", err)
			} else {
				c.Request.Body = ioutil.NopCloser(bytes.NewBuffer(body))
			}
		}

		writer := responseBodyWriter{
			ResponseWriter: c.Writer,
			body:           &bytes.Buffer{},
		}
		c.Writer = writer

		// 开始时间
		startTime := time.Now()
		// 处理请求
		c.Next()
		// 结束时间
		endTime := time.Now()
		// 执行时间
		latencyTime := endTime.Sub(startTime)
		// 请求方式
		reqMethod := c.Request.Method
		// 请求路由
		reqUrl := c.Request.RequestURI
		// 状态码
		statusCode := c.Writer.Status()
		// 请求IP
		clientIP := c.ClientIP()
		// 日志格式json
		configs.Log.WithFields(logrus.Fields{
			"status_code":  statusCode,
			"latency_time": strconv.FormatInt(latencyTime.Milliseconds(), 10) + "ms",
			"client_ip":    clientIP,
			"req_method":   reqMethod,
			"req_url":      reqUrl,
			"request":      string(body),
			"response":     writer.body.String(),
		}).Info()
		//logrus.SetReportCaller(true)

		//日志是字符串 Infof
		//configs.Log.Infof("| %3d | %13v | %15s | %s | %s |",
		//	statusCode,
		//	latencyTime,
		//	clientIP,
		//	reqMethod,
		//	reqUrl,
		//)
		if configs.CONFIG.Logger.LogInConsole {
			LoggerToConsole(statusCode, latencyTime, clientIP, reqMethod, reqUrl, string(body), writer.body.String())
		}
	}
}

func LoggerToConsole(statusCode int, latencyTime time.Duration, clientIP string, reqMethod string, reqUrl string, request string, response string) gin.HandlerFunc {
	configs.Log.Out = gin.DefaultWriter
	// 设置日志级别
	configs.Log.SetLevel(logrus.DebugLevel)

	return func(c *gin.Context) {
		// 日志格式json
		configs.Log.WithFields(logrus.Fields{
		"status_code":  statusCode,
		"latency_time": strconv.FormatInt(latencyTime.Milliseconds(), 10) + "ms",
		"client_ip":    clientIP,
		"req_method":   reqMethod,
		"req_url":      reqUrl,
		"request":      request,
		"response":     response,
		}).Info()

		//日志是字符串
		//Logger.Infof("| %3d | %13v | %15s | %s | %s |",
		//	statusCode,
		//	latencyTime,
		//	clientIP,
		//	reqMethod,
		//	reqUrl,
		//)
	}
}

// 日志记录到 MongoDB
func LoggerToMongo() gin.HandlerFunc {
	return func(c *gin.Context) {
	}
}

// 日志记录到 ES
func LoggerToES() gin.HandlerFunc {
	return func(c *gin.Context) {
}
}

// 日志记录到 MQ
func LoggerToMQ() gin.HandlerFunc {
	return func(c *gin.Context) {
}
}

type responseBodyWriter struct {
	gin.ResponseWriter
	body *bytes.Buffer
}

func (r responseBodyWriter) Write(b []byte) (int, error) {
	r.body.Write(b)
	return r.ResponseWriter.Write(b)
}