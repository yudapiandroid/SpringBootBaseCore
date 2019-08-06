#### Spring Boot 简化项目

- 简化了Controller
- 自定义了Mybatis的通用DAO


#### 项目依赖
```$xml
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>1.3.2</version>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <version>2.1.3.RELEASE</version>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.59</version>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>com.github.pagehelper</groupId>
            <artifactId>pagehelper-spring-boot-starter</artifactId>
            <version>1.2.10</version>
            <scope>provided</scope>
        </dependency>
```


#### 使用方式

- 添加仓库地址
```$xml
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://www.jitpack.io</url>
		</repository>
	</repositories>
```

- 添加依赖
```$xml
    <dependency>
	    <groupId>com.github.yudapiandroid</groupId>
	    <artifactId>SpringBootBaseCore</artifactId>
	    <version>1.0.0</version>
	</dependency>
```

- 项目入口添加注解: @EnableCommonController

