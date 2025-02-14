package configs

import (
	"crypto/tls"
	"fmt"
	"github.com/jordan-wright/email"
	"net/smtp"
	"strings"
)

type Email struct {
	To       string `mapstructure:"to" json:"to" yaml:"to"`                   // 收件人:多个以英文逗号分隔
	Port     int    `mapstructure:"port" json:"port" yaml:"port"`             // 端口
	From     string `mapstructure:"from" json:"from" yaml:"from"`             // 发件人
	Host     string `mapstructure:"host" json:"host" yaml:"host"`             // 服务器地址
	IsSSL    bool   `mapstructure:"is-ssl" json:"isSSL" yaml:"is-ssl"`        // 是否SSL
	Secret   string `mapstructure:"secret" json:"secret" yaml:"secret"`       // 密钥
	Nickname string `mapstructure:"nickname" json:"nickname" yaml:"nickname"` // 昵称
}

func Send(to string, subject string, body string) error {
	from := CONFIG.Email.From
	nickname := CONFIG.Email.Nickname
	secret := CONFIG.Email.Secret
	host := CONFIG.Email.Host
	port := CONFIG.Email.Port
	isSSL := CONFIG.Email.IsSSL

	auth := smtp.PlainAuth("", from, secret, host)
	e := email.NewEmail()
	if nickname != "" {
		e.From = fmt.Sprintf("%s <%s>", nickname, from)
	} else {
		e.From = from
	}
	e.To = strings.Split(to, ",")
	e.Subject = subject
	e.HTML = []byte(body)
	var err error
	hostAddr := fmt.Sprintf("%s:%d", host, port)
	if isSSL {
		err = e.SendWithTLS(hostAddr, auth, &tls.Config{ServerName: host})
	} else {
		err = e.Send(hostAddr, auth)
	}
	return err
}
