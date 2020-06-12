# springboot-task-quartz

> 此 demo 主要演示了 Spring Boot 如何集成 Quartz 定时任务，并实现对定时任务的管理，包括新增定时任务，删除定时任务，暂停定时任务，恢复定时任务，修改定时任务启动时间，以及定时任务列表查询。
## 前端

> 前端页面请 clone 本项目，查看具体代码

## 启动

1. clone 本项目
2. 初始化表格
3. 启动 `SpringBootDemoTaskQuartzApplication.java`
4. 打开浏览器，查看 http://localhost:8080/demo/job.html 

## 参考

- Spring Boot 官方文档：https://docs.spring.io/spring-boot/docs/2.1.0.RELEASE/reference/htmlsingle/#boot-features-quartz

- Quartz 官方文档：http://www.quartz-scheduler.org/documentation/quartz-2.2.x/quick-start.html

- Quartz 重复调度问题：https://segmentfault.com/a/1190000015492260

- 关于Quartz定时任务状态 (在 `QRTZ_TRIGGERS` 表中的 `TRIGGER_STATE` 字段)

  ![image-20181126171110378](assets/image-20181126171110378-3223470.png)

- Vue.js 官方文档：https://cn.vuejs.org/v2/guide/

- Element-UI 官方文档：http://element-cn.eleme.io/#/zh-CN
