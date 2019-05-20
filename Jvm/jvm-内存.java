----------------------------
内存区域					|
----------------------------
	# 运行时数据区

		-- 线程共享
		堆(Heap)
		方法区(Method Area)
		
					
		
		-- 线程私有
		虚拟机栈(VM Stack)
		本地方法栈(Native Method Stack)
		程序计数器(Program Counter Register)

------------------------------------
方法区(Method Area)					|
------------------------------------
	# 每个线程共享的内存区域
	# 它存储已被虚拟机加载的类信息,常量,静态变量,即时编译器编译后的代码等数据
		- 类信息(类名, 访问修饰符, 字段描述, 方法描述...)
		- 常量池
		- ...

	# Jvm规范把它描述为堆的一个逻辑部分,所以也被成为: Non Heap(非堆),目的是为了跟堆区分开来

	# 蛮多人也称它为永久代(Permanent Generation)
		* 因为HotSpot虚拟机的设计团队,把GC分代收集器扩展到了方法区(就是使用了永久代来实现方法区)
		* 好处就是能够省去专门为方法区编译内存管理代码的工作
		* 怎么去实现方法区,不受虚拟机规范的约束
	
	# 在jdk1.8以后,HotSpot虚拟机已经放弃永久代,该用 Native Memory (Metaspeace)来实现方法区

	
	# 如果方法区无法满足内存分配需求的时候会抛出异常:OutOfMemoryError

	# 运行时常量池也是方法区的一部分
		* class 文件中除了了有类的版本,字段,方法,接口等描述信息外
		* 还存放了编译时期生成的各种字面量和符号引用,这部分内容将在类加载后存放进方法区的常量池

		* 它作为方法区的一部分,也受限于方法区,如果常量池无法申请到内存,就会抛出异常:OutOfMemoryError


------------------------------------
虚拟机栈(VM Stack)					|
------------------------------------
	# 线程私有的,它的生命周期跟线程的生命周期一样
	# 执行每个Java方法时,就会创建一个栈帧,在栈帧里面会存储一些东西
		* 存储局部变量表
			- 编译之间可预知的一些数据类型:boolean,byte,char,short,int,long,float,double,对象引用,rerurnAddress(指向一条字节码指定的地址)
			- 64位长度的 long 和 double 会占用两个局部变量空间(slot),其余的数据类型只会占用1个
			- 局部变量表所需要的内存空间,在编译的时候就已经确定了,不能修改

		* 操作数栈
		* 动态链接
		* 方法出口等信息
	
	# 每一个方法从调用到结束,就对应着一个栈帧在jvm中入栈到出栈

	# 这个区域可能会抛出两种异常
		StackOverflowError
			* 不当的递归或者其他操作,导致了栈的深度大于了虚拟机允许的深度

		OutOfMemoryError
			* 不能申请到足够的内存来开辟新的栈帧
	
------------------------------------
本地方法栈(Native Method Stack)		|
------------------------------------
	# 它与虚拟机栈其实是一样的,服务的对象不同
	# 虚拟机栈为Java的方法提供运行空间,本地方法栈为本地方法提供了运行空间
		* 虚拟机规范对于本地方法栈使用的语言, 数据结构, 使用方式都没有强制约束
		* HotSpot虚拟机, 甚至直接就把本地方法栈和虚拟机栈合二为一

	# 它也也可能会抛出异常:StackOverflowError,OutOfMemoryError
	

------------------------------------
程序计数器(Program Counter Register)|
------------------------------------
	# 一块儿较小的内存

	# 可以当看做是当前线程所执行的字节码行号指示器
		* 字节码解释器工作的时候,就是通过修改这个计数器的值来选取下一条要执行的字节码指令
		* if while break continue goto ... 线程的恢复等等功能都需要依赖这个计数器
	
	# 如果正在执行一个Java方法,则该计数器的值是虚拟机字节码指令的地址

	# 如果正在执行一个Native方法,责该计数器的值是:undefined

	# 它是一个线程私有的内存区域,在每个线程中独立存在,互不影响

	# 它是唯一一个在JVM虚拟机规范中不存在:OutOfMemoryError 情况的区域

	# 它只是一个概念模型,不同的JVM可能会通过其他更为高效的方式去实现


------------------------------------
直接内存							|
------------------------------------
	# 它不是Jvm规范的一部分,但是却可以被频繁的使用

	# NIO中的 Channel/Buffer,可以使用 Natice 函数直接分配堆外内存
		* DirectByteBuffer
		* 可以带来显著的行性能提升,因为避免了Java堆和Natice堆中来回的复制数据
	
	# 直接内存的分配只会受到物理内存,处理器寻址空间的限制