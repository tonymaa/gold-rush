# 📈 Gold Rush — 黄金价格提醒系统

> **Gold Rush** 是一个实时黄金价格提醒项目，帮助用户跟踪黄金行情，并在价格达到设定阈值时发送通知。([GitHub](https://github.com/tonymaa/gold-rush))

---

## 🧠 项目简介 | Overview

Gold Rush 提供了一个简单的全栈解决方案，包括：

* **实时黄金价格抓取**
* **价格提醒通知**
* **前端可视化展示**
* **Android 移动端支持**
* **轻量数据库存储（SQLite）**

适合希望实时监控黄金价格的用户或进行价格策略研究的开发者。

---

## 🚀 快速开始 | Quick Start

### 1. 克隆仓库 | Clone the repository

```bash
git clone https://github.com/tonymaa/gold-rush.git
cd gold-rush
```

### 2. 使用 Docker 启动 | Start with Docker

```bash
# 构建 Docker 镜像
docker build -t gold-rush .

# 启动容器
docker run -p 800:800 --name gold-rush-container gold-rush

# 或者如果提供 docker-compose.yml
docker-compose up -d
```

### 3. 访问前端 | Access frontend

在浏览器访问：

```
http://localhost:800
```

或者使用 Android 客户端查看实时黄金价格。

---

## 🗂 项目结构 | Project Structure

```
gold-rush/
├── android/                 # Android 客户端
├── backend/goldRush/        # 后端服务，处理价格抓取与提醒逻辑
├── frontend/                # 前端 Web 可视化
├── sqlite/                  # SQLite 数据库文件和初始化脚本
├── Dockerfile               # Docker 镜像构建配置
├── docker-compose.yml       # Docker Compose 配置
├── start.sh                 # 启动脚本
└── README.md                # 项目说明
```

---

## 🧰 技术栈 | Tech Stack

* **后端**：Java
* **前端**：TypeScript + React
* **移动端**：Android
* **数据库**：SQLite
* **部署**：Docker

---

## 🔔 功能 | Features

1. 实时黄金价格抓取
2. 价格上下限提醒通知
3. 历史价格存储与可视化
4. Android 移动端实时查看
5. 可自定义提醒阈值和通知方式

---

## 📄 开源协议 | License

本项目使用 **MIT License**。
[GitHub](https://github.com/tonymaa/gold-rush)

---
