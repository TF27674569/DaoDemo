# 数据库封装
## 引入依赖
### **compile 'com.tianfeng:dao:1.0.0'** </br></br>

## 配置
### 在项目主入口添加初始化操作
```java
public class App extends Application {
    private static final int DB_VERSION = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        DaoFactory.Builder
                .app(this)
                .dbName("test.db")
                .dbVersion(DB_VERSION)
                .build();
    }
  }
```
### 如果需要对数据库进行升级操作
```java
public class App extends Application {
    private static final int DB_VERSION = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        DaoFactory.Builder
                .app(this)
                .dbName("test.db")
                .dbVersion(DB_VERSION)
                .dbDaoSupport(new IUpgradeSupport() {
                    @Override
                    public <T> void onUpgradeSql(SQLiteDatabase db, int oldVersion, int newVersion, Class<T> clazz, String tableName) {
                        if ((clazz == Person.class)) {
                            String upgrade = "alter table " + tableName + " add sex text";//增加一个列sex
                            db.execSQL(upgrade);
                        }
                        Log.e("TAG", "onUpgradeSql: " + tableName);
                    }
                })
                .build();
    }
}

```

### 使用
#### 1. 生成实体类
```java
@Table // 表示是一张表
public class Person {

    @Column// 表示是表的列
    public String name;// 访问符可以为私有
    
    @Column
    public int age;

    @Column
    public String sex;
    
    // 一定要有一个空的构造器 私有也可以
    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }


    public Person(String name, int age, String sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }
    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                '}';
    }
}

```
#### 2. 调用
```java
 // 创建表生成操作对象
 IDaoSupport<Person> daoSupport = DaoFactory.get().getDaoSupportAndCreateTable(Person.class);
 
 // 添加
   List<Person> datas = new ArrayList<>();
   for (int i = 0; i < 100; i++) {
       datas.add(new Person("张三" + i, i,"男"));
   }
   daoSupport.insert(datas);
   
   // 查询
   List<Person> datas = daoSupport.querySupport().query();
 
```

### 增删改查可根据相关字符进行拼接sql语句执行

