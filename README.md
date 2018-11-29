# ActThreadTest
//isRunning = false;//不主动关闭inner thread，子线程持有activity引用，子线程一直运行着，activity就永远不会被gc  
//outerThread.close();//不主动关闭outer thread，子线程持有 handler的弱引用，handler持有activity的弱引用，activity关闭后，主动GC，handler和activity对象都能被回收，子线程仍然运行着  
//outerListenerThread.close();//不主动关闭outer listener thread，listener持有activity的引用，thread持有listener引用，activity关闭后，thread还在运行，listener没有释放，activity也不会被GC  
//btn4  这种情况下outerListener持有activity的弱引用，所以activity关闭后，主动GC，activity可以被回收，子线程仍然运行  
