# 校园互助跑腿系统 API 接口文档

本文档描述了校园互助跑腿系统的后端接口。

## 1. 用户认证 (Auth)

### 1.1 注册用户
- **URL**: `/auth/register`
- **Method**: `POST`
- **描述**: 注册新用户。
- **请求 Body**:
  ```json
  {
    "username": "用户名",
    "password": "密码",
    "phone": "手机号",
    "userType": 1 // 1:买家, 3:商家
  }
  ```
- **响应**: 注册成功的用户信息。

### 1.2 用户登录
- **URL**: `/auth/login`
- **Method**: `POST`
- **描述**: 用户登录获取 Token。
- **请求 Body**:
  ```json
  {
    "username": "用户名",
    "password": "密码"
  }
  ```
- **响应**:
  ```json
  {
    "code": 1,
    "data": {
      "token": "JWT_TOKEN",
      "user": { ... }
    }
  }
  ```

### 1.3 获取个人信息
- **URL**: `/auth/profile`
- **Method**: `GET`
- **Header**: `token: <JWT_TOKEN>`
- **描述**: 获取当前登录用户的详细信息。

### 1.4 查询用户列表
- **URL**: `/auth/query`
- **Method**: `GET`
- **描述**: 获取所有用户列表。

### 1.5 根据手机号查询用户
- **URL**: `/auth/query/{phone}`
- **Method**: `GET`
- **Path 参数**: `phone` (手机号)

## 2. 地址管理 (Address)

### 2.1 获取地址列表
- **URL**: `/addresses`
- **Method**: `GET`
- **Header**: `token: <JWT_TOKEN>`
- **描述**: 获取当前用户的收货地址列表。

### 2.2 创建地址
- **URL**: `/addresses`
- **Method**: `POST`
- **Header**: `token: <JWT_TOKEN>`
- **请求 Body**:
  ```json
  {
    "consignee": "收货人",
    "phone": "手机号",
    "detail": "详细地址",
    "isDefault": 0 // 0:否, 1:是
  }
  ```

### 2.3 更新地址
- **URL**: `/addresses/{id}`
- **Method**: `PUT`
- **Header**: `token: <JWT_TOKEN>`
- **Path 参数**: `id` (地址ID)
- **请求 Body**: 同创建地址。

### 2.4 删除地址
- **URL**: `/addresses/{id}`
- **Method**: `DELETE`
- **Header**: `token: <JWT_TOKEN>`
- **Path 参数**: `id` (地址ID)

### 2.5 设为默认地址
- **URL**: `/addresses/{id}/default`
- **Method**: `PATCH`
- **Header**: `token: <JWT_TOKEN>`
- **Path 参数**: `id` (地址ID)

## 3. 分类管理 (Category)

### 3.1 获取分类列表
- **URL**: `/categories`
- **Method**: `GET`
- **Query 参数**: `parentId` (可选，父分类ID)

### 3.2 创建分类 (商家)
- **URL**: `/categories`
- **Method**: `POST`
- **Header**: `token: <JWT_TOKEN>` (必须是商家)
- **请求 Body**:
  ```json
  {
    "name": "分类名称",
    "parentId": 0,
    "sort": 1,
    "status": 1
  }
  ```

### 3.3 更新分类 (商家)
- **URL**: `/categories/{id}`
- **Method**: `PUT`
- **Header**: `token: <JWT_TOKEN>` (必须是商家)
- **Path 参数**: `id` (分类ID)
- **请求 Body**: 同创建分类。

### 3.4 更新分类状态 (商家)
- **URL**: `/categories/{id}/status`
- **Method**: `PATCH`
- **Header**: `token: <JWT_TOKEN>` (必须是商家)
- **Path 参数**: `id` (分类ID)
- **请求 Body**: `{"status": 1}`

## 4. 商品管理 (Product)

### 4.1 搜索商品
- **URL**: `/products`
- **Method**: `GET`
- **Query 参数**:
  - `categoryId`: 分类ID
  - `keyword`: 关键词
  - `minPrice`: 最低价
  - `maxPrice`: 最高价
  - `status`: 商品状态 (1:上架)
  - `sellerId`: 商家ID
  - `sortBy`: 排序字段 (price, sales, create_time)
  - `order`: 排序方式 (asc, desc)

### 4.2 商品详情
- **URL**: `/products/{id}`
- **Method**: `GET`
- **Path 参数**: `id` (商品ID)

### 4.3 创建商品 (商家)
- **URL**: `/products`
- **Method**: `POST`
- **Header**: `token: <JWT_TOKEN>` (必须是商家)
- **请求 Body**:
  ```json
  {
    "categoryId": 1,
    "name": "商品名称",
    "price": 10.00,
    "stock": 100,
    "description": "描述",
    "image": "图片URL"
  }
  ```

### 4.4 更新商品 (商家)
- **URL**: `/products/{id}`
- **Method**: `PUT`
- **Header**: `token: <JWT_TOKEN>` (必须是商家)
- **Path 参数**: `id` (商品ID)
- **请求 Body**: 同创建商品。

### 4.5 更改商品状态 (商家)
- **URL**: `/products/{id}/status`
- **Method**: `PATCH`
- **Header**: `token: <JWT_TOKEN>` (必须是商家)
- **Path 参数**: `id` (商品ID)
- **请求 Body**: `{"status": 1}`

### 4.6 调整库存 (商家)
- **URL**: `/products/{id}/stock`
- **Method**: `PATCH`
- **Header**: `token: <JWT_TOKEN>` (必须是商家)
- **Path 参数**: `id` (商品ID)
- **Query 参数**:
  - `delta`: 增量 (例如 -10 表示减少10)
  - `stock`: 直接设置库存值 (二选一)

## 5. 订单管理 (Order)

### 5.1 创建订单
- **URL**: `/orders`
- **Method**: `POST`
- **Header**: `token: <JWT_TOKEN>`
- **请求 Body**:
  ```json
  {
    "addressId": 1,
    "items": [
      { "productId": 1, "quantity": 2 }
    ],
    "remark": "备注"
  }
  ```

### 5.2 支付订单
- **URL**: `/orders/{orderNo}/pay`
- **Method**: `POST`
- **Header**: `token: <JWT_TOKEN>`
- **Path 参数**: `orderNo` (订单号)

### 5.3 取消订单
- **URL**: `/orders/{orderNo}/cancel`
- **Method**: `POST`
- **Header**: `token: <JWT_TOKEN>`
- **Path 参数**: `orderNo` (订单号)
- **请求 Body**: `{"cancelReason": "不想要了"}` (可选)

### 5.4 发货 (商家)
- **URL**: `/orders/{orderNo}/ship`
- **Method**: `POST`
- **Header**: `token: <JWT_TOKEN>` (必须是商家)
- **Path 参数**: `orderNo` (订单号)

### 5.5 确认收货
- **URL**: `/orders/{orderNo}/confirm`
- **Method**: `POST`
- **Header**: `token: <JWT_TOKEN>`
- **Path 参数**: `orderNo` (订单号)

### 5.6 获取我的订单
- **URL**: `/orders`
- **Method**: `GET`
- **Header**: `token: <JWT_TOKEN>`
- **Query 参数**: `status` (订单状态过滤)

### 5.7 获取商家订单 (商家)
- **URL**: `/orders/seller`
- **Method**: `GET`
- **Header**: `token: <JWT_TOKEN>` (必须是商家)
- **Query 参数**: `status` (订单状态过滤)

### 5.8 订单详情
- **URL**: `/orders/{orderNo}`
- **Method**: `GET`
- **Header**: `token: <JWT_TOKEN>`
- **Path 参数**: `orderNo` (订单号)
- **响应**: 包含 `order` (订单基本信息) 和 `items` (订单商品列表)。

## 6. 评价管理 (Review)

### 6.1 添加评价
- **URL**: `/orders/{orderNo}/reviews`
- **Method**: `POST`
- **Header**: `token: <JWT_TOKEN>`
- **Path 参数**: `orderNo` (订单号)
- **请求 Body**:
  ```json
  {
    "productId": 1,
    "rating": 5,
    "content": "好评！"
  }
  ```

### 6.2 获取商品评价
- **URL**: `/products/{productId}/reviews`
- **Method**: `GET`
- **Path 参数**: `productId` (商品ID)

## 7. 钱包管理 (Wallet)

### 7.1 获取交易记录
- **URL**: `/wallet/transactions`
- **Method**: `GET`
- **Header**: `token: <JWT_TOKEN>`

### 7.2 充值
- **URL**: `/wallet/recharge`
- **Method**: `POST`
- **Header**: `token: <JWT_TOKEN>`
- **请求 Body**: `{"amount": 100.00}`

---
**通用响应格式**:
```json
{
  "code": 1, // 1:成功, 0:失败
  "msg": "success", // 错误信息
  "data": { ... } // 数据载体
}
```
