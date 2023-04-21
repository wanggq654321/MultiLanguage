# MultiLanguage
MultiLanguage服务端动态下发多语言国际化解决方案

实现原理：
1.参考原生Resources实现
2.使用弱引用关联页面Context页面的Strings,主动回收内存
3.使用mmap保存动态下发的国际化 key-value 
