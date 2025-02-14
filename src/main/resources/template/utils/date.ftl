package utils

import "time"

type Date time.Time

const (
	timeFormart1 = "2006-01-02"
)

func (t *Date) UnmarshalJSON(data []byte) (err error) {
	now, err := time.ParseInLocation(`"`+timeFormart1+`"`, string(data), time.Local)
	*t = Date(now)
	return
}

func (t Date) MarshalJSON() ([]byte, error) {
	b := make([]byte, 0, len(timeFormart1)+2)
	b = append(b, '"')
	b = time.Time(t).AppendFormat(b, timeFormart1)
	b = append(b, '"')
	return b, nil
}

func (t Date) String() string {
	return time.Time(t).Format(timeFormart1)
}
