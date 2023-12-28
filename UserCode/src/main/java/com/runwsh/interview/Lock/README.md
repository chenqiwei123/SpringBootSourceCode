## 1、Java中有哪几种方式来创建线程执行任务？

【源码】

[ThreadDemo.java](..%2FA001Thread%2FThreadDemo.java)
**1. 继承Thread类**

> 使用继承Thread类。重写run方法。
> 用继承thread类的实例对象调用start方法创建线程执行run方法。（缺点单继承）

```java
    /**
     * 继承Thread类创建线程
     */
    private static void CreateThread() {
        Thread thread = new Thread() {
            public void run() {
                System.out.println("继承Thread类创建线程==线程启动");
            }
        };
        thread.start();
    }
```
**2. 实现Runnable接口**
> 实现Runnable接口。Java中类是单继承，但接口是可以多继承的。
> 将实现Runnable接口run方法。
> （也可以使用匿名内部类或者那么lambda表达式方式）
> 该实例对象作为参数传给Thread构造函数，
> 调用Thread实例对象的start方法创建线程执行run方法。
```java
    private static void ImplementRunnable() {
        new Thread(new Runnable() {
            public void run() {
                System.out.println("实现Runnable接口创建线程==线程启动");
            }
        }).start();
    }
```

**3. 实现Callable接口**
> 实现Callable接口。与实现Runnable接口相似。不过实现Callable接口可以拿到执行完的接口。结合FutureTask传入实现Callable接口的实例对象，通过future.get（）阻塞等待获取参数。
```java
private static void ImplementCallable() {
        try {
            // 创建Callable对象
            CallableDemo callableDemo = new CallableDemo();
            // 创建FutureTask对象
            FutureTask<String> futureTask = new FutureTask<String>(callableDemo);
            // 创建线程
            Thread thread = new Thread(futureTask);
            // 启动线程
            thread.start();
            System.out.println("实现Callable接口创建线程==线程启动");
            // 等待线程执行完毕
            System.out.println(futureTask.get());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private static class CallableDemo implements Callable<String> {
        public String call() throws Exception {
            return "实现Callable接口创建线程==线程执行完毕";
        }
    }
```
**4. 使用ExecutorService(不建议)**

> 利用线程池创建线程。利用Executors创建线程。将实现runnable实例对象交给线程池管理。
```java
    private static void ImplementExecutorService() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(new Runnable() {
            public void run() {
                System.out.println("使用ExecutorService线程池创建线程==线程执行完毕");
            }
        });
    }
```
- **不建议的原因：**

1.LinkedBlockingQueue队列是无界的，如果任务很多会造成内存溢出。

2.不能自定义线程的名字，不易方便排查问题。所以建议直接使用ThreadPoolExecutor定义线程池。

```java
    public static ExecutorService newFixedThreadPool(int nThreads) {
        return new ThreadPoolExecutor(nThreads, nThreads,
                                      0L, TimeUnit.MILLISECONDS,
                                      new LinkedBlockingQueue<Runnable>());
    }
```


