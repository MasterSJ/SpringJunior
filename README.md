# SpringJunior
尝试自己动手来实现一些Spring的主要功能（目前暂定实现ioc和aop）

2018-05-17：
刚刚把aop整合进去了，算是基本完成ioc+aop的功能了，当然，可能还有很多特殊情况没有考虑到，毕竟java语言的写法还是很多样的，
这个项目也是用来练手的，我也没有想过要将它做成spring这样大而全的东西，主要用来熟悉实现原理，在此基础上尽量考虑更多的情况，
所以我最后还是决定将它定为v0.5，算是达到一半的目的了吧，如果就机会就继续完善，否则再稍加改进也算是完成熟悉实现原理的任务了。

2018-08-03：
今天突发奇想，如果我想要做java方法的性能分析，可以做吗？想了一下无非就是计算方法执行前后的时间差，感觉这个东西就是一个aop的东西，于是尝试了一下，确实可以。
使用方法，在想要分析执行结果的方法上面添加注解@SjPerformanceAnalysis（当然此方法所属类也必须包含在配置的扫描根路径下）；这样在执行的时候控制台会打印
此方法执行所消耗的毫秒数，如果想将此结果以其他方式输出，那么可以自定义一个类实现cn.wws.springjunior.itf.SjInputOutputInterface接口即可。
