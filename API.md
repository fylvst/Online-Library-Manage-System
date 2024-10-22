

 # API文档

```shell
服务器地址：101.201.143.17   或者域名：http://stackpop.top
端口：8082
```

```json
//响应结果解释
{
    "code": xxx,
    "message": "xxx",
    "data": xxx
}
code:状态码 自定义
message: 返回的消息
data: 返回给前端的数据
```
## 关注功能

### 添加关注

> /attention/add

- 请求参数


| 字段名         | 类型      | 是否必填 | 描述   | 备注   |
| ----------- | ------- | ---- | ---- | ---- |
| pid     | Integer  | 是    | 顾客id |      |
| bid     | Integer | 是    | 书籍id |      |


- 响应结果

```json

{
    "code": 200,
    "message": "success",
    "data": null
}

//失败
{
    "code": 300,
    "message": "database insert exception",
    "data": null
}
```
### 根据顾客id查询关注的书籍

> /attention/list

- 请求参数


| 字段名         | 类型      | 是否必填 | 描述   | 备注   |
| ----------- | ------- | ---- | ---- | ---- |
| pid     | Integer  | 是    | 顾客id |      |


- 响应结果

```json

{
    "code": 200,
    "message": "success",
    "data": [
        {
            "isDelete": 0,
            "createTime": "2023-04-10 16:49:05",
            "updateTime": "2023-05-06 14:40:21",
            "bid": 768,
            "name": "Thus Spoke Zarathustra",
            "author": "Friedrich Nietzsche",
            "cid": 1,
            "cateName": "Philosophy",
            "nums": 1,
            "available": 0,
            "description": "Nietzsche was one of the most revolutionary and subversive thinkers in Western philosophy, and Thus Spoke Zarathustra remains his most famous and influential work. It describes how the ancient Persian prophet Zarathustra descends from his solitude in the mountains to tell the world that God is dead and that the Superman, the human embodiment of divinity, is his successor. With blazing intensity and poetic brilliance, Nietzsche argues that the meaning of existence is not to be found in religiouspieties or meek submission, but in an all-powerful life force: passionate, chaotic & free.",
            "isbn": "9780141904320",
            "publisher": "Penguin UK",
            "location": "A101"
        },
        {
            "isDelete": 0,
            "createTime": "2023-04-10 16:49:05",
            "updateTime": "2023-04-20 07:39:44",
            "bid": 769,
            "name": "God Is Not Great",
            "author": "Christopher Hitchens",
            "cid": 1,
            "cateName": "Philosophy",
            "nums": 1,
            "available": 0,
            "description": "Whether you\"re a lifelong believer, a devout atheist, or someone who remains uncertain about the role of religion in our lives, this insightful manifesto will engage you with its provocative ideas. With a close and studied reading of the major religious texts, Christopher Hitchens documents the ways in which religion is a man-made wish, a cause of dangerous sexual repression, and a distortion of our origins in the cosmos. With eloquent clarity, Hitchens frames the argument for a more secular life based on science and reason, in which hell is replaced by the Hubble Telescope\"s awesome view of the universe, and Moses and the burning bush give way to the beauty and symmetry of the double helix. In the tradition of Bertrand Russell\"s Why I Am Not a Christian and Sam Harris\"s The End of Faith, Christopher Hitchens makes the ultimate case against religion.",
            "isbn": "0446195340",
            "publisher": "Twelve",
            "location": "A101"
        },
        {
            "isDelete": 0,
            "createTime": "2023-04-10 16:49:05",
            "updateTime": "2023-05-06 14:40:26",
            "bid": 770,
            "name": "Thus Spoke Zarathustra",
            "author": "Friedrich Wilhelm Nietzsche",
            "cid": 1,
            "cateName": "Philosophy",
            "nums": 1,
            "available": 0,
            "description": "A landmark work of philosophy and of literature, Thus Spoke Zarathustra is the fullest expression of Nietzche\"s belief that \"the object of mankind should lie in its highest individuals.\" In his thirtieth year Zarathustra - the archetypal Ubermensch representative of supreme passion and creativity - abandons his home for the mountains, where he lives, literally and figuratively, on a level of experience far above the conventional standards of good and evil. The exuberant, poetic testimony of Nietzche\"s great messianic hero (and alter ego) is a vivid demonstration of the philosopher\"s genius.",
            "isbn": "0192805835",
            "publisher": "Oxford University Press, USA",
            "location": "A101"
        }
    ]
}
```

## 支付功能

> /alipay/pay

| 字段名         | 类型      | 是否必填 | 描述   | 备注   |
| ----------- | ------- | ---- | ---- | ---- |
| subject     | String  | 是    | 交易内容 |      |
| traceNo     | Integer | 是    | 记录id |      |
| totalAmount | Double  | 是    | 支付金额 |      |

## Admin功能

### 注册

> /admin/reg

- 请求参数

|  字段名  |  类型  | 是否必填 | 描述             | 备注 |
| :------: | :----: | :------: | ---------------- | ---- |
| username | String |    是    | 请求注册的用户名 |      |
| password | String |    是    | 密码             |      |

- 响应结果

| 字段名 | 类型 | 描述       |
| ------ | ---- | ---------- |
| data   | void | 无返回结果 |

```json
//响应结果
//成功
{
    "code": 200,
    "message": "register successfully",
    "data": null
}

//失败,用户名已存在
{
    "code": 2000,
    "message": "username already exist",
    "data": null
}
//数据库更新异常
{
    "code": 3000,
    "message": "database server insert exception",
    "data": null
}
```

### 登录

> /admin/login

- 请求参数

|  字段名  |  类型  | 是否必填 | 描述         | 备注 |
| :------: | :----: | -------- | ------------ | ---- |
| username | String | 是       | 登录的用户名 |      |
| password | String | 是       | 密码         |      |

- 响应结果

| 字段名   | 类型    | 描述        |
| -------- | ------- | ----------- |
| aid      | Integer | admin用户id |
| username | String  | 用户名      |

```json
//响应成功
{
    "code": 200,
    "message": "login successfully",
    "data": {
        "isDelete": null,
        "createTime": null,
        "updateTime": null,
        "aid": 1,
        "username": "root",
        "password": null,
        "nickname": null
    }
}
//响应失败
{
    "code": 2000,
    "message": "username or password is wrong",
    "data": null
}
```

### 查询所有patron

> /admin/listPatron

- 请求参数 无
- 响应结果

| 字段名   | 类型   | 描述     |
| -------- | ------ | -------- |
| nickname | String | 用户昵称 |
| username | String | 用户名   |
| phone    | String | 电话号   |
| email    | String | 邮箱     |
| age      | int    |          |
| pid      | int    |          |

```json
{
    "code": 200,
    "message": "success",
    "data": [
        {
            "isDelete": null,
            "createTime": null,
            "updateTime": null,
            "username": "zbq",
            "password": null,
            "nickname": "hhh",
            "age": 12,
            "phone": "13619185732",
            "email": "100@qq.com",
            "pid": 1
        }，
        ......    
    ]
}
```

### 根据pid删除patron

> /admin/delPatron

- 请求参数  

| 字段名 | 类型 | 是否必填 | 描述       | 备注 |
| ------ | ---- | -------- | ---------- | ---- |
| pid    | int  | 是       | patron主键 |      |

- 响应参数  无

```json
{
    "code": 200,
    "message": "success",
    "data": null
}
```

### 查询所有staff

> /admin/listStaff

- 请求参数 无

- 响应结果

| 字段名   | 类型   | 描述     |
| -------- | ------ | -------- |
| nickname | String | 用户昵称 |
| username | String | 用户名   |
| phone    | String | 电话号   |
| email    | String | 邮箱     |
| age      | int    |          |
| sid      | int    |          |

```json
{
    "code": 200,
    "message": "success",
    "data": [
        {
            "isDelete": null,
            "createTime": null,
            "updateTime": null,
            "username": "zbq",
            "password": null,
            "nickname": "alex",
            "age": 12,
            "phone": "911",
            "email": "911@usa.com",
            "sid": 1
        },
		...
}
```



### 根据sid删除staff

> /admin/delStaff

- 请求参数

| 字段名 | 类型 | 是否必填 | 描述      | 备注 |
| ------ | ---- | -------- | --------- | ---- |
| sid    | int  | 是       | staff主键 |      |

```json
{
    "code": 200,
    "message": "success",
    "data": null
}
```



## BorrowRecord

### 借书功能

```
/borrowBook
```

- 请求参数

| 字段名  | 类型      | 是否必填 | 描述       | 备注   |
| ---- | ------- | ---- | -------- | ---- |
| pid  | Integer | 是    | patron主键 |      |
| bid  | Integer | 是    | book id  |      |
| days | Integer | 是    | 借书时间，多少天 |      |

- 响应结果: 无数据部分

```json
//成功
{
    "code": 200,
    "message": "borrow success",
    "data": null
}

//失败
{
    "code": 300,
    "message": "book not available",
    "data": null
}
{
    "code": 3000,
    "message": "database server insert exception",
    "data": null
}
```

### 查看借书记录

```
/getRecord
```

- 请求参数

| 字段名  | 类型      | 是否必填 | 描述   | 备注   |
| ---- | ------- | ---- | ---- | ---- |
| pid  | Integer | 是    | 用户主键 |      |

- 响应结果

| 字段名         | 类型      | 描述           |
| ----------- | ------- | ------------ |
| bid         | Integer | book id      |
| bookname    | String  | 书名           |
| borrow_time | String  | 借书时间         |
| deadline    | String  | 应还书时间        |
| flag        | Integer | 0:未归还  1：已归还 |
| fee         | Double  | 金额           |

```json
//响应示例
{
    "code": 200,
    "message": "success",
    "data": [
        {
            "isDelete": null,
            "createTime": null,
            "updateTime": null,
            "id": null,
            "uid": null,
            "username": null,
            "bid": 800,
            "bookname": "Organon Graece",
            "borrowTime": "2023-04-13T06:42:48.000+00:00",
            "flag": 0,
            "deadline": "2023-04-23T13:40:00.000+00:00",
            "fee": 0
		},
...
```

### ~~还书~~(已废弃)

```
/returnBook
```

- ~~请求参数~~

| 字段名  | 类型      | 是否必填 | 描述   | 备注   |
| ---- | ------- | ---- | ---- | ---- |
| id   | Integer | 是    | 记录id |      |
| bid  | Integer | 是    | 书id  |      |

- ~~响应参数 无~~

```json
//成功
{
    "code": 200,
    "message": "success",
    "data": null
}

//失败
{
    "code": 300,
    "message": "database  exception",
    "data": null
}
```

### 续借图书

> /renew

- 请求参数

| 字段名 | 类型 | 是否必填 | 描述     | 备注 |
| ------ | ---- | -------- | -------- | ---- |
| id     | int  | 是       | 记录id   |      |
| days   | int  | 是       | 续借天数 |      |

- 响应参数 无

```json
//请求成功
{
    "code": 200,
    "message": "success",
    "data": null
}
//请求失败
{
    "code": 500,
    "message": "fail",
    "data": null
}
```



## BOOk

### 添加图书信息

```
/book/add
```

- 请求参数

| 字段名         | 类型      | 是否必填 | 描述    | 备注   |
| ----------- | ------- | ---- | ----- | ---- |
| name        | String  | 是    | 书名    |      |
| author      | String  | 是    | 作者    |      |
| cid         | Integer | 是    | 分类id  |      |
| cateName    | String  | 是    | 分类名   |      |
| nums        | Integer | 是    | 数量    |      |
| available   | Integer | 是    | 可借数量  |      |
| description | String  | 否    | 描述    |      |
| isbn        | String  | 否    | isbn号 |      |
| publisher   | String  | 否    | 出版社   |      |
| location    | String  | 是    | 位置    |      |

- 响应结果
```json
{
    "code": 200,
    "message": "insert success",
    "data": null
}
```


### 根据分类cid查询书籍信息

```
/book/getBookByCid
```

- 请求参数

| 字段名  | 类型      | 是否必填 | 描述   | 备注   |
| ---- | ------- | ---- | ---- | ---- |
| cid  | Integer | 是    | 分类id |      |


- 响应结果

| 字段名         | 类型      | 描述      |
| ----------- | ------- | ------- |
| bid         | Integer | 图书id    |
| name        | String  | 书名      |
| author      | String  | 作者      |
| cid         | Integer | 分类id    |
| cateName    | String  | 分类名     |
| nums        | Integer | 图书总数量   |
| available   | Integer | 当前可借阅数量 |
| description | String  | 描述      |
| isbn        | String  | isbn码   |
| publisher   | String  | 出版社     |
| location    | String  | 位置      |

```json
//cid=52部分响应结果
{
    "code": 200,
    "message": "success",
    "data": [
        {
        "isDelete": null,
        "createTime": null,
        "updateTime": null,
        "bid": 1116,
        "name": "Trade, foreign direct investment, and international technology transfer : a survey",
        "author": "Kamal Saggi",
        "cid": 52,
        "cateName": "Information",
        "nums": 6,
        "available": 6,
        "description": "Abstract: May 2000 - How much a developing country can take advantage of technology transfer from foreign direct investment depends partly on how well educated and well trained its workforce is, how much it isxxxxxxxxxxx",
        "isbn": "9781706080978",
        "publisher": "World Bank Publications",
       	"location": "A101"
    },
    {...}
    ]
}
```



### 根据作者、书名、分类名查询书籍列表

```
/book/list
```

- 请求参数

| 字段名     | 类型      | 是否必填 | 描述      | 备注             |
| ------- | ------- | ---- | ------- | -------------- |
| text    | String  | 否    | 文本内容    | 不填则查询所有        |
| pageNum | Integer | 否    | 页码，从1开始 | 不填默认为1，每页10条数据 |

- 响应结果

| 字段名         | 类型      | 描述      |
| ----------- | ------- | ------- |
| bid         | Integer | 图书id    |
| name        | String  | 书名      |
| author      | String  | 作者      |
| cid         | Integer | 分类id    |
| cateName    | String  | 分类名     |
| nums        | Integer | 图书总数量   |
| available   | Integer | 当前可借阅数量 |
| description | String  | 描述      |
| isbn        | String  | isbn码   |
| publisher   | String  | 出版社     |
| location    | String  | 位置      |

```json
//响应结果示例
{
    "code": 200,
    "message": "success",
    "data": [
        {
        "isDelete": null,
        "createTime": null,
        "updateTime": null,
        "bid": 1116,
        "name": "Trade, foreign direct investment, and international technology transfer : a survey",
        "author": "Kamal Saggi",
        "cid": 52,
        "cateName": "Information",
        "nums": 6,
        "available": 6,
        "description": "Abstract: May 2000 - How much a developing country can take advantage of technology transfer from foreign direct investment depends partly on how well educated and well trained its workforce is, how much it isxxxxxxxxxxx",
        "isbn": "9781706080978",
        "publisher": "World Bank Publications"
    },
    {...}
    ]
}
```

### 图书展示分页查询

> /book/getBookByPage

- 请求参数

| 字段名  | 类型   | 是否必填 | 描述      | 备注   |
| ---- | ---- | ---- | ------- | ---- |
| page | int  | 是    | 页码      |      |
| size | int  | 是    | 每一页展示数量 |      |

- 响应结果

| 字段名  | 类型     | 描述              |
| ---- | ------ | --------------- |
| book | Book对象 | 所有的图书信息         |
| nums | int    | 总数，只有page=1时才返回 |

## Patron

### 注册

> /patron/reg
>
> 完整Get请求URL： 101.201.143.17:8082/patron/reg?username=xxx&password=xxx
>
> Get/Post均可，目前暂时没限制，其他亦如此

- 请求参数

|   字段名    |   类型   | 是否必填 | 描述       | 备注   |
| :------: | :----: | :--: | -------- | ---- |
| username | String |  是   | 请求注册的用户名 |      |
| password | String |  是   | 密码       |      |

- 响应结果

| 字段名  | 类型   | 描述    |
| ---- | ---- | ----- |
| data | void | 无返回结果 |

```json
//响应结果
//成功
{
    "code": 200,
    "message": "register successfully",
    "data": null
}

//失败,用户名已存在
{
    "code": 2000,
    "message": "username already exist",
    "data": null
}
//数据库更新异常
{
    "code": 3000,
    "message": "database server insert exception",
    "data": null
}
```

### 登录

> /patron/login

- 请求参数

|   字段名    |   类型   | 是否必填 | 描述     | 备注   |
| :------: | :----: | ---- | ------ | ---- |
| username | String | 是    | 登录的用户名 |      |
| password | String | 是    | 密码     |      |

- 响应结果

| 字段名      | 类型      | 描述         |
| -------- | ------- | ---------- |
| pid      | Integer | patron用户id |
| username | String  | 用户名        |

```json
//响应成功
{
    "code": 200,
    "message": "login successfully",
    "data": {
        "isDelete": null,
        "createTime": null,
        "updateTime": null,
        "username": "ccc",
        "password": null,
        "nickname": null,
        "age": null,
        "phone": null,
        "email": null,
        "pid": 4
	}
}
//响应失败
{
    "code": 2000,
    "message": "username or password is wrong",
    "data": null
}
```

### 获取个人信息

> /patron/getByPid
>
> 获取昵称，年龄，电话，邮箱

- 请求参数

| 字段名  |   类型    | 是否必填 | 描述   | 备注   |
| :--: | :-----: | ---- | ---- | ---- |
| pid  | Integer | 是    | 主键   |      |

- 响应结果

| 字段名      | 类型     | 描述   |
| -------- | ------ | ---- |
| nickname | String | 用户昵称 |
| username | String | 用户名  |
| phone    | String | 电话号  |
| email    | String | 邮箱   |

```json
//响应成功
{
    "code": 200,
    "message": "success",
    "data": {
        "isDelete": null,
        "createTime": null,
        "updateTime": null,
        "username": "ccc",
        "password": null,
        "nickname": "ccc",
        "age": 23,
        "phone": "18290901101",
        "email": "222@qq.com",
        "pid": null
	}
}
```

### 更改个人信息

> /patron/updateInfo
>

- 请求参数

|   字段名    |   类型    | 是否必填 | 描述   | 备注   |
| :------: | :-----: | ---- | ---- | ---- |
|   pid    | Integer | 是    | 用户主键 |      |
| nickname | String  | 否    | 改后昵称 |      |
|   age    | Integer | 否    | 年龄   |      |
|  phone   | String  | 否    | 电话号  |      |
|  email   | String  | 否    | 邮箱   |      |

- 响应结果

| 字段名  | 类型   | 描述   |
| ---- | ---- | ---- |
| data | void | 无数据  |

```json
//响应结果成功
{
    "code": 200,
    "message": "update successfully",
    "data": null
}
//失败，几乎不会发生
//数据库更新异常
{
    "code": 3000,
    "message": "database server insert exception",
    "data": null
}
```

## Category

### 添加Category接口

- 接口描述：用于添加一个Category分类。
- 请求URL：`/category/add`
- 请求方式：POST
- 参数说明：

| 参数名称     | 是否必须 | 数据类型   | 默认值  | 说明   |
| -------- | ---- | ------ | ---- | ---- |
| cateName | 是    | String |      | 分类名称 |

- 返回示例：

成功情况下返回：

```java
{
    "code":200,
    "message":"添加成功",
    "data":null
}
```

失败情况下返回：

```java
{
    "code":500,
    "message":"添加失败",
    "data":null
}
```

### 删除Category接口

- 接口描述：用于删除指定ID的Category分类。
- 请求URL：`/category/del`
- 请求方式：DELETE
- 参数说明：

| 参数名称 | 是否必须 | 数据类型    | 默认值  | 说明   |
| ---- | ---- | ------- | ---- | ---- |
| cid  | 是    | Integer |      | 分类ID |

- 返回示例：

成功情况下返回：

```Java
{
    "code":200,
    "message":"删除成功",
    "data":null
}
```

失败情况下返回：

```Java
{
    "code":500,
    "message":"删除失败",
    "data":null
}
```

### 修改Category接口

- 接口描述：用于修改指定ID的Category分类。
- 请求URL：`/category/update`
- 请求方式：PUT
- 参数说明：

| 参数名称     | 是否必须 | 数据类型    | 默认值  | 说明   |
| -------- | ---- | ------- | ---- | ---- |
| cid      | 是    | Integer |      | 分类ID |
| cateName | 是    | String  |      | 分类名称 |

- 返回示例：

成功情况下返回：

```Java
{
    "code":200,
    "message":"修改成功",
    "data":null
}
```

失败情况下返回：

```Java
{
    "code":500,
    "message":"修改失败",
    "data":null
}
```

### 查询Category列表接口

- 接口描述：用于查询所有的Category分类列表。
- 请求URL：`/category/list`
- 请求方式：GET
- 参数说明：无参数
- 返回示例：

成功情况下返回：

```Java
{
    "code":200,
    "message":"查询成功",
    "data":[
        "分类1",
        "分类2"
    ]
}
```

失败情况下返回：

```Java
{
    "code":500,
    "message":"查询失败",
    "data":null
}
```
## Staff

### 注册

> /staff/reg
- 请求参数

|   字段名    |   类型   | 是否必填 | 描述       | 备注   |
| :------: | :----: | :--: | -------- | ---- |
| username | String |  是   | 请求注册的用户名 |      |
| password | String |  是   | 密码       |      |

- 响应结果

| 字段名  | 类型   | 描述    |
| ---- | ---- | ----- |
| data | void | 无返回结果 |

```json
//响应结果
//成功
{
    "code": 200,
    "message": "register successfully",
    "data": null
}

//失败,用户名已存在
{
    "code": 2000,
    "message": "username already exist",
    "data": null
}
//数据库更新异常
{
    "code": 3000,
    "message": "database server insert exception",
    "data": null
}
```
### 登录
> /staff/login

- 请求参数

|   字段名    |   类型   | 是否必填 | 描述     | 备注   |
| :------: | :----: | ---- | ------ | ---- |
| username | String | 是    | 登录的用户名 |      |
| password | String | 是    | 密码     |      |

- 响应结果

| 字段名      | 类型      | 描述       |
| -------- | ------- | -------- |
| sid      | Integer | Staff的id |
| username | String  | 用户名      |

```json
//响应成功
{
    "code": 200,
    "message": "login successfully",
    "data": {
        "isDelete": null,
        "createTime": null,
        "updateTime": null,
        "username": "aaa",
        "password": null,
        "nickname": null,
        "age": null,
        "phone": null,
        "email": null,
        "sid": 2
	}
}
//响应失败
{
    "code": 2000,
    "message": "username or password is wrong",
    "data": null
}
```

### 获取个人信息

> /staff/getBySid
>
> 获取昵称，年龄，电话，邮箱

- 请求参数

| 字段名  |   类型    | 是否必填 | 描述   | 备注   |
| :--: | :-----: | ---- | ---- | ---- |
| sid  | Integer | 是    | 用户主键 |      |

- 响应结果

| 字段名      | 类型     | 描述   |
| -------- | ------ | ---- |
| nickname | String | 用户昵称 |
| username | String | 用户名  |
| phone    | String | 电话号  |
| email    | String | 邮箱   |

```json
//响应成功
{
    "code": 200,
    "message": "success",
    "data": {
        "isDelete": null,
        "createTime": null,
        "updateTime": null,
        "username": "aaa",
        "password": null,
        "nickname": "pissenlit",
        "age": 18,
        "phone": "911",
        "email": null,
        "sid": null
    }
}
```

### 更改个人信息

> /staff/updateInfo
>
> 下面四个至少传一个吧

- 请求参数

|   字段名    |   类型    | 是否必填 | 描述   | 备注   |
| :------: | :-----: | ---- | ---- | ---- |
|   sid    | Integer | 是    | 用户主键 |      |
| nickname | String  | 否    | 改后昵称 |      |
|   age    | Integer | 否    | 年龄   |      |
|  phone   | String  | 否    | 电话号  |      |
|  email   | String  | 否    | 邮箱   |      |

- 响应结果

| 字段名  | 类型   | 描述   |
| ---- | ---- | ---- |
| data | void | 无数据  |

```json
//响应结果成功
{
    "code": 200,
    "message": "update successfully",
    "data": null
}
//失败，几乎不会发生
//数据库更新异常
{
    "code": 3000,
    "message": "database server insert exception",
    "data": null
}
```