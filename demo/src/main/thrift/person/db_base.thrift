namespace java com.github.panhongan.demo.thrift

typedef i64 timestamp

struct DbBase {
    1: i64 id;
    2: string created_by;
    3: timestamp created_at;
    4: string updated_by;
    5: timestamp updated_at;
}
