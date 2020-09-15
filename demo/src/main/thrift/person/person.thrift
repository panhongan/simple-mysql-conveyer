namespace java com.github.panhongan.demo.thrift

include "db_base.thrift"

struct Person {
    1: db_base.DbBase db_base;
    2: string name;
    3: db_base.timestamp birthday;
}
