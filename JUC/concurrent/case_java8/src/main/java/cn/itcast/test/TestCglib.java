package cn.itcast.test;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class TestCglib {
    public static void main(String[] args) throws IOException {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(MyService.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

                Object result = methodProxy.invokeSuper(o, objects);


                Field fastClassInfo = methodProxy.getClass().getDeclaredField("fastClassInfo");
                fastClassInfo.setAccessible(true);
                Object info = fastClassInfo.get(methodProxy);
                Field f2 = info.getClass().getDeclaredField("f2");
                f2.setAccessible(true);
                System.out.println(f2.get(info));
                System.out.println(f2.get(info).getClass());

                Field i2 = info.getClass().getDeclaredField("i2");
                i2.setAccessible(true);
                System.out.println(i2.get(info));

                return result;
            }
        });
        MyService myService = (MyService) enhancer.create();
        System.out.println(myService.getClass());
        myService.save();
        System.in.read();
    }
}
class MyService {
    public void save() {
        System.out.println("save...");
    }
}
