------------------------
镜像构建				|
------------------------
	# 注册
		https://hub.docker.com/

	# 登录 &  退出登录
		docker login
		docker logout
	


------------------------
Commit					|
------------------------
	# 其实是更新了本地的镜像,类似于版本控制器的操作
		* 先根据镜像创建容器
		* 修改了容器信息(安装了软件配置啥的)
		* 再把这个修改过的容器作为新的镜像提交到本地
	

	docker commit [id] [respository]/[name]:[tag]
		id
			* 容器id
		respository
			* 仓库名
		name
			* 镜像名称
		tag
			* 标签
		-m
			* 备注信息
				-m "这是备注信息"
		--a
			* 指定作者信息
				-a "KevinBlandy"
			
	
	# commoit 提交的仅仅修改的部分,所以更新操作非常的轻量


------------------------
Build					|
------------------------
	# 根据Dockerfile构建

	docker build -t [repository]:[name]:[tag] .
		repository
		name
		tag
			* 仓库,镜像名称,标签
			* 如果未指定标签,则默认会添加一个 last 的标签
		
		-f	
			*  如果当前目录下没有:Dockerfile文件,那么就要通过该参数指定Dockerfile的路径
			*  而且,如果是通过该参数指定的文件,非必须命名为:Dockerfile

		--build-arg
			* 为Df文件中声明的配置项(ARG)赋值
				--build-arg name=root

		--no-cache
			* 禁止docker的构建缓存
	
	# .dockerignore 文件
		* 如果构建的上下文环境中存在该文件的话
		* 那么该文件中列出的文件不会被当作构建上下文的一部分
		* 防止它们被上传到Docker守护进程中去
		* 跟 .gitignore 文件一样
		* 匹配模式采用了Go语言中的 filepath(???咋又是GO的东西)
	
	
	# Docker 构建缓存
		* Docker非常的聪明其实,它的构建过程存在缓存机制
		* 它会把每一步的执行都构建成一个镜像,缓存起来
		* 会把之前构建的镜像,当作缓存,并且基于它去执行新的构建
		* 假设执行:1,2,3步都成功了,在执行第4步的时候失败
		* 修改第4步的配置信息,重新构建时,仅仅只会从第4步开始构建


	
	# 查看构建的过程(执行栈)
		docker history [id]
			id 
				* image id
			————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
			IMAGE               CREATED             CREATED BY                                      SIZE                COMMENT
			860466ea6f08        19 minutes ago      /bin/sh -c #(nop)  EXPOSE 80                    0B                  
			46cfd6b742e2        19 minutes ago      /bin/sh -c echo 'vim & git &install success!…   0B                  
			7c8f32c5a997        22 minutes ago      /bin/sh -c yum -y install git                   40.2MB              
			881201610d17        22 minutes ago      /bin/sh -c yum -y install vim                   126MB               
			83c083061e4e        23 minutes ago      /bin/sh -c #(nop)  MAINTAINER KevinBlandy "7…   0B                  
			1e1148e4cc2c        4 weeks ago         /bin/sh -c #(nop)  CMD ["/bin/bash"]            0B                  
			<missing>           4 weeks ago         /bin/sh -c #(nop)  LABEL org.label-schema.sc…   0B                  
			<missing>           4 weeks ago         /bin/sh -c #(nop) ADD file:6f877549795f4798a…   202MB         
			————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
				
			
		
------------------------
推送到hub				|
------------------------