#项目简介

### 框架结构如下

- action
  - ApiActionModel  用来用来存储接口对象yaml反序列化出来的action单元
- apiObject
  - apiObjectModel 用来用来存储apiObject yaml反序列化出来的ApiObject实体对象
  - ApiLoader 用来加载api对象和获取某个action
- global
  - baseResult  响应信息
  - globalVariable 全局变量
- step
  - AssertModel 用来存储反序列化出来的断言对象
  - StepResult 用来存储请求的相应信息和断言结果
  - StepModel 用例具体测试步骤
-testcase
  -TestCaseModel 用来用来存储testcase yaml反序列化出来的TestCase实体对象
-test
  - 用例执行
 

--------
待完善